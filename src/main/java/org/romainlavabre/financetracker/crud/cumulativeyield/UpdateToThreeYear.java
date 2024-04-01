package org.romainlavabre.financetracker.crud.cumulativeyield;

import org.romainlavabre.crud.Update;
import org.romainlavabre.financetracker.entity.CumulativeYield;
import org.romainlavabre.financetracker.parameter.CumulativeYieldParameter;
import org.romainlavabre.financetracker.repository.CumulativeYieldRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "updateCumulativeYieldToThreeYear" )
public class UpdateToThreeYear implements Update< CumulativeYield > {
    protected final CumulativeYieldRepository cumulativeYieldRepository;


    public UpdateToThreeYear( CumulativeYieldRepository cumulativeYieldRepository ) {
        this.cumulativeYieldRepository = cumulativeYieldRepository;
    }


    @Override
    public void update( Request request, CumulativeYield cumulativeYield ) {
        Float toThreeYear = request.getParameter( CumulativeYieldParameter.TO_THREE_YEAR, Float.class );

        cumulativeYield.setToThreeYear( toThreeYear );

        cumulativeYieldRepository.persist( cumulativeYield );
    }
}
