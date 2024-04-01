package org.romainlavabre.financetracker.crud.country;

import org.romainlavabre.financetracker.entity.Country;
import org.romainlavabre.financetracker.parameter.CountryParameter;
import org.romainlavabre.financetracker.repository.CountryRepository;
import org.romainlavabre.request.Request;
import org.springframework.stereotype.Service;

@Service( "createCountry" )
public class Create implements org.romainlavabre.crud.Create< Country > {
    protected final CountryRepository countryRepository;


    public Create( CountryRepository countryRepository ) {
        this.countryRepository = countryRepository;
    }


    @Override
    public void create( Request request, Country country ) {
        String name      = request.getParameter( CountryParameter.NAME, String.class );
        Byte   continent = request.getParameter( CountryParameter.CONTINENT, Byte.class );

        country
                .setName( name )
                .setContinent( continent );

        countryRepository.persist( country );
    }
}
