package org.romainlavabre.financetracker.business.statistic.dto;

import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;

public class ProviderDistribution {
    @Json( groups = @Group )
    public final String provider;

    @Json( groups = @Group )
    public final float weight;


    public ProviderDistribution( String provider, float weight ) {
        this.provider = provider;
        this.weight   = weight;
    }
}
