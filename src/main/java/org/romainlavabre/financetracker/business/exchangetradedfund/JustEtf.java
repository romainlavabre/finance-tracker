package org.romainlavabre.financetracker.business.exchangetradedfund;

import org.romainlavabre.exception.HttpInternalServerErrorException;
import org.romainlavabre.financetracker.configuration.response.Message;
import org.romainlavabre.rest.RequestBuilder;
import org.romainlavabre.rest.Response;
import org.romainlavabre.rest.Rest;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class JustEtf implements Provider {
    @Override
    public Scrapper.ScrapperResult scrape( String pageLink ) {
        Response response =
                Rest.builder()
                        .get( pageLink )
                        .buildAndSend( RequestBuilder.RESPONSE_HTML );

        if ( !response.isSuccess() ) {
            throw new HttpInternalServerErrorException( Message.UNABLE_TO_BUILD_EXCHANGE_TRADED_FUND );
        }

        String productId = resolveProductId( pageLink );

        String htmlBody = response.getBodyAsString();

        Scrapper.ScrapperResult scrapperResult = new Scrapper.ScrapperResult( productId );

        resolveSectorDistribution( scrapperResult, htmlBody );


        return scrapperResult;
    }


    protected void resolveSectorDistribution( Scrapper.ScrapperResult scrapperResult, String htmlBody ) {
        String[] trs = htmlBody.split( "<h3> Pays </h3>" )[ 1 ].split( "<table" )[ 1 ].split( "</table>" )[ 0 ].split( "<tr>" );

        for ( String tr : trs ) {
            Pattern pattern = Pattern.compile( "<td>([A-Z-a-z- ]+)</td>" );
            Matcher matcher = pattern.matcher( tr );

            if ( matcher.find() ) {
                System.out.println( matcher.group( 1 ) );
            }
        }

    }


    private String resolveProductId( String pageLink ) {
        return pageLink.split( "isin=" )[ 1 ].split( "#" )[ 0 ];
    }


    @Override
    public boolean isMatch( String pageLink ) {
        return pageLink.contains( "justetf.com" );
    }
}
