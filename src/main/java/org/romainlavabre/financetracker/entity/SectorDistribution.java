package org.romainlavabre.financetracker.entity;

import jakarta.persistence.*;
import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;
import org.romainlavabre.encoder.annotation.JsonPut;
import org.romainlavabre.encoder.annotation.Row;
import org.romainlavabre.exception.HttpUnprocessableEntityException;
import org.romainlavabre.financetracker.configuration.json.put.PutSectorName;
import org.romainlavabre.financetracker.configuration.response.Message;

@JsonPut( groups = {
        @Group( row = {
                @Row( key = "sector_name", handler = PutSectorName.class )
        } )
} )
@Entity
public class SectorDistribution {

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
    protected Sector sector;

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


    public SectorDistribution setWeight( Float weight ) {
        if ( weight == null ) {
            throw new HttpUnprocessableEntityException( Message.SECTOR_DISTRIBUTION_WEIGHT_REQUIRED );
        }

        this.weight = weight;

        return this;
    }


    public Sector getSector() {
        return sector;
    }


    public SectorDistribution setSector( Sector sector ) {
        this.sector = sector;

        return this;
    }


    public ExchangeTradedFund getExchangeTradedFund() {
        return exchangeTradedFund;
    }


    public SectorDistribution setExchangeTradedFund( ExchangeTradedFund exchangeTradedFund ) {
        this.exchangeTradedFund = exchangeTradedFund;

        if ( !exchangeTradedFund.getSectorDistributions().contains( this ) ) {
            exchangeTradedFund.addSectorDistribution( this );
        }

        return this;
    }
}
