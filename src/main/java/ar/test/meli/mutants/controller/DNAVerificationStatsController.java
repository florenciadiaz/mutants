package ar.test.meli.mutants.controller;

import ar.test.meli.mutants.configuration.ApplicationProperties;
import ar.test.meli.mutants.service.DNAVerificationStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DNAVerificationStatsController {

    private final DNAVerificationStatsService dnaVerificationStatsService;

    @Autowired
    public DNAVerificationStatsController(DNAVerificationStatsService dnaVerificationStatsService) {
        this.dnaVerificationStatsService = dnaVerificationStatsService;
    }

    @GetMapping(path = "/stats")
    public ResponseEntity detectMutant() {
        DNAVerificationStatsResponse dnaVerificationStatsResponse = dnaVerificationStatsService.calculateStats();
        return ResponseEntity.ok().body(dnaVerificationStatsResponse);
    }
}
