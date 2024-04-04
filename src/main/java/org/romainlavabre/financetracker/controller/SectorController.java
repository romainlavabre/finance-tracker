package org.romainlavabre.financetracker.controller;

import jakarta.transaction.Transactional;
import org.romainlavabre.crud.Create;
import org.romainlavabre.crud.Update;
import org.romainlavabre.database.DataStorageHandler;
import org.romainlavabre.encoder.Encoder;
import org.romainlavabre.financetracker.entity.Sector;
import org.romainlavabre.financetracker.repository.SectorRepository;
import org.romainlavabre.request.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( path = "/sectors" )
public class SectorController {
    protected final Create< Sector >   createSector;
    protected final Update< Sector >   updateSectorName;
    protected final SectorRepository   sectorRepository;
    protected final DataStorageHandler dataStorageHandler;
    protected final Request            request;


    public SectorController( Create< Sector > createSector, Update< Sector > updateSectorName, SectorRepository sectorRepository, DataStorageHandler dataStorageHandler, Request request ) {
        this.createSector       = createSector;
        this.updateSectorName   = updateSectorName;
        this.sectorRepository   = sectorRepository;
        this.dataStorageHandler = dataStorageHandler;
        this.request            = request;
    }


    @GetMapping( path = "/{id:[0-9]+}" )
    public ResponseEntity< Map< String, Object > > findOne( @PathVariable( "id" ) long id ) {
        Sector sector = sectorRepository.findOrFail( id );

        return ResponseEntity.ok( Encoder.encode( sector ) );
    }


    @GetMapping
    public ResponseEntity< List< Map< String, Object > > > findAll() {
        List< Sector > sectors = sectorRepository.findAll();

        return ResponseEntity.ok( Encoder.encode( sectors ) );
    }


    @Transactional
    @PostMapping
    public ResponseEntity< Map< String, Object > > create() {
        Sector sector = new Sector();

        createSector.create( request, sector );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( sector ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/name" )
    public ResponseEntity< Map< String, Object > > updateName( @PathVariable( "id" ) long id ) {
        Sector sector = sectorRepository.findOrFail( id );

        updateSectorName.update( request, sector );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( sector ) );
    }
}
