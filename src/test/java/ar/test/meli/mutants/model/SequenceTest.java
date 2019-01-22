package ar.test.meli.mutants.model;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SequenceTest {

    @Test
    void hasMutantDNA_givenMutantDNA_mustBeTrue() throws InvalidSequenceException {
        String[] dna6x6 = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = Sequence.of(dna6x6, 4);

//        "A T G C G A"
//        "C A G T G C"
//        "T T A T G T"
//        "A G A A G G"
//        "C C C C T A"
//        "T C A C T G"

        boolean actual = sequence.hasMutantDNA();

        assertTrue(actual);
    }

    @Test
    void hasMutantDNA_givenHumanDNA_mustBeFalse() throws InvalidSequenceException {
        String[] dna6x6 = {"ATGCAA","CAGTGC","TTCTGT","AGAAGG","CGCCTA","TCACTG"};
        Sequence sequence = Sequence.of(dna6x6, 4);

//        "A T G C A A"
//        "C A G T G C"
//        "T T C T G T"
//        "A G A A G G"
//        "C G C C T A"
//        "T C A C T G"

        boolean actual = sequence.hasMutantDNA();

        assertFalse(actual);
    }

    @Test
    void hasMutantDNA_givenLessRowsThanColumns_mustThrowException() throws InvalidSequenceException {
        String[] dna5x6 = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA"};
        Sequence sequence = Sequence.of(dna5x6, 4);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::hasMutantDNA);

        assertEquals("DNA sequence must have same amount of rows and columns", exception.getMessage());
    }

    @Test
    void hasMutantDNA_givenLessColumnsThanRows_mustThrowException() throws InvalidSequenceException {
        String[] dna6x5 = {"ATGCG","CAGTG","TTATG","AGAAG","CCCCT","TCACT"};
        Sequence sequence = Sequence.of(dna6x5, 4);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::hasMutantDNA);

        assertEquals("DNA sequence must have same amount of rows and columns", exception.getMessage());
    }

    @Test
    void hasMutantDNA_givenMissingColumn_mustThrowException() throws InvalidSequenceException {
        String[] dnaMissingColumnAtRow2 = {"ATGCGA","CAGTGC","TTATG","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = Sequence.of(dnaMissingColumnAtRow2, 4);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::hasMutantDNA);

        assertEquals("DNA sequence must have same amount of rows and columns", exception.getMessage());
    }

    @Test
    void hasMutantDNA_givenExtraColumn_mustThrowException() throws InvalidSequenceException {
        String[] dnaExtraColumnAtRow2 = {"ATGCGA","CAGTGC","TTATGTT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = Sequence.of(dnaExtraColumnAtRow2, 4);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::hasMutantDNA);

        assertEquals("DNA sequence must have same amount of rows and columns", exception.getMessage());
    }

    @Test
    void hasMutantDNA_givenInvalidNitrogenousBase_mustThrowException() throws InvalidSequenceException {
        String[] dnaWithXAtRow2 = {"ATGCGA","CAGTGC","TTXTGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = Sequence.of(dnaWithXAtRow2, 4);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::hasMutantDNA);

        assertEquals("DNA sequence must contain only valid nitrogenous bases", exception.getMessage());
    }

    @Test
    void hasMutant_givenInvalidCharacterAtNitrogenousBase_mustThrowException() throws InvalidSequenceException {
        String[] dnaWithInvalidCharAtRow2 = {"ATGCGA","CAGTGC","TT,TGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = Sequence.of(dnaWithInvalidCharAtRow2, 4);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::hasMutantDNA);

        assertEquals("DNA sequence must contain only valid nitrogenous bases", exception.getMessage());
    }

    @Test
    void hasMutant_givenNumberAtNitrogenousBase_mustThrowException() throws InvalidSequenceException {
        String[] dnaWithNumberAtRow2 = {"ATGCGA","CAGTGC","TT8TGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = Sequence.of(dnaWithNumberAtRow2, 4);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::hasMutantDNA);

        assertEquals("DNA sequence must contain only valid nitrogenous bases", exception.getMessage());
    }
}