package org.romainlavabre.financetracker.entity;

import jakarta.persistence.*;
import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;
import org.romainlavabre.exception.HttpConflictException;
import org.romainlavabre.exception.HttpUnprocessableEntityException;
import org.romainlavabre.financetracker.configuration.response.Message;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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
    protected String productId;

    @Json( groups = {
            @Group
    } )
    @Column( nullable = false )
    protected String provider;

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
    protected float pricing;

    @Json( groups = {
            @Group
    } )
    protected byte risk;

    @Json( groups = {
            @Group
    } )
    protected float amount;

    @Json( groups = {
            @Group
    } )
    @Column( nullable = false )
    protected ZonedDateTime lastScrapAt;

    @OneToMany( cascade = CascadeType.PERSIST, mappedBy = "exchangeTradedFund" )
    protected final List< AnnuallyYield > annuallyYields;

    @OneToOne( cascade = CascadeType.PERSIST, mappedBy = "exchangeTradedFund" )
    protected CumulativeYield cumulativeYield;


    public ExchangeTradedFund() {
        lastScrapAt    = ZonedDateTime.now( ZoneOffset.UTC );
        annuallyYields = new ArrayList<>();
    }


    public long getId() {
        return id;
    }


    public String getProductId() {
        return productId;
    }


    public ExchangeTradedFund setProductId( String productId ) {
        if ( productId == null || productId.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.EXCHANGE_TRADED_FUND_PRODUCT_ID_REQUIRED );
        }

        this.productId = productId;

        return this;
    }


    public String getProvider() {
        return provider;
    }


    public ExchangeTradedFund setProvider( String provider ) {
        if ( provider == null || provider.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.EXCHANGE_TRADED_FUND_PROVIDER_REQUIRED );
        }

        this.provider = provider.toUpperCase();

        return this;
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


    public float getPricing() {
        return pricing;
    }


    public ExchangeTradedFund setPricing( Float pricing ) {
        if ( pricing == null ) {
            throw new HttpUnprocessableEntityException( Message.EXCHANGE_TRADED_FUND_PRICING_REQUIRED );
        }

        this.pricing = pricing;

        return this;
    }


    public byte getRisk() {
        return risk;
    }


    public ExchangeTradedFund setRisk( Byte risk ) {
        if ( risk == null ) {
            throw new HttpUnprocessableEntityException( Message.EXCHANGE_TRADED_FUND_RISK_REQUIRED );
        }

        this.risk = risk;

        return this;
    }


    public float getAmount() {
        return amount;
    }


    public ExchangeTradedFund setAmount( Float amount ) {
        if ( amount == null ) {
            throw new HttpUnprocessableEntityException( Message.EXCHANGE_TRADED_FUND_AMOUNT_REQUIRED );
        }

        this.amount = amount;

        return this;
    }


    public ZonedDateTime getLastScrapAt() {
        return lastScrapAt;
    }


    public ExchangeTradedFund scraped() {
        this.lastScrapAt = ZonedDateTime.now( ZoneOffset.UTC );

        return this;
    }


    public List< AnnuallyYield > getAnnuallyYields() {
        return annuallyYields;
    }


    public ExchangeTradedFund addAnnuallyYield( AnnuallyYield annuallyYield ) {
        if ( !annuallyYields.contains( annuallyYield ) ) {
            for ( AnnuallyYield localAnnuallyYield : annuallyYields ) {
                if ( localAnnuallyYield.getYear() == annuallyYield.getYear() ) {
                    throw new HttpConflictException( Message.ANNUALLY_YIELD_ALREADY_EXISTS );
                }
            }

            annuallyYields.add( annuallyYield );

            if ( annuallyYield.getExchangeTradedFund() != this ) {
                annuallyYield.setExchangeTradedFund( this );
            }
        }

        return this;
    }


    public CumulativeYield getCumulativeYield() {
        return cumulativeYield;
    }


    public ExchangeTradedFund setCumulativeYield( CumulativeYield cumulativeYield ) {
        this.cumulativeYield = cumulativeYield;

        if ( cumulativeYield.getExchangeTradedFund() != this ) {
            cumulativeYield.setExchangeTradedFund( this );
        }

        return this;
    }
}
