package org.romainlavabre.financetracker.controller;

import jakarta.transaction.Transactional;
import org.romainlavabre.crud.Create;
import org.romainlavabre.crud.Update;
import org.romainlavabre.database.DataStorageHandler;
import org.romainlavabre.encoder.Encoder;
import org.romainlavabre.financetracker.entity.CountryDistribution;
import org.romainlavabre.financetracker.entity.ExchangeTradedFund;
import org.romainlavabre.financetracker.repository.CountryDistributionRepository;
import org.romainlavabre.financetracker.repository.ExchangeTradedFundRepository;
import org.romainlavabre.request.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( path = "/country_distributions" )
public class CountryDistributionController {
    protected final Create< CountryDistribution > createCountryDistribution;
    protected final Update< CountryDistribution > updateCountryDistributionWeight;
    protected final CountryDistributionRepository countryDistributionRepository;
    protected final ExchangeTradedFundRepository  exchangeTradedFundRepository;
    protected final DataStorageHandler            dataStorageHandler;
    protected final Request                       request;


    public CountryDistributionController( Create< CountryDistribution > createCountryDistribution, Update< CountryDistribution > updateCountryDistributionWeight, CountryDistributionRepository countryDistributionRepository, ExchangeTradedFundRepository exchangeTradedFundRepository, DataStorageHandler dataStorageHandler, Request request ) {
        this.createCountryDistribution       = createCountryDistribution;
        this.updateCountryDistributionWeight = updateCountryDistributionWeight;
        this.countryDistributionRepository   = countryDistributionRepository;
        this.exchangeTradedFundRepository    = exchangeTradedFundRepository;
        this.dataStorageHandler              = dataStorageHandler;
        this.request                         = request;
    }


    @GetMapping( path = "/{id:[0-9]+}" )
    public ResponseEntity< Map< String, Object > > findOne( @PathVariable( "id" ) long id ) {
        CountryDistribution countryDistribution = countryDistributionRepository.findOrFail( id );

        return ResponseEntity.ok( Encoder.encode( countryDistribution ) );
    }


    @GetMapping( path = "/by/exchange_traded_fund/{id:[0-9]+}" )
    public ResponseEntity< List< Map< String, Object > > > findAllByExchangeTradedFund( @PathVariable( "id" ) long id ) {
        ExchangeTradedFund exchangeTradedFund = exchangeTradedFundRepository.findOrFail( id );

        return ResponseEntity.ok( Encoder.encode( exchangeTradedFund.getCountryDistributions() ) );
    }


    @Transactional
    @PostMapping
    public ResponseEntity< Map< String, Object > > create() {
        CountryDistribution countryDistribution = new CountryDistribution();

        createCountryDistribution.create( request, countryDistribution );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( countryDistribution ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/weight" )
    public ResponseEntity< Map< String, Object > > updateWeight( @PathVariable( "id" ) long id ) {
        CountryDistribution countryDistribution = countryDistributionRepository.findOrFail( id );

        updateCountryDistributionWeight.update( request, countryDistribution );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( countryDistribution ) );
    }
}
