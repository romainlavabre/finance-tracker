package org.romainlavabre.financetracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PingController {

    @GetMapping(path = "/ping")
    public ResponseEntity<Void> ping(){
        return ResponseEntity.noContent().build();
    }
}
