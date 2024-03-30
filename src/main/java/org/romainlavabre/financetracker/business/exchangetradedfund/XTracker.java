package org.romainlavabre.financetracker.business.exchangetradedfund;


import org.romainlavabre.dataconverter.Cast;
import org.romainlavabre.exception.HttpInternalServerErrorException;
import org.romainlavabre.financetracker.configuration.response.Message;
import org.romainlavabre.rest.Response;
import org.romainlavabre.rest.Rest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service( "XTrackerProvider" )
public class XTracker implements Provider {

    @Override
    public Scrapper.ScrapperResult scrape( String pageLink ) {
        String productId = resolveProductId( pageLink );

        Response response =
                Rest.builder()
                        .get( "https://etf.dws.com/api/pdp/fr-fr/etf/{productId}/pdp" )
                        .routeParam( "productId", productId )
                        .buildAndSend();

        if ( !response.isSuccess() ) {
            throw new HttpInternalServerErrorException( Message.UNABLE_TO_BUILD_EXCHANGE_TRADED_FUND );
        }

        Map< String, Object > body = response.getBodyAsMap();

        Scrapper.ScrapperResult scrapperResult = new Scrapper.ScrapperResult( productId );

        resolveAnnuallyYield( scrapperResult, body );
        resolveCumulativeYield( scrapperResult, body );
        resolveDistribution( scrapperResult, body );
        resolvePricing( scrapperResult, body );

        return scrapperResult;
    }


    protected void resolveAnnuallyYield( Scrapper.ScrapperResult scrapperResult, Map< String, Object > body ) {
        Map< String, Object >         pageSections       = ( Map< String, Object > ) body.get( "pageSections" );
        Map< String, Object >         performanceSection = ( Map< String, Object > ) pageSections.get( "performanceSection" );
        Map< String, Object >         performanceTables  = ( Map< String, Object > ) performanceSection.get( "performanceTables" );
        Map< String, Object >         calendarTab        = ( Map< String, Object > ) performanceTables.get( "calendarTab" );
        Map< String, Object >         table              = ( Map< String, Object > ) calendarTab.get( "table" );
        List< Map< String, Object > > columns            = ( List< Map< String, Object > > ) table.get( "columns" );
        List< Map< String, Object > > values             = ( List< Map< String, Object > > ) table.get( "values" );

        for ( Map< String, Object > column : columns ) {
            String columnKey = column.get( "key" ).toString();

            if ( columnKey.equals( "column_0" ) ) {
                continue;
            }

            Map< String, Object > productValues = values.get( 0 );
            Map< String, Object > value         = ( Map< String, Object > ) productValues.get( columnKey );

            int   year  = Cast.getInt( column.get( "value" ) );
            float yield = Cast.getFloat( value.get( "sortValue" ) );

            scrapperResult.addAnnuallyYield( year, yield );
        }
    }


    protected void resolveCumulativeYield( Scrapper.ScrapperResult scrapperResult, Map< String, Object > body ) {
        Map< String, Object >         pageSections       = ( Map< String, Object > ) body.get( "pageSections" );
        Map< String, Object >         performanceSection = ( Map< String, Object > ) pageSections.get( "performanceSection" );
        Map< String, Object >         performanceTables  = ( Map< String, Object > ) performanceSection.get( "performanceTables" );
        Map< String, Object >         cumulativeTab      = ( Map< String, Object > ) performanceTables.get( "cumulativeTab" );
        Map< String, Object >         table              = ( Map< String, Object > ) cumulativeTab.get( "table" );
        List< Map< String, Object > > columns            = ( List< Map< String, Object > > ) table.get( "columns" );
        List< Map< String, Object > > values             = ( List< Map< String, Object > > ) table.get( "values" );

        for ( Map< String, Object > column : columns ) {
            String columnKey   = column.get( "key" ).toString();
            String columnValue = column.get( "value" ).toString().replace( "<span class=\"no-break\">", "" ).replace( "</span>", "" );

            if ( !columnValue.endsWith( "A" ) ) {
                continue;
            }

            Map< String, Object > productValues = values.get( 0 );
            Map< String, Object > value         = ( Map< String, Object > ) productValues.get( columnKey );

            int   to    = Cast.getInt( columnValue.replace( "A", "" ) );
            float yield = Cast.getFloat( value.get( "sortValue" ) );

            scrapperResult.addCumulativeYield( to, yield );
        }
    }


    protected void resolveDistribution( Scrapper.ScrapperResult scrapperResult, Map< String, Object > body ) {
        Map< String, Object >         pageSections = ( Map< String, Object > ) body.get( "pageSections" );
        Map< String, Object >         portfolio    = ( Map< String, Object > ) pageSections.get( "portfolio" );
        Map< String, Object >         charts       = ( Map< String, Object > ) portfolio.get( "charts" );
        List< Map< String, Object > > cards        = ( List< Map< String, Object > > ) charts.get( "cards" );

        for ( Map< String, Object > card : cards ) {
            List< Map< String, Object > > data = ( List< Map< String, Object > > ) card.get( "data" );

            if ( card.get( "title" ).toString().equals( "Répartition par pays" ) ) {
                resolveCountryDistribution( scrapperResult, data );
                continue;
            }

            if ( card.get( "title" ).toString().equals( "Répartition par devise" ) ) {
                resolveCurrencyDistribution( scrapperResult, data );
                continue;
            }
        }

        Map< String, Object > securityPortfolio = ( Map< String, Object > ) pageSections.get( "securityPortfolio" );
        charts = ( Map< String, Object > ) securityPortfolio.get( "charts" );
        cards  = ( List< Map< String, Object > > ) charts.get( "cards" );

        for ( Map< String, Object > card : cards ) {
            List< Map< String, Object > > data = ( List< Map< String, Object > > ) card.get( "data" );

            if ( card.get( "title" ).toString().equals( "Répartition par secteur" ) ) {
                resolveSectorDistribution( scrapperResult, data );
                continue;
            }
        }
    }


    protected void resolveCountryDistribution( Scrapper.ScrapperResult scrapperResult, List< Map< String, Object > > data ) {
        for ( Map< String, Object > row : data ) {
            String country      = row.get( "allocationName" ).toString();
            float  distribution = Cast.getFloat( row.get( "weighting" ) ) * 100;

            scrapperResult.addCountryDistribution( country, distribution );
        }
    }


    protected void resolveCurrencyDistribution( Scrapper.ScrapperResult scrapperResult, List< Map< String, Object > > data ) {
        for ( Map< String, Object > row : data ) {
            String country      = row.get( "allocationName" ).toString();
            float  distribution = Cast.getFloat( row.get( "weighting" ) ) * 100;

            scrapperResult.addCurrencyDistribution( country, distribution );
        }
    }


    protected void resolveSectorDistribution( Scrapper.ScrapperResult scrapperResult, List< Map< String, Object > > data ) {
        for ( Map< String, Object > row : data ) {
            String sector       = row.get( "allocationName" ).toString();
            float  distribution = Cast.getFloat( row.get( "weighting" ) ) * 100;

            scrapperResult.addSectorDistribution( sector, distribution );
        }
    }


    protected String resolveProductId( String pageLink ) {
        return pageLink.split( "/" )[ 4 ].split( "-" )[ 0 ];
    }


    protected void resolvePricing( Scrapper.ScrapperResult scrapperResult, Map< String, Object > body ) {
        Map< String, Object >         pageFrame     = ( Map< String, Object > ) body.get( "pageFrame" );
        Map< String, Object >         productHeader = ( Map< String, Object > ) pageFrame.get( "productHeader" );
        List< Map< String, Object > > tableValues   = ( List< Map< String, Object > > ) productHeader.get( "tableValues" );

        for ( Map< String, Object > column : tableValues ) {
            String columnKey = column.get( "key" ).toString();

            if ( !columnKey.equals( "TER" ) ) {
                continue;
            }

            float columnValue = Cast.getFloat( column.get( "value" ).toString().replace( "%", "" ).replace( ",", "." ) );

            scrapperResult.setPricing( columnValue );
        }
    }


    @Override
    public boolean isMatch( String pageLink ) {
        return pageLink.contains( "etf.dws.com" );
    }
}
