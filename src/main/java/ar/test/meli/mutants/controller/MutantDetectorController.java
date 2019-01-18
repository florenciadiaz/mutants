package ar.test.meli.mutants.controller;

import ar.test.meli.mutants.model.Detector;
import ar.test.meli.mutants.model.exception.InvalidSequenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MutantDetectorController {

    @PostMapping(path = "/mutant")
    public ResponseEntity detectMutant(@RequestBody DNASampleRequest sample) {
        Detector detector = new Detector();
        boolean isMutant;
        try {
             isMutant = detector.isMutant(sample.getDna());
        } catch (InvalidSequenceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return isMutant
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
