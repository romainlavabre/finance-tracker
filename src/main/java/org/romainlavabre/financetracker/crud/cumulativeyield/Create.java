package org.romainlavabre.financetracker.crud.cumulativeyield;

import org.romainlavabre.financetracker.entity.CumulativeYield;
import org.romainlavabre.financetracker.parameter.CumulativeYieldParameter;
import org.romainlavabre.financetracker.repository.CumulativeYieldRepository;
import org.romainlavabre.financetracker.repository.ExchangeTradedFundRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "createCumulativeYield" )
public class Create implements org.romainlavabre.crud.Create< CumulativeYield > {
    protected final CumulativeYieldRepository    cumulativeYieldRepository;
    protected final ExchangeTradedFundRepository exchangeTradedFundRepository;


    public Create( CumulativeYieldRepository cumulativeYieldRepository, ExchangeTradedFundRepository exchangeTradedFundRepository ) {
        this.cumulativeYieldRepository    = cumulativeYieldRepository;
        this.exchangeTradedFundRepository = exchangeTradedFundRepository;
    }


    @Override
    public void create( Request request, CumulativeYield cumulativeYield ) {
        Float toTenYear            = request.getParameter( CumulativeYieldParameter.TO_TEN_YEAR, Float.class );
        Float toFiveYear           = request.getParameter( CumulativeYieldParameter.TO_FIVE_YEAR, Float.class );
        Float toThreeYear          = request.getParameter( CumulativeYieldParameter.TO_THREE_YEAR, Float.class );
        Float toOneYear            = request.getParameter( CumulativeYieldParameter.TO_ONE_YEAR, Float.class );
        Float sinceCreation        = request.getParameter( CumulativeYieldParameter.SINCE_CREATION, Float.class );
        Long  exchangeTradedFundId = request.getParameter( CumulativeYieldParameter.EXCHANGE_TRADED_FUND, Long.class );

        cumulativeYield
                .setToTenYear( toTenYear )
                .setToFiveYear( toFiveYear )
                .setToThreeYear( toThreeYear )
                .setToOneYear( toOneYear )
                .setSinceCreation( sinceCreation )
                .setExchangeTradedFund( exchangeTradedFundRepository.findOrFail( exchangeTradedFundId ) );

        cumulativeYieldRepository.persist( cumulativeYield );
    }
}
