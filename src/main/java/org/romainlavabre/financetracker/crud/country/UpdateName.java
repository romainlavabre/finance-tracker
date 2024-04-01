package org.romainlavabre.financetracker.crud.country;

import org.romainlavabre.crud.Update;
import org.romainlavabre.financetracker.entity.Country;
import org.romainlavabre.financetracker.parameter.CountryParameter;
import org.romainlavabre.financetracker.repository.CountryRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "updateCountryName" )
public class UpdateName implements Update< Country > {
    protected final CountryRepository countryRepository;


    public UpdateName( CountryRepository countryRepository ) {
        this.countryRepository = countryRepository;
    }


    @Override
    public void update( Request request, Country country ) {
        String name = request.getParameter( CountryParameter.NAME, String.class );

        country.setName( name );

        countryRepository.persist( country );
    }
}
