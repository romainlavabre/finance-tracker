package org.romainlavabre.financetracker.configuration.json.put;

import org.romainlavabre.encoder.put.Put;
import org.romainlavabre.financetracker.entity.CountryDistribution;
import org.springframework.stereotype.Service;

@Service
public class PutCountryName implements Put< CountryDistribution > {

    @Override
    public Object build( CountryDistribution countryDistribution ) {
        return countryDistribution.getCountry().getName();
    }
}
