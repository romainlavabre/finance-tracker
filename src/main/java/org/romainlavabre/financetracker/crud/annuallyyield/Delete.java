package org.romainlavabre.financetracker.crud.annuallyyield;

import org.romainlavabre.financetracker.entity.AnnuallyYield;
import org.romainlavabre.financetracker.repository.AnnuallyYieldRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "deleteAnnuallyYield" )
public class Delete implements org.romainlavabre.crud.Delete< AnnuallyYield > {
    protected final AnnuallyYieldRepository annuallyYieldRepository;


    public Delete( AnnuallyYieldRepository annuallyYieldRepository ) {
        this.annuallyYieldRepository = annuallyYieldRepository;
    }


    @Override
    public void delete( Request request, AnnuallyYield annuallyYield ) {
        annuallyYieldRepository.remove( annuallyYield );
    }
}
