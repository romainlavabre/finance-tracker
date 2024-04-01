package org.romainlavabre.financetracker.entity;

import jakarta.persistence.*;
import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;

@Entity
public class CumulativeYield {

    @Json( groups = @Group )
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    protected long id;

    @Json( groups = @Group )
    protected Float toTenYear;

    @Json( groups = @Group )
    protected Float toFiveYear;

    @Json( groups = @Group )
    protected Float toThreeYear;

    @Json( groups = @Group )
    protected Float toOneYear;

    @Json( groups = @Group )
    protected Float sinceCreation;
    
    @Json( groups = @Group )
    @OneToOne( cascade = CascadeType.ALL )
    @JoinColumn( nullable = false, unique = true )
    protected ExchangeTradedFund exchangeTradedFund;


    public long getId() {
        return id;
    }


    public Float getToTenYear() {
        return toTenYear;
    }


    public CumulativeYield setToTenYear( Float toTenYear ) {
        this.toTenYear = toTenYear;

        return this;
    }


    public Float getToFiveYear() {
        return toFiveYear;
    }


    public CumulativeYield setToFiveYear( Float toFiveYear ) {
        this.toFiveYear = toFiveYear;

        return this;
    }


    public Float getToThreeYear() {
        return toThreeYear;
    }


    public CumulativeYield setToThreeYear( Float toThreeYear ) {
        this.toThreeYear = toThreeYear;

        return this;
    }


    public Float getToOneYear() {
        return toOneYear;
    }


    public CumulativeYield setToOneYear( Float toOneYear ) {
        this.toOneYear = toOneYear;

        return this;
    }


    public Float getSinceCreation() {
        return sinceCreation;
    }


    public CumulativeYield setSinceCreation( Float sinceCreation ) {
        this.sinceCreation = sinceCreation;

        return this;
    }


    public ExchangeTradedFund getExchangeTradedFund() {
        return exchangeTradedFund;
    }


    public CumulativeYield setExchangeTradedFund( ExchangeTradedFund exchangeTradedFund ) {
        this.exchangeTradedFund = exchangeTradedFund;

        if ( exchangeTradedFund.getCumulativeYield() != this ) {
            exchangeTradedFund.setCumulativeYield( this );
        }

        return this;
    }
}
