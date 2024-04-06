package org.romainlavabre.financetracker.controller;

import org.romainlavabre.encoder.Encoder;
import org.romainlavabre.financetracker.business.statistic.StatisticBuilder;
import org.romainlavabre.financetracker.business.statistic.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( path = "/statistics" )
public class StatisticController {
    protected final StatisticBuilder statisticBuilder;


    public StatisticController( StatisticBuilder statisticBuilder ) {
        this.statisticBuilder = statisticBuilder;
    }


    @GetMapping( path = "/patrimony" )
    public ResponseEntity< Map< String, Object > > getPatrimony() {
        Patrimony patrimony = statisticBuilder.getPatrimony();

        return ResponseEntity.ok( Encoder.encode( patrimony ) );
    }


    @GetMapping( path = "/risk_distribution" )
    public ResponseEntity< List< Map< String, Object > > > getRiskDistribution() {
        List< RiskDistribution > riskDistribution = statisticBuilder.getRiskDistribution();

        return ResponseEntity.ok( Encoder.encode( riskDistribution ) );
    }


    @GetMapping( path = "/provider_distribution" )
    public ResponseEntity< List< Map< String, Object > > > getProviderDistribution() {
        List< ProviderDistribution > providerDistribution = statisticBuilder.getProviderDistribution();

        return ResponseEntity.ok( Encoder.encode( providerDistribution ) );
    }


    @GetMapping( path = "/exchange_traded_fund_distribution" )
    public ResponseEntity< List< Map< String, Object > > > getExchangeTradedFundDistribution() {
        List< ExchangeTradedFundDistribution > exchangeTradedFundDistribution = statisticBuilder.getExchangeTradedFundDistribution();

        return ResponseEntity.ok( Encoder.encode( exchangeTradedFundDistribution ) );
    }


    @GetMapping( path = "/country_distribution" )
    public ResponseEntity< List< Map< String, Object > > > getCountryDistribution() {
        List< CountryDistribution > countryDistribution = statisticBuilder.getCountryDistribution();

        return ResponseEntity.ok( Encoder.encode( countryDistribution ) );
    }


    @GetMapping( path = "/continent_distribution" )
    public ResponseEntity< List< Map< String, Object > > > getContinentDistribution() {
        List< ContinentDistribution > continentDistribution = statisticBuilder.getContinentDistribution();

        return ResponseEntity.ok( Encoder.encode( continentDistribution ) );
    }


    @GetMapping( path = "/sector_distribution" )
    public ResponseEntity< List< Map< String, Object > > > getSectorDistribution() {
        List< SectorDistribution > sectorDistribution = statisticBuilder.getSectorDistribution();

        return ResponseEntity.ok( Encoder.encode( sectorDistribution ) );
    }


    @GetMapping( path = "/past_performance" )
    public ResponseEntity< List< Map< String, Object > > > getPastPerformanceAggregateByYear() {
        List< PastPerformanceAggregateByYear > pastPerformanceAggregateByYear = statisticBuilder.getPastPerformanceAggregateByYear();

        return ResponseEntity.ok( Encoder.encode( pastPerformanceAggregateByYear ) );
    }


    @GetMapping( path = "/cumulative_yield" )
    public ResponseEntity< Map< String, Object > > getCumulativeYield() {
        CumulativeYield cumulativeYield = statisticBuilder.getCumulativeYield();

        return ResponseEntity.ok( Encoder.encode( cumulativeYield ) );
    }


    @GetMapping( path = "/average_pricing" )
    public ResponseEntity< Map< String, Object > > getAveragePricing() {
        AveragePricing averagePricing = statisticBuilder.getAveragePricing();

        return ResponseEntity.ok( Encoder.encode( averagePricing ) );
    }
}
