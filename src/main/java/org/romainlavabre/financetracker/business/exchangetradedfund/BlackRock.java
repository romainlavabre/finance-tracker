package org.romainlavabre.financetracker.business.exchangetradedfund;

import org.json.JSONArray;
import org.json.JSONObject;
import org.romainlavabre.dataconverter.Cast;
import org.romainlavabre.exception.HttpInternalServerErrorException;
import org.romainlavabre.financetracker.configuration.response.Message;
import org.romainlavabre.rest.RequestBuilder;
import org.romainlavabre.rest.Response;
import org.romainlavabre.rest.Rest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service( "BlackRockProvider" )
public class BlackRock implements Provider {
    @Override
    public Scrapper.ScrapperResult scrape( String pageLink ) {
        String productId = resolveProductId( pageLink );

        Response response =
                Rest.builder()
                        .get( pageLink )
                        .buildAndSend( RequestBuilder.RESPONSE_HTML );

        if ( !response.isSuccess() ) {
            throw new HttpInternalServerErrorException( Message.UNABLE_TO_BUILD_EXCHANGE_TRADED_FUND );
        }

        String htmlBody = response.getBodyAsString();

        Scrapper.ScrapperResult scrapperResult = new Scrapper.ScrapperResult( productId );

        resolveAnnuallyYield( scrapperResult, htmlBody );
        resolveCumulativeYield( scrapperResult, htmlBody );
        resolveSectorDistribution( scrapperResult, htmlBody );
        resolveCountryDistribution( scrapperResult, htmlBody );
        resolvePricing( scrapperResult, htmlBody );

        return scrapperResult;
    }


    protected void resolveAnnuallyYield( Scrapper.ScrapperResult scrapperResult, String htmlBody ) {
        htmlBody = htmlBody.split( "product-table border-row calendar-year" )[ 1 ];

        String[] classNames = new String[]{ "tenYear", "nineYear", "eightYear", "sevenYear", "sixYear", "fiveYear", "fourYear", "threeYear", "twoYear", "oneYear" };

        for ( String className : classNames ) {
            Pattern pattern = Pattern.compile( "<th class=\"" + className + " \">([0-9]{4})</th>" );
            Matcher matcher = pattern.matcher( htmlBody );

            if ( matcher.find() ) {
                int year = Cast.getInt( matcher.group( 1 ) );

                pattern = Pattern.compile( "<td class=\"" + className + " \">\\n([0-9,-]+)\\n</td>" );
                matcher = pattern.matcher( htmlBody );

                if ( matcher.find() ) {
                    if ( matcher.group( 1 ).equals( "-" ) ) {
                        continue;
                    }

                    scrapperResult.addAnnuallyYield( year, Cast.getFloat( matcher.group( 1 ).replace( ",", "." ) ) );
                }
            }
        }
    }


    protected void resolveCumulativeYield( Scrapper.ScrapperResult scrapperResult, String htmlBody ) {
        Pattern pattern = Pattern.compile( "<div id=\"cumulativeTabs\" data-ajaxUri=\"(.*)\">" );
        Matcher matcher = pattern.matcher( htmlBody );

        if ( !matcher.find() ) {
            return;
        }

        Response response =
                Rest.builder()
                        .get( "https://www.blackrock.com" + matcher.group( 1 ) )
                        .buildAndSend( RequestBuilder.RESPONSE_HTML );

        htmlBody = response.getBodyAsString();

        Map< String, Integer > loop = Map.of(
                "oneYear", 1,
                "threeYear", 3,
                "fiveYear", 5,
                "tenYear", 10,
                "inception", 0
        );

        for ( Map.Entry< String, Integer > entry : loop.entrySet() ) {
            pattern = Pattern.compile( "<td class=\"" + entry.getKey() + " \">\\n([0-9,-]+)" );
            matcher = pattern.matcher( htmlBody );

            if ( !matcher.find() ) {
                continue;
            }

            if ( matcher.group( 1 ).equals( "-" ) ) {
                continue;
            }

            scrapperResult.addCumulativeYield( entry.getValue(), Cast.getFloat( matcher.group( 1 ).replace( ",", "." ) ) );
        }
    }


    protected void resolveSectorDistribution( Scrapper.ScrapperResult scrapperResult, String htmlBody ) {
        if ( htmlBody.split( "var tabsSectorDataTable =" ).length == 1 ) {
            return;
        }

        String encodedJson = htmlBody.split( "var tabsSectorDataTable =" )[ 1 ].split( ";" )[ 0 ];

        JSONArray array = new JSONArray( encodedJson );

        for ( Object object : array ) {
            JSONObject jsonObject = ( JSONObject ) object;

            scrapperResult.addSectorDistribution( jsonObject.getString( "name" ), Cast.getFloat( jsonObject.getString( "value" ).replace( ",", "." ) ) );
        }
    }


    protected void resolveCountryDistribution( Scrapper.ScrapperResult scrapperResult, String htmlBody ) {
        if ( htmlBody.split( "var subTabsCountriesDataTable =" ).length == 1 ) {
            return;
        }

        String encodedJson = htmlBody.split( "var subTabsCountriesDataTable =" )[ 1 ].split( ";" )[ 0 ];

        JSONArray array = new JSONArray( encodedJson );

        for ( Object object : array ) {
            JSONObject jsonObject = ( JSONObject ) object;

            if ( jsonObject.getString( "name" ).equals( "Liquidités et/ou produits dérivés" )
                    || jsonObject.get( "name" ).equals( "Other" ) ) {
                continue;
            }

            scrapperResult.addCountryDistribution( jsonObject.getString( "name" ), Cast.getFloat( jsonObject.getString( "value" ).replace( ",", "." ) ) );
        }
    }


    protected String resolveProductId( String pageLink ) {
        return pageLink.split( "/" )[ 6 ];
    }


    protected void resolvePricing( Scrapper.ScrapperResult scrapperResult, String htmlBody ) {
        if ( htmlBody.split( "TER" ).length == 1 ) {
            return;
        }

        htmlBody = htmlBody.split( "TER" )[ 2 ];

        Pattern pattern = Pattern.compile( "([0-9]+,*[0-9]+)%" );
        Matcher matcher = pattern.matcher( htmlBody );

        if ( matcher.find() ) {
            scrapperResult.setPricing( Cast.getFloat( matcher.group( 1 ).replace( ",", "." ) ) );
        }
    }


    @Override
    public boolean isMatch( String pageLink ) {
        return pageLink.startsWith( "https://www.blackrock.com" );
    }
}
