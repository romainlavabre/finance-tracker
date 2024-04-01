package org.romainlavabre.financetracker.crud.country;

import org.romainlavabre.crud.Update;
import org.romainlavabre.financetracker.entity.Country;
import org.romainlavabre.financetracker.parameter.CountryParameter;
import org.romainlavabre.financetracker.repository.CountryRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "updateCountryContinent" )
public class UpdateContinent implements Update< Country > {
    protected final CountryRepository countryRepository;


    public UpdateContinent( CountryRepository countryRepository ) {
        this.countryRepository = countryRepository;
    }


    @Override
    public void update( Request request, Country country ) {
        Byte continent = request.getParameter( CountryParameter.CONTINENT, Byte.class );

        country.setContinent( continent );

        countryRepository.persist( country );
    }
}
