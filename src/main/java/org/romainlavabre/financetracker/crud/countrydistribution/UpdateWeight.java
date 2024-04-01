package org.romainlavabre.financetracker.crud.countrydistribution;

import org.romainlavabre.crud.Update;
import org.romainlavabre.financetracker.entity.CountryDistribution;
import org.romainlavabre.financetracker.parameter.CountryDistributionParameter;
import org.romainlavabre.financetracker.repository.CountryDistributionRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "updateCountryDistributionWeight" )
public class UpdateWeight implements Update< CountryDistribution > {

    protected final CountryDistributionRepository countryDistributionRepository;


    public UpdateWeight( CountryDistributionRepository countryDistributionRepository ) {
        this.countryDistributionRepository = countryDistributionRepository;
    }


    @Override
    public void update( Request request, CountryDistribution countryDistribution ) {
        Float weight = request.getParameter( CountryDistributionParameter.WEIGHT, Float.class );

        countryDistribution.setWeight( weight );

        countryDistributionRepository.persist( countryDistribution );
    }
}
