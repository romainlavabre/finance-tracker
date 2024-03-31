package org.romainlavabre.financetracker.business.exchangetradedfund;

import org.romainlavabre.dataconverter.Cast;
import org.romainlavabre.exception.HttpInternalServerErrorException;
import org.romainlavabre.financetracker.configuration.response.Message;
import org.romainlavabre.rest.Response;
import org.romainlavabre.rest.Rest;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class Amundi implements Provider {
    @Override
    public Scrapper.ScrapperResult scrape( String pageLink ) {
        String productId = resolveProductId( pageLink );

        List< Object > metrics = new ArrayList<>();

        for ( int i = ZonedDateTime.now().getYear(); i >= ZonedDateTime.now().getYear() - 20; i-- ) {
            metrics.add( Map.of(
                    "indicator", "shareCalendarPerformance",
                    "period", String.valueOf( i )
            ) );
        }

        String[] shareCumulativePerformances = new String[]{
                "ONE_YEAR",
                "THREE_YEARS",
                "FIVE_YEARS",
                "TEN_YEARS",
                "SINCE_CREATION"
        };

        for ( String shareCumulativePerformance : shareCumulativePerformances ) {
            metrics.add( Map.of(
                    "indicator", "shareCumulativePerformance",
                    "period", shareCumulativePerformance
            ) );
        }

        Response response =
                Rest.builder()
                        .post( "https://www.amundietf.fr/mapi/ProductAPI/getProductsData" )
                        .jsonBody( Map.of(
                                "context", Map.of(
                                        "countryCode", "FRA",
                                        "countryName", "France",
                                        "googleCountryCode", "FR"
                                ),
                                "productIds", List.of( productId.toUpperCase() ),
                                "characteristics", List.of(
                                        "TOTAL_EXPENSE_RATIO",
                                        "SRI"
                                ),
                                "metrics", metrics,
                                "breakDown", Map.of(
                                        "aggregationFields", List.of(
                                                "INDEX_SECTORS",
                                                "INDEX_CURRENCIES",
                                                "INDEX_COUNTRIES"
                                        )
                                )

                        ) )
                        .buildAndSend();

        if ( !response.isSuccess() ) {
            throw new HttpInternalServerErrorException( Message.UNABLE_TO_BUILD_EXCHANGE_TRADED_FUND );
        }

        Map< String, Object > body = ( ( List< Map< String, Object > > ) response.getBodyAsMap().get( "products" ) ).get( 0 );

        Scrapper.ScrapperResult scrapperResult = new Scrapper.ScrapperResult( productId.toUpperCase() );

        resolveCumulativeYield( scrapperResult, body );
        resolveAnnuallyYield( scrapperResult, body );
        resolveSectorDistribution( scrapperResult, body );
        resolveCountryDistribution( scrapperResult, body );
        resolveCurrencyDistribution( scrapperResult, body );
        resolvePricing( scrapperResult, body );
        resolveRisk( scrapperResult, body );

        return scrapperResult;
    }


    protected void resolveAnnuallyYield( Scrapper.ScrapperResult scrapperResult, Map< String, Object > body ) {
        List< Map< String, Object > > metrics = ( List< Map< String, Object > > ) body.get( "metrics" );

        for ( Map< String, Object > metric : metrics ) {
            String indicator = metric.get( "indicator" ).toString();

            if ( !indicator.equals( "shareCalendarPerformance" ) || metric.get( "value" ) == null ) {
                continue;
            }


            int   year  = Cast.getInt( metric.get( "period" ) );
            float yield = Cast.getFloat( metric.get( "value" ) );

            scrapperResult.addAnnuallyYield( year, yield * 100 );
        }
    }


    protected void resolveCumulativeYield( Scrapper.ScrapperResult scrapperResult, Map< String, Object > body ) {
        List< Map< String, Object > > metrics = ( List< Map< String, Object > > ) body.get( "metrics" );

        Map< String, Integer > map = Map.of(
                "ONE_YEAR", 1,
                "THREE_YEARS", 3,
                "FIVE_YEARS", 5,
                "TEN_YEARS", 10,
                "SINCE_CREATION", 0
        );

        for ( Map< String, Object > metric : metrics ) {
            String indicator = metric.get( "indicator" ).toString();

            if ( !indicator.equals( "shareCumulativePerformance" ) || metric.get( "value" ) == null ) {
                continue;
            }

            String period = metric.get( "period" ).toString();


            scrapperResult.addCumulativeYield( map.get( period ), Cast.getFloat( metric.get( "value" ) ) * 100 );
        }
    }


    protected void resolveSectorDistribution( Scrapper.ScrapperResult scrapperResult, Map< String, Object > body ) {
        List< Map< String, Object > > breakDowns = ( List< Map< String, Object > > ) body.get( "breakDowns" );


        for ( Map< String, Object > breakDown : breakDowns ) {
            String aggregationField = breakDown.get( "aggregationField" ).toString();

            if ( !aggregationField.equals( "INDEX_SECTORS" ) ) {
                continue;
            }

            List< Map< String, Object > > data = ( List< Map< String, Object > > ) breakDown.get( "breakDownData" );

            for ( Map< String, Object > row : data ) {
                scrapperResult.addSectorDistribution( row.get( "aggregationName" ).toString(), Cast.getFloat( row.get( "weight" ) ) * 100 );
            }
        }
    }


    protected void resolveCountryDistribution( Scrapper.ScrapperResult scrapperResult, Map< String, Object > body ) {
        List< Map< String, Object > > breakDowns = ( List< Map< String, Object > > ) body.get( "breakDowns" );


        for ( Map< String, Object > breakDown : breakDowns ) {
            String aggregationField = breakDown.get( "aggregationField" ).toString();

            if ( !aggregationField.equals( "INDEX_COUNTRIES" ) ) {
                continue;
            }

            List< Map< String, Object > > data = ( List< Map< String, Object > > ) breakDown.get( "breakDownData" );

            for ( Map< String, Object > row : data ) {
                scrapperResult.addCountryDistribution( row.get( "aggregationName" ).toString(), Cast.getFloat( row.get( "weight" ) ) * 100 );
            }
        }
    }


    protected void resolveCurrencyDistribution( Scrapper.ScrapperResult scrapperResult, Map< String, Object > body ) {
        List< Map< String, Object > > breakDowns = ( List< Map< String, Object > > ) body.get( "breakDowns" );


        for ( Map< String, Object > breakDown : breakDowns ) {
            String aggregationField = breakDown.get( "aggregationField" ).toString();

            if ( !aggregationField.equals( "INDEX_CURRENCIES" ) ) {
                continue;
            }

            List< Map< String, Object > > data = ( List< Map< String, Object > > ) breakDown.get( "breakDownData" );

            for ( Map< String, Object > row : data ) {
                scrapperResult.addCurrencyDistribution( row.get( "aggregationName" ).toString(), Cast.getFloat( row.get( "weight" ) ) * 100 );
            }
        }
    }


    protected String resolveProductId( String pageLink ) {
        return pageLink.split( "/" )[ pageLink.split( "/" ).length - 1 ];
    }


    protected void resolvePricing( Scrapper.ScrapperResult scrapperResult, Map< String, Object > body ) {
        Map< String, Object > characteristics = ( Map< String, Object > ) body.get( "characteristics" );

        scrapperResult.setPricing( Cast.getFloat( characteristics.get( "TOTAL_EXPENSE_RATIO" ) ) );

    }


    protected void resolveRisk( Scrapper.ScrapperResult scrapperResult, Map< String, Object > body ) {
        Map< String, Object > characteristics = ( Map< String, Object > ) body.get( "characteristics" );

        if ( characteristics.get( "SRI" ) == null ) {
            return;
        }

        scrapperResult.setRiskLevel( Cast.getInt( characteristics.get( "SRI" ) ) );

    }


    @Override
    public boolean isMatch( String pageLink ) {
        return pageLink.startsWith( "https://www.amundietf.fr" );
    }
}
