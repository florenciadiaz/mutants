package ar.test.meli.mutants.model;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SequenceTest {

    @Test
    void toTableWithValidDNAMustNotBeNull() throws InvalidSequenceException {
        String[] dna6x6 = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence(dna6x6);

        Character[][] table = sequence.toTable();

        assertNotNull(table);
    }

    @Test
    void toTableWithLessRowsThanColumnsMustThrowException()  {
        String[] dna5x6 = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA"};
        Sequence sequence = new Sequence(dna5x6);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::toTable);

        assertEquals("DNA sequence must have same ammount of rows and columms", exception.getMessage());
    }

    @Test
    void toTableWithLessColumnsThanRowsMustThrowException()  {
        String[] dna6x5 = {"ATGCG","CAGTG","TTATG","AGAAG","CCCCT","TCACT"};
        Sequence sequence = new Sequence(dna6x5);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::toTable);

        assertEquals("DNA sequence must have same ammount of rows and columms", exception.getMessage());
    }

    @Test
    void toTableWithMissingColumnMustThrowException()  {
        String[] dnaMissingColumnAtRow3 = {"ATGCGA","CAGTGC","TTATG","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence(dnaMissingColumnAtRow3);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::toTable);

        assertEquals("DNA sequence must have same ammount of rows and columms", exception.getMessage());
    }

    @Test
    void toTableWithExtraColumnMustThrowException()  {
        String[] dnaExtraColumnAtRow3 = {"ATGCGA","CAGTGC","TTATGTT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence(dnaExtraColumnAtRow3);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::toTable);

        assertEquals("DNA sequence must have same ammount of rows and columms", exception.getMessage());
    }

    @Test
    void toTableWithInvalidNitrogenousBaseMustThrowException() {
        String[] dnaWithXAtRow3 = {"ATGCGA","CAGTGC","TTXTGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence(dnaWithXAtRow3);

        Throwable exception = assertThrows(InvalidSequenceException.class, sequence::toTable);

        assertEquals("DNA sequence must contain only valid nitrogenous bases", exception.getMessage());
    }
}