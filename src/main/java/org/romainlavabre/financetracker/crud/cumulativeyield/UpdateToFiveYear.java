package org.romainlavabre.financetracker.crud.cumulativeyield;

import org.romainlavabre.crud.Update;
import org.romainlavabre.financetracker.entity.CumulativeYield;
import org.romainlavabre.financetracker.parameter.CumulativeYieldParameter;
import org.romainlavabre.financetracker.repository.CumulativeYieldRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "updateCumulativeYieldToFiveYear" )
public class UpdateToFiveYear implements Update< CumulativeYield > {
    protected final CumulativeYieldRepository cumulativeYieldRepository;


    public UpdateToFiveYear( CumulativeYieldRepository cumulativeYieldRepository ) {
        this.cumulativeYieldRepository = cumulativeYieldRepository;
    }


    @Override
    public void update( Request request, CumulativeYield cumulativeYield ) {
        Float toFiveYear = request.getParameter( CumulativeYieldParameter.TO_FIVE_YEAR, Float.class );

        cumulativeYield.setToFiveYear( toFiveYear );

        cumulativeYieldRepository.persist( cumulativeYield );
    }
}
