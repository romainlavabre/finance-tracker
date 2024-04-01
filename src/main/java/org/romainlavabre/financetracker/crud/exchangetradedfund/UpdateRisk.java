package org.romainlavabre.financetracker.crud.exchangetradedfund;

import org.romainlavabre.crud.Update;
import org.romainlavabre.financetracker.entity.ExchangeTradedFund;
import org.romainlavabre.financetracker.parameter.ExchangeTradedFundParameter;
import org.romainlavabre.financetracker.repository.ExchangeTradedFundRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "updateExchangeTradedFundRisk" )
public class UpdateRisk implements Update< ExchangeTradedFund > {
    protected final ExchangeTradedFundRepository exchangeTradedFundRepository;


    public UpdateRisk( ExchangeTradedFundRepository exchangeTradedFundRepository ) {
        this.exchangeTradedFundRepository = exchangeTradedFundRepository;
    }


    @Override
    public void update( Request request, ExchangeTradedFund exchangeTradedFund ) {
        Byte risk = request.getParameter( ExchangeTradedFundParameter.RISK, Byte.class );

        exchangeTradedFund.setRisk( risk );

        exchangeTradedFundRepository.persist( exchangeTradedFund );
    }
}
