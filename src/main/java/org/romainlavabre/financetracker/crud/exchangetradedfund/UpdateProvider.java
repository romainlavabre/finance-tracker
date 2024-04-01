package org.romainlavabre.financetracker.crud.exchangetradedfund;

import org.romainlavabre.crud.Update;
import org.romainlavabre.financetracker.entity.ExchangeTradedFund;
import org.romainlavabre.financetracker.parameter.ExchangeTradedFundParameter;
import org.romainlavabre.financetracker.repository.ExchangeTradedFundRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "updateExchangeTradedFundProvider" )
public class UpdateProvider implements Update< ExchangeTradedFund > {
    protected final ExchangeTradedFundRepository exchangeTradedFundRepository;


    public UpdateProvider( ExchangeTradedFundRepository exchangeTradedFundRepository ) {
        this.exchangeTradedFundRepository = exchangeTradedFundRepository;
    }


    @Override
    public void update( Request request, ExchangeTradedFund exchangeTradedFund ) {
        String provider = request.getParameter( ExchangeTradedFundParameter.PROVIDER, String.class );

        exchangeTradedFund.setProvider( provider );

        exchangeTradedFundRepository.persist( exchangeTradedFund );
    }
}
