package org.romainlavabre.financetracker.controller;

import jakarta.transaction.Transactional;
import org.romainlavabre.crud.Create;
import org.romainlavabre.crud.Update;
import org.romainlavabre.database.DataStorageHandler;
import org.romainlavabre.encoder.Encoder;
import org.romainlavabre.financetracker.entity.ExchangeTradedFund;
import org.romainlavabre.financetracker.entity.SectorDistribution;
import org.romainlavabre.financetracker.repository.ExchangeTradedFundRepository;
import org.romainlavabre.financetracker.repository.SectorDistributionRepository;
import org.romainlavabre.request.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( path = "/sector_distributions" )
public class SectorDistributionController {
    protected final Create< SectorDistribution > createSectorDistribution;
    protected final Update< SectorDistribution > updateSectorDistributionWeight;
    protected final SectorDistributionRepository sectorDistributionRepository;
    protected final ExchangeTradedFundRepository exchangeTradedFundRepository;
    protected final DataStorageHandler           dataStorageHandler;
    protected final Request                      request;


    public SectorDistributionController( Create< SectorDistribution > createSectorDistribution, Update< SectorDistribution > updateSectorDistributionWeight, SectorDistributionRepository sectorDistributionRepository, ExchangeTradedFundRepository exchangeTradedFundRepository, DataStorageHandler dataStorageHandler, Request request ) {
        this.createSectorDistribution       = createSectorDistribution;
        this.updateSectorDistributionWeight = updateSectorDistributionWeight;
        this.sectorDistributionRepository   = sectorDistributionRepository;
        this.exchangeTradedFundRepository   = exchangeTradedFundRepository;
        this.dataStorageHandler             = dataStorageHandler;
        this.request                        = request;
    }


    @GetMapping( path = "/{id:[0-9]+}" )
    public ResponseEntity< Map< String, Object > > findOne( @PathVariable( "id" ) long id ) {
        SectorDistribution sectorDistribution = sectorDistributionRepository.findOrFail( id );

        return ResponseEntity.ok( Encoder.encode( sectorDistribution ) );
    }


    @GetMapping( path = "/by/exchange_traded_fund/{id:[0-9]+}" )
    public ResponseEntity< List< Map< String, Object > > > findAllByExchangeTradedFund( @PathVariable( "id" ) long id ) {
        ExchangeTradedFund exchangeTradedFund = exchangeTradedFundRepository.findOrFail( id );

        return ResponseEntity.ok( Encoder.encode( exchangeTradedFund.getSectorDistributions() ) );
    }


    @Transactional
    @PostMapping
    public ResponseEntity< Map< String, Object > > create() {
        SectorDistribution sectorDistribution = new SectorDistribution();

        createSectorDistribution.create( request, sectorDistribution );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( sectorDistribution ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/weight" )
    public ResponseEntity< Map< String, Object > > updateWeight( @PathVariable( "id" ) long id ) {
        SectorDistribution sectorDistribution = sectorDistributionRepository.findOrFail( id );

        updateSectorDistributionWeight.update( request, sectorDistribution );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( sectorDistribution ) );
    }
}
