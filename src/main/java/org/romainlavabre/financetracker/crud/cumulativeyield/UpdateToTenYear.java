package org.romainlavabre.financetracker.crud.cumulativeyield;

import org.romainlavabre.crud.Update;
import org.romainlavabre.financetracker.entity.CumulativeYield;
import org.romainlavabre.financetracker.parameter.CumulativeYieldParameter;
import org.romainlavabre.financetracker.repository.CumulativeYieldRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "updateCumulativeYieldToTenYear" )
public class UpdateToTenYear implements Update< CumulativeYield > {
    protected final CumulativeYieldRepository cumulativeYieldRepository;


    public UpdateToTenYear( CumulativeYieldRepository cumulativeYieldRepository ) {
        this.cumulativeYieldRepository = cumulativeYieldRepository;
    }


    @Override
    public void update( Request request, CumulativeYield cumulativeYield ) {
        Float toTenYear = request.getParameter( CumulativeYieldParameter.TO_TEN_YEAR, Float.class );

        cumulativeYield.setToTenYear( toTenYear );

        cumulativeYieldRepository.persist( cumulativeYield );
    }
}
