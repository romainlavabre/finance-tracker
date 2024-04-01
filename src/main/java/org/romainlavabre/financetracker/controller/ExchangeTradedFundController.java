package org.romainlavabre.financetracker.controller;

import jakarta.transaction.Transactional;
import org.romainlavabre.crud.Create;
import org.romainlavabre.crud.Update;
import org.romainlavabre.database.DataStorageHandler;
import org.romainlavabre.encoder.Encoder;
import org.romainlavabre.financetracker.entity.ExchangeTradedFund;
import org.romainlavabre.financetracker.repository.ExchangeTradedFundRepository;
import org.romainlavabre.request.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping( path = "/exchange_traded_funds" )
public class ExchangeTradedFundController {

    protected final Create< ExchangeTradedFund > createExchangeTradedFund;
    protected final Update< ExchangeTradedFund > updateExchangeTradedFundAmount;
    protected final Update< ExchangeTradedFund > updateExchangeTradedFundProductId;
    protected final Update< ExchangeTradedFund > updateExchangeTradedFundProvider;
    protected final Update< ExchangeTradedFund > updateExchangeTradedFundPageLink;
    protected final Update< ExchangeTradedFund > updateExchangeTradedFundPricing;
    protected final Update< ExchangeTradedFund > updateExchangeTradedFundRisk;
    protected final Update< ExchangeTradedFund > updateExchangeTradedFundPublicName;
    protected final ExchangeTradedFundRepository exchangeTradedFundRepository;
    protected final Request                      request;
    protected final DataStorageHandler           dataStorageHandler;


    public ExchangeTradedFundController( Create< ExchangeTradedFund > createExchangeTradedFund, Update< ExchangeTradedFund > updateExchangeTradedFundAmount, Update< ExchangeTradedFund > updateExchangeTradedFundProductId, Update< ExchangeTradedFund > updateExchangeTradedFundProvider, Update< ExchangeTradedFund > updateExchangeTradedFundPageLink, Update< ExchangeTradedFund > updateExchangeTradedFundPricing, Update< ExchangeTradedFund > updateExchangeTradedFundRisk, Update< ExchangeTradedFund > updateExchangeTradedFundPublicName, ExchangeTradedFundRepository exchangeTradedFundRepository, Request request, DataStorageHandler dataStorageHandler ) {
        this.createExchangeTradedFund           = createExchangeTradedFund;
        this.updateExchangeTradedFundAmount     = updateExchangeTradedFundAmount;
        this.updateExchangeTradedFundProductId  = updateExchangeTradedFundProductId;
        this.updateExchangeTradedFundProvider   = updateExchangeTradedFundProvider;
        this.updateExchangeTradedFundPageLink   = updateExchangeTradedFundPageLink;
        this.updateExchangeTradedFundPricing    = updateExchangeTradedFundPricing;
        this.updateExchangeTradedFundRisk       = updateExchangeTradedFundRisk;
        this.updateExchangeTradedFundPublicName = updateExchangeTradedFundPublicName;
        this.exchangeTradedFundRepository       = exchangeTradedFundRepository;
        this.request                            = request;
        this.dataStorageHandler                 = dataStorageHandler;
    }


    @GetMapping( path = "/{id:[0-9]+}" )
    public ResponseEntity< Map< String, Object > > findOne( @PathVariable( "id" ) long id ) {
        ExchangeTradedFund exchangeTradedFund = exchangeTradedFundRepository.findOrFail( id );

        return ResponseEntity.ok( Encoder.encode( exchangeTradedFund ) );
    }


    @Transactional
    @PostMapping
    public ResponseEntity< Map< String, Object > > create() {
        ExchangeTradedFund exchangeTradedFund = new ExchangeTradedFund();

        createExchangeTradedFund.create( request, exchangeTradedFund );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( exchangeTradedFund ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/amount" )
    public ResponseEntity< Map< String, Object > > updateAmount( @PathVariable( "id" ) long id ) {
        ExchangeTradedFund exchangeTradedFund = exchangeTradedFundRepository.findOrFail( id );

        updateExchangeTradedFundAmount.update( request, exchangeTradedFund );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( exchangeTradedFund ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/page_link" )
    public ResponseEntity< Map< String, Object > > updatePageLink( @PathVariable( "id" ) long id ) {
        ExchangeTradedFund exchangeTradedFund = exchangeTradedFundRepository.findOrFail( id );

        updateExchangeTradedFundPageLink.update( request, exchangeTradedFund );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( exchangeTradedFund ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/pricing" )
    public ResponseEntity< Map< String, Object > > updatePricing( @PathVariable( "id" ) long id ) {
        ExchangeTradedFund exchangeTradedFund = exchangeTradedFundRepository.findOrFail( id );

        updateExchangeTradedFundPricing.update( request, exchangeTradedFund );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( exchangeTradedFund ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/product_id" )
    public ResponseEntity< Map< String, Object > > updateProductId( @PathVariable( "id" ) long id ) {
        ExchangeTradedFund exchangeTradedFund = exchangeTradedFundRepository.findOrFail( id );

        updateExchangeTradedFundProductId.update( request, exchangeTradedFund );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( exchangeTradedFund ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/provider" )
    public ResponseEntity< Map< String, Object > > updateProvider( @PathVariable( "id" ) long id ) {
        ExchangeTradedFund exchangeTradedFund = exchangeTradedFundRepository.findOrFail( id );

        updateExchangeTradedFundProvider.update( request, exchangeTradedFund );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( exchangeTradedFund ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/public_name" )
    public ResponseEntity< Map< String, Object > > updatePublicName( @PathVariable( "id" ) long id ) {
        ExchangeTradedFund exchangeTradedFund = exchangeTradedFundRepository.findOrFail( id );

        updateExchangeTradedFundPublicName.update( request, exchangeTradedFund );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( exchangeTradedFund ) );
    }


    @Transactional
    @PatchMapping( path = "/{id:[0-9]+}/risk" )
    public ResponseEntity< Map< String, Object > > updateRisk( @PathVariable( "id" ) long id ) {
        ExchangeTradedFund exchangeTradedFund = exchangeTradedFundRepository.findOrFail( id );

        updateExchangeTradedFundRisk.update( request, exchangeTradedFund );

        dataStorageHandler.save();

        return ResponseEntity.ok( Encoder.encode( exchangeTradedFund ) );
    }
}
