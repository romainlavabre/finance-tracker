package org.romainlavabre.financetracker.crud.cumulativeyield;

import org.romainlavabre.crud.Update;
import org.romainlavabre.financetracker.entity.CumulativeYield;
import org.romainlavabre.financetracker.parameter.CumulativeYieldParameter;
import org.romainlavabre.financetracker.repository.CumulativeYieldRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "updateCumulativeYieldSinceCreation" )
public class UpdateSinceCreation implements Update< CumulativeYield > {
    protected final CumulativeYieldRepository cumulativeYieldRepository;


    public UpdateSinceCreation( CumulativeYieldRepository cumulativeYieldRepository ) {
        this.cumulativeYieldRepository = cumulativeYieldRepository;
    }


    @Override
    public void update( Request request, CumulativeYield cumulativeYield ) {
        Float sinceCreation = request.getParameter( CumulativeYieldParameter.SINCE_CREATION, Float.class );

        cumulativeYield.setSinceCreation( sinceCreation );

        cumulativeYieldRepository.persist( cumulativeYield );
    }
}
