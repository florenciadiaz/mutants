package ar.test.meli.mutants.controller;

import ar.test.meli.mutants.configuration.ApplicationProperties;
import ar.test.meli.mutants.model.exception.InvalidSequenceException;
import ar.test.meli.mutants.service.MutantDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MutantDetectionController {

    private final MutantDetectionService mutantDetectionService;
    private final ApplicationProperties properties;

    @Autowired
    public MutantDetectionController(MutantDetectionService mutantDetectionService, ApplicationProperties properties) {
        this.mutantDetectionService = mutantDetectionService;
        this.properties = properties;
    }

    @PostMapping(path = "/mutant")
    public ResponseEntity detectMutant(@RequestBody DNASampleRequest sample) {
        boolean isMutant;
        try {
            String[] dna = sample.getDna();
            isMutant = this.mutantDetectionService.verify(dna);
        } catch (InvalidSequenceException ex) {
            return ResponseEntity.badRequest().body(MessageResponse.error(ex.getMessage()));
        }
        return isMutant
                ? ResponseEntity.ok().body(MessageResponse.success(properties.getDetection().getMutantMessage()))
                : ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(MessageResponse.success(properties.getDetection().getNotMutantMessage()));
    }
}
