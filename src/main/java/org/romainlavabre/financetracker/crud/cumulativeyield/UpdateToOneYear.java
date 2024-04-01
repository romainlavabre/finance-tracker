package org.romainlavabre.financetracker.crud.cumulativeyield;

import org.romainlavabre.crud.Update;
import org.romainlavabre.financetracker.entity.CumulativeYield;
import org.romainlavabre.financetracker.parameter.CumulativeYieldParameter;
import org.romainlavabre.financetracker.repository.CumulativeYieldRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "updateCumulativeYieldToOneYear" )
public class UpdateToOneYear implements Update< CumulativeYield > {
    protected final CumulativeYieldRepository cumulativeYieldRepository;


    public UpdateToOneYear( CumulativeYieldRepository cumulativeYieldRepository ) {
        this.cumulativeYieldRepository = cumulativeYieldRepository;
    }


    @Override
    public void update( Request request, CumulativeYield cumulativeYield ) {
        Float toOneYear = request.getParameter( CumulativeYieldParameter.TO_ONE_YEAR, Float.class );

        cumulativeYield.setToOneYear( toOneYear );

        cumulativeYieldRepository.persist( cumulativeYield );
    }
}
