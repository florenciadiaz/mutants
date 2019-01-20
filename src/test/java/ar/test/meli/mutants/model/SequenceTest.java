package ar.test.meli.mutants.model;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SequenceTest {

    @Test
    void toTable_givenValidDNA_mustNotBeNull() throws InvalidSequenceException {
        String[] dna6x6 = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence(dna6x6);

        NitrogenousBase table = sequence.toTable();

        assertNotNull(table);
    }

    @Test
    void toTable_givenLessRowsThanColumns_mustThrowException()  {
        String[] dna5x6 = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA"};
        Sequence sequence = new Sequence(dna5x6);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::toTable);

        assertEquals("DNA sequence must have same amount of rows and columns", exception.getMessage());
    }

    @Test
    void toTable_givenLessColumnsThanRows_mustThrowException()  {
        String[] dna6x5 = {"ATGCG","CAGTG","TTATG","AGAAG","CCCCT","TCACT"};
        Sequence sequence = new Sequence(dna6x5);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::toTable);

        assertEquals("DNA sequence must have same amount of rows and columns", exception.getMessage());
    }

    @Test
    void toTable_givenMissingColumn_mustThrowException()  {
        String[] dnaMissingColumnAtRow2 = {"ATGCGA","CAGTGC","TTATG","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence(dnaMissingColumnAtRow2);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::toTable);

        assertEquals("DNA sequence must have same amount of rows and columns", exception.getMessage());
    }

    @Test
    void toTable_givenExtraColumn_mustThrowException()  {
        String[] dnaExtraColumnAtRow2 = {"ATGCGA","CAGTGC","TTATGTT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence(dnaExtraColumnAtRow2);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::toTable);

        assertEquals("DNA sequence must have same amount of rows and columns", exception.getMessage());
    }

    @Test
    void toTable_givenInvalidNitrogenousBase_mustThrowException() {
        String[] dnaWithXAtRow2 = {"ATGCGA","CAGTGC","TTXTGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence(dnaWithXAtRow2);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::toTable);

        assertEquals("DNA sequence must contain only valid nitrogenous bases", exception.getMessage());
    }

    @Test
    void toTable_givenInvalidCharacterAtNitrogenousBase_mustThrowException() {
        String[] dnaWithInvalidCharAtRow2 = {"ATGCGA","CAGTGC","TT,TGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence(dnaWithInvalidCharAtRow2);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::toTable);

        assertEquals("DNA sequence must contain only valid nitrogenous bases", exception.getMessage());
    }

    @Test
    void toTable_givenNumberAtNitrogenousBase_mustThrowException() {
        String[] dnaWithNumberAtRow2 = {"ATGCGA","CAGTGC","TT8TGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence(dnaWithNumberAtRow2);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::toTable);

        assertEquals("DNA sequence must contain only valid nitrogenous bases", exception.getMessage());
    }
}