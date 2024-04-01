package org.romainlavabre.financetracker.crud.annuallyyield;

import org.romainlavabre.financetracker.entity.AnnuallyYield;
import org.romainlavabre.financetracker.parameter.AnnuallyYieldParameter;
import org.romainlavabre.financetracker.repository.AnnuallyYieldRepository;
import org.romainlavabre.financetracker.repository.ExchangeTradedFundRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "createAnnuallyYield" )
public class Create implements org.romainlavabre.crud.Create< AnnuallyYield > {

    protected final AnnuallyYieldRepository      annuallyYieldRepository;
    protected final ExchangeTradedFundRepository exchangeTradedFundRepository;


    public Create( AnnuallyYieldRepository annuallyYieldRepository, ExchangeTradedFundRepository exchangeTradedFundRepository ) {
        this.annuallyYieldRepository      = annuallyYieldRepository;
        this.exchangeTradedFundRepository = exchangeTradedFundRepository;
    }


    @Override
    public void create( Request request, AnnuallyYield annuallyYield ) {
        Short year                 = request.getParameter( AnnuallyYieldParameter.YEAR, Short.class );
        Float yield                = request.getParameter( AnnuallyYieldParameter.YIELD, Float.class );
        Long  exchangeTradedFundId = request.getParameter( AnnuallyYieldParameter.EXCHANGE_TRADED_FUND, Long.class );

        annuallyYield
                .setYear( year )
                .setYield( yield )
                .setExchangeTradedFund( exchangeTradedFundRepository.findOrFail( exchangeTradedFundId ) );

        annuallyYieldRepository.persist( annuallyYield );
    }
}
