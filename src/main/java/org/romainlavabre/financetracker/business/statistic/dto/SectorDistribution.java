package org.romainlavabre.financetracker.business.statistic.dto;

import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;

public class SectorDistribution {

    @Json( groups = @Group )
    public final String sector;

    @Json( groups = @Group )
    public final float weight;


    public SectorDistribution( String sector, float weight ) {
        this.sector = sector;
        this.weight = weight;
    }
}
