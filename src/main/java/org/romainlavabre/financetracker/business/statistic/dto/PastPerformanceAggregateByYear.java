package org.romainlavabre.financetracker.business.statistic.dto;

import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;

public class PastPerformanceAggregateByYear {
    @Json( groups = @Group )
    public final short year;

    @Json( groups = @Group )
    public final float weight;


    public PastPerformanceAggregateByYear( short year, float weight ) {
        this.year   = year;
        this.weight = weight;
    }
}
