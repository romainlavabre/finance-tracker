package org.romainlavabre.financetracker.entity;

import jakarta.persistence.*;
import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;
import org.romainlavabre.exception.HttpUnprocessableEntityException;
import org.romainlavabre.financetracker.configuration.response.Message;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Entity
public class ExchangeTradedFund {

    @Json( groups = {
            @Group
    } )
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    protected long id;

    @Json( groups = {
            @Group
    } )
    @Column( nullable = false )
    protected String publicName;

    @Json( groups = {
            @Group
    } )
    @Column( nullable = false )
    protected String pageLink;

    @Json( groups = {
            @Group
    } )
    @Column( nullable = false )
    protected ZonedDateTime lastScrapAt;


    public long getId() {
        return id;
    }


    public String getPublicName() {
        return publicName;
    }


    public ExchangeTradedFund setPublicName( String publicName ) {
        if ( publicName == null || publicName.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.EXCHANGE_TRADED_FUND_PUBLIC_NAME_REQUIRED );
        }

        this.publicName = publicName;

        return this;
    }


    public String getPageLink() {
        return pageLink;
    }


    public ExchangeTradedFund setPageLink( String pageLink ) {
        if ( pageLink == null || pageLink.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.EXCHANGE_TRADED_FUND_PAGE_LINK_REQUIRED );
        }

        this.pageLink = pageLink;

        return this;
    }


    public ZonedDateTime getLastScrapAt() {
        return lastScrapAt;
    }


    public ExchangeTradedFund scraped() {
        this.lastScrapAt = ZonedDateTime.now( ZoneOffset.UTC );

        return this;
    }
}