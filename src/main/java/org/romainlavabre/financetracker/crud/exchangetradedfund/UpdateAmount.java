package org.romainlavabre.financetracker.crud.exchangetradedfund;

import org.romainlavabre.crud.Update;
import org.romainlavabre.financetracker.entity.ExchangeTradedFund;
import org.romainlavabre.financetracker.parameter.ExchangeTradedFundParameter;
import org.romainlavabre.financetracker.repository.ExchangeTradedFundRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "updateExchangeTradedFundAmount" )
public class UpdateAmount implements Update< ExchangeTradedFund > {
    protected final ExchangeTradedFundRepository exchangeTradedFundRepository;


    public UpdateAmount( ExchangeTradedFundRepository exchangeTradedFundRepository ) {
        this.exchangeTradedFundRepository = exchangeTradedFundRepository;
    }


    @Override
    public void update( Request request, ExchangeTradedFund exchangeTradedFund ) {
        Float amount = request.getParameter( ExchangeTradedFundParameter.AMOUNT, Float.class );

        exchangeTradedFund.setAmount( amount );

        exchangeTradedFundRepository.persist( exchangeTradedFund );
    }
}
