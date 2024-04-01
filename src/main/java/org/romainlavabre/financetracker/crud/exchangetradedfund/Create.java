package org.romainlavabre.financetracker.crud.exchangetradedfund;

import org.romainlavabre.financetracker.entity.ExchangeTradedFund;
import org.romainlavabre.financetracker.parameter.ExchangeTradedFundParameter;
import org.romainlavabre.financetracker.repository.ExchangeTradedFundRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "createExchangeTradedFund" )
public class Create implements org.romainlavabre.crud.Create< ExchangeTradedFund > {
    protected final ExchangeTradedFundRepository exchangeTradedFundRepository;


    public Create( ExchangeTradedFundRepository exchangeTradedFundRepository ) {
        this.exchangeTradedFundRepository = exchangeTradedFundRepository;
    }


    @Override
    public void create( Request request, ExchangeTradedFund exchangeTradedFund ) {
        String productId  = request.getParameter( ExchangeTradedFundParameter.PRODUCT_ID, String.class );
        String provider   = request.getParameter( ExchangeTradedFundParameter.PROVIDER, String.class );
        String publicName = request.getParameter( ExchangeTradedFundParameter.PUBLIC_NAME, String.class );
        String pageLink   = request.getParameter( ExchangeTradedFundParameter.PAGE_LINK, String.class );
        Float  pricing    = request.getParameter( ExchangeTradedFundParameter.PRICING, Float.class );
        Byte   risk       = request.getParameter( ExchangeTradedFundParameter.RISK, Byte.class );
        Float  amount     = request.getParameter( ExchangeTradedFundParameter.AMOUNT, Float.class );

        exchangeTradedFund
                .setProductId( productId )
                .setProvider( provider )
                .setPublicName( publicName )
                .setPageLink( pageLink )
                .setPricing( pricing )
                .setRisk( risk )
                .setAmount( amount );

        exchangeTradedFundRepository.persist( exchangeTradedFund );
    }
}
