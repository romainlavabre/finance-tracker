package org.romainlavabre.financetracker.controller;

import org.romainlavabre.financetracker.configuration.json.GroupType;
import org.romainlavabre.financetracker.configuration.pagination.dto.PaginationExchangeTradedFund;
import org.romainlavabre.pagination.Pagination;
import org.romainlavabre.pagination.PaginationHandler;
import org.romainlavabre.pagination.exception.*;
import org.romainlavabre.request.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping( path = "/paginations" )
public class PaginationController {
    protected final PaginationHandler paginationHandler;
    protected final Request           request;


    public PaginationController( PaginationHandler paginationHandler, Request request ) {
        this.paginationHandler = paginationHandler;
        this.request           = request;
    }


    @GetMapping( path = "/exchange_traded_fund" )
    public ResponseEntity< Map< String, Object > > exchangeTradedFund() throws FileError, NotSupportedDtoType, NotSupportedKey, NotSupportedOperator, NotSupportedValue {
        Pagination pagination = paginationHandler.getResult( request, PaginationExchangeTradedFund.class );

        return ResponseEntity.ok( pagination.encode( GroupType.DEFAULT ) );
    }
}
