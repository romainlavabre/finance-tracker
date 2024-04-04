package org.romainlavabre.financetracker.entity;

import jakarta.persistence.*;
import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;
import org.romainlavabre.encoder.annotation.JsonPut;
import org.romainlavabre.encoder.annotation.Row;
import org.romainlavabre.exception.HttpUnprocessableEntityException;
import org.romainlavabre.financetracker.configuration.json.put.PutCountryName;
import org.romainlavabre.financetracker.configuration.response.Message;

@JsonPut( groups = {
        @Group( row = {
                @Row( key = "country_name", handler = PutCountryName.class )
        } )
} )
@Entity
public class CountryDistribution {

    @Json( groups = {
            @Group
    } )
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    protected long id;

    @Json( groups = {
            @Group
    } )
    protected float weight;

    @Json( groups = {
            @Group
    } )
    @ManyToOne
    @JoinColumn( nullable = false )
    protected Country country;

    @Json( groups = {
            @Group
    } )
    @ManyToOne
    @JoinColumn( nullable = false )
    protected ExchangeTradedFund exchangeTradedFund;


    public long getId() {
        return id;
    }


    public float getWeight() {
        return weight;
    }


    public CountryDistribution setWeight( Float weight ) {
        if ( weight == null ) {
            throw new HttpUnprocessableEntityException( Message.COUNTRY_DISTRIBUTION_WEIGHT_REQUIRED );
        }

        this.weight = weight;

        return this;
    }


    public Country getCountry() {
        return country;
    }


    public CountryDistribution setCountry( Country country ) {
        this.country = country;

        return this;
    }


    public ExchangeTradedFund getExchangeTradedFund() {
        return exchangeTradedFund;
    }


    public CountryDistribution setExchangeTradedFund( ExchangeTradedFund exchangeTradedFund ) {
        this.exchangeTradedFund = exchangeTradedFund;

        if ( !exchangeTradedFund.getCountryDistributions().contains( this ) ) {
            exchangeTradedFund.addCountryDistribution( this );
        }

        return this;
    }
}
