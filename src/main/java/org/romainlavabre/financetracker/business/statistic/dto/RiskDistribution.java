package org.romainlavabre.financetracker.business.statistic.dto;

import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;

public class RiskDistribution {
    @Json( groups = @Group )
    public final byte risk;

    @Json( groups = @Group )
    public final float weight;


    public RiskDistribution( byte risk, float weight ) {
        this.risk   = risk;
        this.weight = weight;
    }
}
