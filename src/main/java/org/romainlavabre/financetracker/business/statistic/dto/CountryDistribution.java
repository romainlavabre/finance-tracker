package org.romainlavabre.financetracker.business.statistic.dto;

import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;

public class CountryDistribution {
    @Json( groups = @Group )
    public final String country;

    @Json( groups = @Group )
    public final float weight;


    public CountryDistribution( String country, float weight ) {
        this.country = country;
        this.weight  = weight;
    }
}
