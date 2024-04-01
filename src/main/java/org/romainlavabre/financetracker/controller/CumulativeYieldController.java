package org.romainlavabre.financetracker.controller;

import jakarta.transaction.Transactional;
import org.romainlavabre.crud.Create;
import org.romainlavabre.crud.Update;
import org.romainlavabre.database.DataStorageHandler;
import org.romainlavabre.encoder.Encoder;
import org.romainlavabre.financetracker.entity.CumulativeYield;
import org.romainlavabre.financetracker.repository.CumulativeYieldRepository;
import org.romainlavabre.request.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping( path = "/cumulative_yields" )
public class CumulativeYieldController {

    protected final Create< CumulativeYield > createCumulativeYield;
    protected final Update< CumulativeYield > updateCumulativeYieldToTenYear;
    protected final Update< CumulativeYield > updateCumulativeYieldToFiveYear;
    protected final Update< CumulativeYield > updateCumulativeYieldToThreeYear;
    protected final Update< CumulativeYield > updateCumulativeYieldToOneYear;
    protected final Update< CumulativeYield > updateCumulativeYieldSinceCreation;
    protected final CumulativeYieldRepository cumulativeYieldRepository;
    protected final DataStorageHandler        dataStorageHandler;
    protected final Request                   request;


    public CumulativeYieldController( Create< CumulativeYield > createCumulativeYield, Update< CumulativeYield > updateCumulativeYieldToTenYear, Update< CumulativeYield > updateCumulativeYieldToFiveYear, Update< CumulativeYield > updateCumulativeYieldToThreeYear, Update< CumulativeYield > updateCumulativeYieldToOneYear, Update< CumulativeYield > updateCumulativeYieldSinceCreation, CumulativeYieldRepository cumulativeYieldRepository, DataStorageHandler dataStorageHandler, Request request ) {
        this.createCumulativeYield              = createCumulativeYield;
        this.updateCumulativeYieldToTenYear     = updateCumulativeYieldToTenYear;
        this.updateCumulativeYieldToFiveYear    = updateCumulativeYieldToFiveYear;
        this.updateCumulativeYieldToThreeYear   = updateCumulativeYieldToThreeYear;
        this.updateCumulativeYieldToOneYear     = updateCumulativeYieldToOneYear;
        this.updateCumulativeYieldSinceCreation = updateCumulativeYieldSinceCreation;
        this.cumulativeYieldRepository          = cumulativeYieldRepository;
        this.dataStorageHandler                 = dataStorageHandler;
        this.request                            = request;
    }


    @GetMapping( path = "/{id:[0-9]+}" )
    public ResponseEntity< Map< String, Object > > findOne( @PathVariable( "id" ) long id ) {
        CumulativeYield cumulativeYield = cumulativeYieldRepository.findOrFail( id );

        return ResponseEntity.ok( Encoder.encode( cumulativeYield ) );
    }


    @Transactional
    @PostMapping
    public ResponseEntity< Map< String, Object > > create() {
        CumulativeYield cumulativeYield = new CumulativeYield();

        createCumulativeYield.create( request, cumulativeYield );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( cumulativeYield ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/to_ten_year" )
    public ResponseEntity< Map< String, Object > > updateToTenYear( @PathVariable( "id" ) long id ) {
        CumulativeYield cumulativeYield = cumulativeYieldRepository.findOrFail( id );

        updateCumulativeYieldToTenYear.update( request, cumulativeYield );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( cumulativeYield ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/to_five_year" )
    public ResponseEntity< Map< String, Object > > updateToFiveYear( @PathVariable( "id" ) long id ) {
        CumulativeYield cumulativeYield = cumulativeYieldRepository.findOrFail( id );

        updateCumulativeYieldToFiveYear.update( request, cumulativeYield );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( cumulativeYield ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/to_three_year" )
    public ResponseEntity< Map< String, Object > > updateToThreeYear( @PathVariable( "id" ) long id ) {
        CumulativeYield cumulativeYield = cumulativeYieldRepository.findOrFail( id );

        updateCumulativeYieldToThreeYear.update( request, cumulativeYield );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( cumulativeYield ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/to_one_year" )
    public ResponseEntity< Map< String, Object > > updateToOneYear( @PathVariable( "id" ) long id ) {
        CumulativeYield cumulativeYield = cumulativeYieldRepository.findOrFail( id );

        updateCumulativeYieldToOneYear.update( request, cumulativeYield );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( cumulativeYield ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/since_creation" )
    public ResponseEntity< Map< String, Object > > updateSinceCreation( @PathVariable( "id" ) long id ) {
        CumulativeYield cumulativeYield = cumulativeYieldRepository.findOrFail( id );

        updateCumulativeYieldSinceCreation.update( request, cumulativeYield );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( cumulativeYield ) );
    }
}
