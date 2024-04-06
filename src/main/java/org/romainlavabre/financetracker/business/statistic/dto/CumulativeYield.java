package org.romainlavabre.financetracker.business.statistic.dto;

import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;

public class CumulativeYield {

    @Json( groups = @Group )
    public final float toTenYear;

    @Json( groups = @Group )
    public final float toFiveYear;

    @Json( groups = @Group )
    public final float toThreeYear;

    @Json( groups = @Group )
    public final float toOneYear;

    @Json( groups = @Group )
    public final float sinceCreation;


    public CumulativeYield( float toTenYear, float toFiveYear, float toThreeYear, float toOneYear, float sinceCreation ) {
        this.toTenYear     = toTenYear;
        this.toFiveYear    = toFiveYear;
        this.toThreeYear   = toThreeYear;
        this.toOneYear     = toOneYear;
        this.sinceCreation = sinceCreation;
    }
}
