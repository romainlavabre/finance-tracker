package org.romainlavabre.financetracker.business.statistic.dto;

import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;

public class ExchangeTradedFundDistribution {

    public final long id;

    @Json( groups = @Group )
    public final String name;

    @Json( groups = @Group )
    public final float weight;


    public ExchangeTradedFundDistribution( long id, String name, float weight ) {
        this.id     = id;
        this.name   = name;
        this.weight = weight;
    }
}
