package org.romainlavabre.financetracker.crud.annuallyyield;

import org.romainlavabre.crud.Update;
import org.romainlavabre.financetracker.entity.AnnuallyYield;
import org.romainlavabre.financetracker.parameter.AnnuallyYieldParameter;
import org.romainlavabre.financetracker.repository.AnnuallyYieldRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "updateAnnuallyYield" )
public class UpdateYield implements Update< AnnuallyYield > {
    protected final AnnuallyYieldRepository annuallyYieldRepository;


    public UpdateYield( AnnuallyYieldRepository annuallyYieldRepository ) {
        this.annuallyYieldRepository = annuallyYieldRepository;
    }


    @Override
    public void update( Request request, AnnuallyYield annuallyYield ) {
        Float yield = request.getParameter( AnnuallyYieldParameter.YIELD, Float.class );

        annuallyYield.setYield( yield );

        annuallyYieldRepository.persist( annuallyYield );
    }
}
