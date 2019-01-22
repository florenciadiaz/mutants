package ar.test.meli.mutants.model;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SequenceTest {

    @Test
    void hasMutantDNA_givenMutantDNA_mustBeTrue() throws InvalidSequenceException {
        String[] dna6x6 = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence(dna6x6);

        boolean actual = sequence.hasMutantDNA(4);

        assertTrue(actual);
    }

    @Test
    void hasMutantDNA_givenHumanDNA_mustBeFalse() throws InvalidSequenceException {
        String[] dna6x6 = {"ATGCAA","CAGTGC","TTCTGT","AGAAGG","CGCCTA","TCACTG"};
        Sequence sequence = new Sequence(dna6x6);

//        "A T G C A A"
//        "C A G T G C"
//        "T T C T G T"
//        "A G A A G G"
//        "C G C C T A"
//        "T C A C T G"

        boolean actual = sequence.hasMutantDNA(4);

        assertFalse(actual);
    }

    @Test
    void hasMutantDNA_givenLessRowsThanColumns_mustThrowException()  {
        String[] dna5x6 = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA"};
        Sequence sequence = new Sequence(dna5x6);

        Throwable exception = assertThrows(InvalidSequenceException.class, () -> {
                                    sequence.hasMutantDNA(4);
                                });

        assertEquals("DNA sequence must have same amount of rows and columns", exception.getMessage());
    }

    @Test
    void hasMutantDNA_givenLessColumnsThanRows_mustThrowException()  {
        String[] dna6x5 = {"ATGCG","CAGTG","TTATG","AGAAG","CCCCT","TCACT"};
        Sequence sequence = new Sequence(dna6x5);

        Throwable exception = assertThrows(InvalidSequenceException.class, () -> {
                                    sequence.hasMutantDNA(4);
                                });

        assertEquals("DNA sequence must have same amount of rows and columns", exception.getMessage());
    }

    @Test
    void hasMutantDNA_givenMissingColumn_mustThrowException()  {
        String[] dnaMissingColumnAtRow2 = {"ATGCGA","CAGTGC","TTATG","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence(dnaMissingColumnAtRow2);

        Throwable exception = assertThrows(InvalidSequenceException.class, () -> {
                                    sequence.hasMutantDNA(4);
                                });

        assertEquals("DNA sequence must have same amount of rows and columns", exception.getMessage());
    }

    @Test
    void hasMutantDNA_givenExtraColumn_mustThrowException()  {
        String[] dnaExtraColumnAtRow2 = {"ATGCGA","CAGTGC","TTATGTT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence(dnaExtraColumnAtRow2);

        Throwable exception = assertThrows(InvalidSequenceException.class, () -> {
                                    sequence.hasMutantDNA(4);
                                });

        assertEquals("DNA sequence must have same amount of rows and columns", exception.getMessage());
    }

    @Test
    void hasMutantDNA_givenInvalidNitrogenousBase_mustThrowException() {
        String[] dnaWithXAtRow2 = {"ATGCGA","CAGTGC","TTXTGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence(dnaWithXAtRow2);

        Throwable exception = assertThrows(InvalidSequenceException.class, () -> {
                                    sequence.hasMutantDNA(4);
                                });

        assertEquals("DNA sequence must contain only valid nitrogenous bases", exception.getMessage());
    }

    @Test
    void hasMutantDNA_givenInvalidCharacterAtNitrogenousBase_mustThrowException() {
        String[] dnaWithInvalidCharAtRow2 = {"ATGCGA","CAGTGC","TT,TGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence(dnaWithInvalidCharAtRow2);

        Throwable exception = assertThrows(InvalidSequenceException.class, () -> {
                                    sequence.hasMutantDNA(4);
                                });

        assertEquals("DNA sequence must contain only valid nitrogenous bases", exception.getMessage());
    }

    @Test
    void hasMutantDNA_givenNumberAtNitrogenousBase_mustThrowException() {
        String[] dnaWithNumberAtRow2 = {"ATGCGA","CAGTGC","TT8TGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence(dnaWithNumberAtRow2);

        Throwable exception = assertThrows(InvalidSequenceException.class, () -> {
                                    sequence.hasMutantDNA(4);
                                });

        assertEquals("DNA sequence must contain only valid nitrogenous bases", exception.getMessage());
    }
}