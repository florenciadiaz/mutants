package ar.test.meli.mutants.controller;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;
import ar.test.meli.mutants.service.MutantDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MutantDetectionController {

    private static final String MUTANT = "Mutant";
    private static final String NOT_MUTANT = "Not-Mutant";

    @Autowired
    private final MutantDetectionService mutantDetectionService;

    public MutantDetectionController(MutantDetectionService mutantDetectionService) {
        this.mutantDetectionService = mutantDetectionService;
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
                ? ResponseEntity.ok().body(MessageResponse.success(MUTANT))
                : ResponseEntity.status(HttpStatus.FORBIDDEN).body(MessageResponse.success(NOT_MUTANT));
    }
}
