package org.romainlavabre.financetracker.controller;

import jakarta.transaction.Transactional;
import org.romainlavabre.crud.Create;
import org.romainlavabre.crud.Delete;
import org.romainlavabre.crud.Update;
import org.romainlavabre.database.DataStorageHandler;
import org.romainlavabre.encoder.Encoder;
import org.romainlavabre.financetracker.entity.AnnuallyYield;
import org.romainlavabre.financetracker.entity.ExchangeTradedFund;
import org.romainlavabre.financetracker.repository.AnnuallyYieldRepository;
import org.romainlavabre.financetracker.repository.ExchangeTradedFundRepository;
import org.romainlavabre.request.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( path = "/annually_yields" )
public class AnnuallyYieldController {
    protected final Create< AnnuallyYield >      createAnnuallyYield;
    protected final Update< AnnuallyYield >      updateAnnuallyYieldYield;
    protected final Delete< AnnuallyYield >      deleteAnnuallyYield;
    protected final AnnuallyYieldRepository      annuallyYieldRepository;
    protected final ExchangeTradedFundRepository exchangeTradedFundRepository;
    protected final DataStorageHandler           dataStorageHandler;
    protected final Request                      request;


    public AnnuallyYieldController( Create< AnnuallyYield > createAnnuallyYield, Update< AnnuallyYield > updateAnnuallyYieldYield, Delete< AnnuallyYield > deleteAnnuallyYield, AnnuallyYieldRepository annuallyYieldRepository, ExchangeTradedFundRepository exchangeTradedFundRepository, DataStorageHandler dataStorageHandler, Request request ) {
        this.createAnnuallyYield          = createAnnuallyYield;
        this.updateAnnuallyYieldYield     = updateAnnuallyYieldYield;
        this.deleteAnnuallyYield          = deleteAnnuallyYield;
        this.annuallyYieldRepository      = annuallyYieldRepository;
        this.exchangeTradedFundRepository = exchangeTradedFundRepository;
        this.dataStorageHandler           = dataStorageHandler;
        this.request                      = request;
    }


    @GetMapping( path = "/{id:[0-9]+}" )
    public ResponseEntity< Map< String, Object > > findOne( @PathVariable( "id" ) long id ) {
        AnnuallyYield annuallyYield = annuallyYieldRepository.findOrFail( id );

        return ResponseEntity.ok( Encoder.encode( annuallyYield ) );
    }


    @GetMapping( path = "/by/exchange_traded_fund/{id:[0-9]+}" )
    public ResponseEntity< List< Map< String, Object > > > findAllByExchangeTradedFund( @PathVariable( "id" ) long id ) {
        ExchangeTradedFund exchangeTradedFund = exchangeTradedFundRepository.findOrFail( id );

        return ResponseEntity.ok( Encoder.encode( exchangeTradedFund.getAnnuallyYields() ) );
    }


    @Transactional
    @PostMapping
    public ResponseEntity< Map< String, Object > > create() {
        AnnuallyYield annuallyYield = new AnnuallyYield();

        createAnnuallyYield.create( request, annuallyYield );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( annuallyYield ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/yield" )
    public ResponseEntity< Map< String, Object > > updateYield( @PathVariable( "id" ) long id ) {
        AnnuallyYield annuallyYield = annuallyYieldRepository.findOrFail( id );

        updateAnnuallyYieldYield.update( request, annuallyYield );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( annuallyYield ) );
    }


    @Transactional
    @DeleteMapping( path = "/{id:[0-9]+}" )
    public ResponseEntity< Void > delete( @PathVariable( "id" ) long id ) {
        AnnuallyYield annuallyYield = annuallyYieldRepository.findOrFail( id );

        deleteAnnuallyYield.delete( request, annuallyYield );

        dataStorageHandler.save();

        return ResponseEntity.noContent().build();
    }
}
