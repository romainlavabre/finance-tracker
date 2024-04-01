package org.romainlavabre.financetracker.crud.exchangetradedfund;

import org.romainlavabre.crud.Update;
import org.romainlavabre.financetracker.entity.ExchangeTradedFund;
import org.romainlavabre.financetracker.parameter.ExchangeTradedFundParameter;
import org.romainlavabre.financetracker.repository.ExchangeTradedFundRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "updateExchangeTradedFundPricing" )
public class UpdatePricing implements Update< ExchangeTradedFund > {
    protected final ExchangeTradedFundRepository exchangeTradedFundRepository;


    public UpdatePricing( ExchangeTradedFundRepository exchangeTradedFundRepository ) {
        this.exchangeTradedFundRepository = exchangeTradedFundRepository;
    }


    @Override
    public void update( Request request, ExchangeTradedFund exchangeTradedFund ) {
        Float pricing = request.getParameter( ExchangeTradedFundParameter.PRICING, Float.class );

        exchangeTradedFund.setPricing( pricing );

        exchangeTradedFundRepository.persist( exchangeTradedFund );
    }
}
