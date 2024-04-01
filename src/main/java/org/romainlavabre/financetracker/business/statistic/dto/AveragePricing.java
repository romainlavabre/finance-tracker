package org.romainlavabre.financetracker.business.statistic.dto;

import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;

public class AveragePricing {

    @Json( groups = @Group )
    public final float averagePricing;


    public AveragePricing( float averagePricing ) {
        this.averagePricing = averagePricing;
    }
}
