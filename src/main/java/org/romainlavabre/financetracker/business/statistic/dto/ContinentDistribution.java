package org.romainlavabre.financetracker.business.statistic.dto;

import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;

public class ContinentDistribution {
    @Json( groups = @Group )
    public final String continent;

    @Json( groups = @Group )
    public final float weight;


    public ContinentDistribution( String continent, float weight ) {
        this.continent = continent;
        this.weight    = weight;
    }
}
