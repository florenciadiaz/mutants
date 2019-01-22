package ar.test.meli.mutants.controller;

import ar.test.meli.mutants.configuration.ApplicationProperties;
import ar.test.meli.mutants.service.DNAVerificationStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
public class DNAVerificationStatsController {

    private final DNAVerificationStatsService dnaVerificationStatsService;
    private final ApplicationProperties properties;

    @Autowired
    public DNAVerificationStatsController(DNAVerificationStatsService dnaVerificationStatsService,
                                          ApplicationProperties properties) {
        this.dnaVerificationStatsService = dnaVerificationStatsService;
        this.properties = properties;
    }

    @GetMapping(path = "/stats")
    public Callable<ResponseEntity> getStats() {
        Long mutantsCount = dnaVerificationStatsService.countMutants();
        Long humansCount = dnaVerificationStatsService.countHumans();
        float ratio = dnaVerificationStatsService.calculateRatio(mutantsCount, humansCount,
                properties.getStats().getRatioDecimalPlaces());

        DNAVerificationStatsResponse response = new DNAVerificationStatsResponse(mutantsCount, humansCount, ratio);
        return () -> ResponseEntity.ok().body(response);
    }
}
