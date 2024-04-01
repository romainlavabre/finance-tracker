package org.romainlavabre.financetracker.controller;

import jakarta.transaction.Transactional;
import org.romainlavabre.crud.Create;
import org.romainlavabre.crud.Update;
import org.romainlavabre.database.DataStorageHandler;
import org.romainlavabre.encoder.Encoder;
import org.romainlavabre.financetracker.entity.Country;
import org.romainlavabre.financetracker.repository.CountryRepository;
import org.romainlavabre.request.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping( path = "/countries" )
public class CountryController {
    protected final Create< Country >  createCountry;
    protected final Update< Country >  updateCountryName;
    protected final Update< Country >  updateCountryContinent;
    protected final CountryRepository  countryRepository;
    protected final DataStorageHandler dataStorageHandler;
    protected final Request            request;


    public CountryController( Create< Country > createCountry, Update< Country > updateCountryName, Update< Country > updateCountryContinent, CountryRepository countryRepository, DataStorageHandler dataStorageHandler, Request request ) {
        this.createCountry          = createCountry;
        this.updateCountryName      = updateCountryName;
        this.updateCountryContinent = updateCountryContinent;
        this.countryRepository      = countryRepository;
        this.dataStorageHandler     = dataStorageHandler;
        this.request                = request;
    }


    @GetMapping( path = "/{id:[0-9]+}" )
    public ResponseEntity< Map< String, Object > > findOne( @PathVariable( "id" ) long id ) {
        Country country = countryRepository.findOrFail( id );

        return ResponseEntity.ok( Encoder.encode( country ) );
    }


    @Transactional
    @PostMapping
    public ResponseEntity< Map< String, Object > > create() {
        Country country = new Country();

        createCountry.create( request, country );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( country ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/name" )
    public ResponseEntity< Map< String, Object > > updateName( @PathVariable( "id" ) long id ) {
        Country country = countryRepository.findOrFail( id );

        updateCountryName.update( request, country );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( country ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/continent" )
    public ResponseEntity< Map< String, Object > > updateContinent( @PathVariable( "id" ) long id ) {
        Country country = countryRepository.findOrFail( id );

        updateCountryContinent.update( request, country );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( country ) );
    }
}
