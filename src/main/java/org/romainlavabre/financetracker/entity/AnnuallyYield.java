package org.romainlavabre.financetracker.entity;

import jakarta.persistence.*;
import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;
import org.romainlavabre.exception.HttpUnprocessableEntityException;
import org.romainlavabre.financetracker.configuration.response.Message;

@Entity
public class AnnuallyYield {

    @Json( groups = {
            @Group
    } )
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    protected long id;

    @Json( groups = {
            @Group
    } )
    protected short year;

    @Json( groups = {
            @Group
    } )
    protected float yield;

    @Json( groups = {
            @Group
    } )
    @ManyToOne( cascade = CascadeType.PERSIST )
    @JoinColumn( nullable = false )
    protected ExchangeTradedFund exchangeTradedFund;


    public long getId() {
        return id;
    }


    public short getYear() {
        return year;
    }


    public AnnuallyYield setYear( Short year ) {
        if ( year == null ) {
            throw new HttpUnprocessableEntityException( Message.ANNUALLY_YIELD_YEAR_REQUIRED );
        }

        this.year = year;

        return this;
    }


    public float getYield() {
        return yield;
    }


    public AnnuallyYield setYield( Float yield ) {
        if ( yield == null ) {
            throw new HttpUnprocessableEntityException( Message.ANNUALLY_YIELD_YIELD_REQUIRED );
        }

        this.yield = yield;

        return this;
    }


    public ExchangeTradedFund getExchangeTradedFund() {
        return exchangeTradedFund;
    }


    public AnnuallyYield setExchangeTradedFund( ExchangeTradedFund exchangeTradedFund ) {
        this.exchangeTradedFund = exchangeTradedFund;

        if ( !exchangeTradedFund.getAnnuallyYields().contains( this ) ) {
            exchangeTradedFund.addAnnuallyYield( this );
        }

        return this;
    }
}

