package org.romainlavabre.financetracker.controller;

import org.romainlavabre.encoder.Encoder;
import org.romainlavabre.financetracker.business.exchangetradedfund.Scrapper;
import org.romainlavabre.request.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping( path = "/exchange_traded_funds" )
public class ExchangeTradedFundController {
    protected final Scrapper scrapper;
    protected final Request  request;


    public ExchangeTradedFundController( Scrapper scrapper, Request request ) {
        this.scrapper = scrapper;
        this.request  = request;
    }


    @PostMapping
    public ResponseEntity< Map< String, Object > > create() {

        return ResponseEntity.accepted().body( Encoder.encode( scrapper.scrape( request.getParameter( "path", String.class ) ) ) );
    }
}
