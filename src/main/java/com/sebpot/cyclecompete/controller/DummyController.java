package com.sebpot.cyclecompete.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/knook")
public class DummyController {

    @GetMapping
    public ResponseEntity<String> getKnook() {
        return ResponseEntity.ok("knook");
    }
}
