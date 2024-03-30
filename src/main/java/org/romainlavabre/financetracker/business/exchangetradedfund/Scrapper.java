package org.romainlavabre.financetracker.business.exchangetradedfund;

import org.romainlavabre.encoder.annotation.Group;
import org.romainlavabre.encoder.annotation.Json;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public interface Scrapper {
    ScrapperResult scrape( String pageLink );


    class ScrapperResult {
        @Json( groups = {
                @Group
        } )
        protected final String productId;

        @Json( groups = {
                @Group
        } )
        protected float pricing;

        @Json( groups = {
                @Group
        } )
        protected final Map< Integer, Float > annuallyYield;

        @Json( groups = {
                @Group
        } )
        protected final Map< Integer, Float > cumulativeYield;

        @Json( groups = {
                @Group
        } )
        protected final Map< String, Float > countryDistribution;

        @Json( groups = {
                @Group
        } )
        protected final Map< String, Float > currencyDistribution;

        @Json( groups = {
                @Group
        } )
        protected final Map< String, Float > sectorDistribution;


        public ScrapperResult( String productId ) {
            this.productId       = productId;
            annuallyYield        = new TreeMap<>();
            cumulativeYield      = new TreeMap<>();
            countryDistribution  = new HashMap<>();
            currencyDistribution = new HashMap<>();
            sectorDistribution   = new HashMap<>();
        }


        public ScrapperResult setPricing( float pricing ) {
            this.pricing = pricing;

            return this;
        }


        public ScrapperResult addAnnuallyYield( int year, float yield ) {
            annuallyYield.put( year, yield );

            return this;
        }


        public ScrapperResult addCumulativeYield( int to, float yield ) {
            cumulativeYield.put( to, yield );

            return this;
        }


        public ScrapperResult addCountryDistribution( String country, float distribution ) {
            countryDistribution.put( country, distribution );

            return this;
        }


        public ScrapperResult addCurrencyDistribution( String currency, float distribution ) {
            currencyDistribution.put( currency, distribution );

            return this;
        }


        public ScrapperResult addSectorDistribution( String currency, float distribution ) {
            sectorDistribution.put( currency, distribution );

            return this;
        }
    }
}
