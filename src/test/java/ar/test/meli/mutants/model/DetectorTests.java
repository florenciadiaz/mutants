package ar.test.meli.mutants.model;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DetectorTests {

    @Test
    void isMutantWithMutantDNAShouldBeTrue() throws InvalidSequenceException {
        Detector detector = new Detector();

        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        boolean actual = detector.isMutant(dna);

        assertTrue(actual);
    }
}