package org.romainlavabre.financetracker.crud.exchangetradedfund;

import org.romainlavabre.crud.Update;
import org.romainlavabre.financetracker.entity.ExchangeTradedFund;
import org.romainlavabre.financetracker.parameter.ExchangeTradedFundParameter;
import org.romainlavabre.financetracker.repository.ExchangeTradedFundRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "updateExchangeTradedFundProductId" )
public class UpdateProductId implements Update< ExchangeTradedFund > {
    protected final ExchangeTradedFundRepository exchangeTradedFundRepository;


    public UpdateProductId( ExchangeTradedFundRepository exchangeTradedFundRepository ) {
        this.exchangeTradedFundRepository = exchangeTradedFundRepository;
    }


    @Override
    public void update( Request request, ExchangeTradedFund exchangeTradedFund ) {
        String productId = request.getParameter( ExchangeTradedFundParameter.PRODUCT_ID, String.class );

        exchangeTradedFund.setProductId( productId );

        exchangeTradedFundRepository.persist( exchangeTradedFund );
    }
}
