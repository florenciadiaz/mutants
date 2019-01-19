package ar.test.meli.mutants.controller;

import ar.test.meli.mutants.model.Detector;
import ar.test.meli.mutants.model.exception.InvalidSequenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MutantDetectorController {

    static final String MUTANT = "Mutant";
    static final String NOT_MUTANT = "Not-Mutant";

    @PostMapping(path = "/mutant")
    public ResponseEntity detectMutant(@RequestBody DNASampleRequest sample) {
        Detector detector = new Detector();
        boolean isMutant;
        try {
             isMutant = detector.isMutant(sample.getDna());
        } catch (InvalidSequenceException ex) {
            return ResponseEntity.badRequest().body(MessageResponse.error(ex.getMessage()));
        }
        return isMutant
                ? ResponseEntity.ok().body(MessageResponse.success(MUTANT))
                : ResponseEntity.status(HttpStatus.FORBIDDEN).body(MessageResponse.success(NOT_MUTANT));
    }
}
