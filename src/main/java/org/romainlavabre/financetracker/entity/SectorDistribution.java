package org.romainlavabre.financetracker.entity;

import jakarta.persistence.*;
import org.romainlavabre.exception.HttpUnprocessableEntityException;
import org.romainlavabre.financetracker.configuration.response.Message;

@Entity
public class SectorDistribution {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    protected long id;

    protected float weight;

    @ManyToOne
    @JoinColumn( nullable = false )
    protected Sector sector;

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
