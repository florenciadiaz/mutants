package ar.test.meli.mutants.model;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DetectorTests {

    @Test
    void isMutant_givenNullDNA_mustThrowException() {
        Detector detector = new Detector(4);

        Throwable exception = assertThrows(InvalidSequenceException.class, () -> detector.isMutant(null));

        assertEquals("DNA sequence cannot be null", exception.getMessage());
    }

    @Test
    void isMutant_givenEmptyDNA_mustThrowException() {
        Detector detector = new Detector(4);
        String[] dna = new String[]{};

        Throwable exception = assertThrows(InvalidSequenceException.class, () -> detector.isMutant(dna));

        assertEquals("DNA sequence cannot be empty", exception.getMessage());
    }

    @Test
    void isMutant__givenInsufficientDNASample_mustThrowException() {
        Detector detector = new Detector(4);
        String[] dna = {"ATC", "GAT", "AAC"};

        Throwable exception = assertThrows(InvalidSequenceException.class, () -> detector.isMutant(dna));

        assertEquals("DNA sequence must have at least 4 nitrogenous bases per row", exception.getMessage());
    }

    @Test
    void isMutant__givenHumanDNA_mustBeFalse() throws InvalidSequenceException {
        Detector detector = new Detector(4);
        String[] dna = {"ATGCGA","CAGTGC","TTCTGT","AGAATG","CACCTA","TCACTG"};

        boolean actual = detector.isMutant(dna);

//        A T G C G A
//        C A G T G C
//        T T C T G T
//        A G A A T G
//        C A C C T A
//        T C A C T G

        assertFalse(actual);
    }

    @Test
    void isMutant__givenMutantSequenceAtRow_mustBeTrue() throws InvalidSequenceException {
        Detector detector = new Detector(4);
        String[] dnaWithMutantAtRow4 = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};

        boolean actual = detector.isMutant(dnaWithMutantAtRow4);

//        A T G C G A
//        C A G T G C
//        T T A T G T
//        A G A A G G
//       (C-C-C-C)T A
//        T C A C T G

        assertTrue(actual);
    }

    @Test
    void isMutant__givenMutantSequenceAtColumn_mustBeTrue() throws InvalidSequenceException {
        Detector detector = new Detector(4);
        String[] dnaWithMutantAtCol2 = {"ATTCGA","CACTGC","TTGTGT","AGGAGG","CAGCTA","TCGCTG"};

        boolean actual = detector.isMutant(dnaWithMutantAtCol2);

//        A T T C G A
//        C A C T G C
//        T T(G)T G T
//        A G(G)A G G
//        C A(G)C T A
//        T C(G)C T G

        assertTrue(actual);
    }

    @Test
    void isMutant_givenMutantSequenceAtLeftToRightDiagonal_mustBeTrue() throws InvalidSequenceException {
        Detector detector = new Detector(4);
        String[] dnaWithMutantAtDiagonal5 = {"ATGCGA","CTGTGC","TGTTGT","AGATTG","CACCTA","TCACTG"};

        boolean actual = detector.isMutant(dnaWithMutantAtDiagonal5);

//        A T G C G A
//        C(T)G T G C
//        T G(T)T G T
//        A G A(T)T G
//        C A C C(T)A
//        T C A C T G

        assertTrue(actual);
    }

    @Test
    void isMutant_givenMutantSequenceAtLeftToRightUpperDiagonal_mustBeTrue() throws InvalidSequenceException {
        Detector detector = new Detector(4);
        String[] dnaWithMutantAtDiagonal4 = {"ATGCGC","CCGTAC","TTAGGT","AGAAGG","CACCTG","TCACTG"};

        boolean actual = detector.isMutant(dnaWithMutantAtDiagonal4);

//        A T G C G C
//        C C(G)T A C
//        T T A(G)G T
//        A G A A(G)G
//        C A C C T(G)
//        T C A C T G

        assertTrue(actual);
    }

    @Test
    void isMutant_givenMutantSequenceAtLeftToRightLowerDiagonal_mustBeTrue() throws InvalidSequenceException {
        Detector detector = new Detector(4);
        String[] dnaWithMutantAtDiagonal7 = {"ATGCGC","CCGTAC","CTACGT","ACAATG","CACCTA","TCACTG"};

        boolean actual = detector.isMutant(dnaWithMutantAtDiagonal7);

//        A T G C G C
//        C C G T A C
//       (C)T A C G T
//        A(C)A A T G
//        C A(C)C T A
//        T C A(C)T G

        assertTrue(actual);
    }

    @Test
    void isMutant_givenMutantSequenceAtRightToLeftDiagonal_mustBeTrue() throws InvalidSequenceException {
        Detector detector = new Detector(4);
        String[] dnaWithMutantAtDiagonal5 = {"ATGCGA","CAGTGC","TTCGGT","AGGATG","CGCCTA","TCACTG"};

        boolean actual = detector.isMutant(dnaWithMutantAtDiagonal5);

//        A T G C G A
//        C A G T(G)C
//        T T C(G)G T
//        A G(G)A T G
//        C(G)C C T A
//        T C A C T G

        assertTrue(actual);
    }

    @Test
    void isMutant_givenMutantSequenceAtRightToLeftUpperDiagonal_mustBeTrue() throws InvalidSequenceException {
        Detector detector = new Detector(4);
        String[] dnaWithMutantAtDiagonal4 = {"ATGCGA","CAGCGC","TTCTGT","ACAATG","CACCTA","TCACTG"};

        boolean actual = detector.isMutant(dnaWithMutantAtDiagonal4);

//        A T G C G A
//        C A G(C)G C
//        T T(C)T G T
//        A(C)A A T G
//       (C)A C C T A
//        T C A C T G

        assertTrue(actual);
    }

    @Test
    void isMutant_givenMutantSequenceAtRightToLeftLowerDiagonal_mustBeTrue() throws InvalidSequenceException {
        Detector detector = new Detector(4);
        String[] dnaWithMutantAtDiagonal7 = {"ATGCGA","CAGTGC","TTCTGT","AGAATG","CACTTA","TCTCTG"};

        boolean actual = detector.isMutant(dnaWithMutantAtDiagonal7);

//        A T G C G A
//        C A G T G C
//        T T C T G(T)
//        A G A A(T)G
//        C A C(T)T A
//        T C(T)C T G

        assertTrue(actual);
    }

    @Test
    void isMutant_givenHumanDNALarge_mustBeFalse() throws InvalidSequenceException {
        Detector detector = new Detector(4);
        String[] dnaWithMutantAtDiagonal7 = {"ATGCGAATGCGAA","CAGTGCTTCTGTT","AGAATGCACTTAC","TCTCTGCAGTGCC",
                "TTCCGTAGAATGA","CACTTATCTCTGT","TTCTGGAGAATGA","CAGTGCATCTGTT","AGAATGCACTTAC","ATGCGAATGCGAA",
                "TCACTGCAGTGCC","CACTTATCTCTGT","AGAATGCACTTAC"};

        boolean actual = detector.isMutant(dnaWithMutantAtDiagonal7);

//        A T G C G A A T G C G A A
//        C A G T G C T T C T G T T
//        A G A A T G C A C T T A C
//        T C T C T G C A G T G C C
//        T T C C G T A G A A T G A
//        C A C T T A T C T C T G T
//        T T C T G G A G A A T G A
//        C A G T G C A T C T G T T
//        A G A A T G C A C T T A C
//        A T G C G A A T G C G A A
//        T C A C T G C A G T G C C
//        C A C T T A T C T C T G T
//        A G A A T G C A C T T A C

        assertFalse(actual);
    }

    @Test
    void isMutant_givenMutantSequenceAtRightToLeftLowerDiagonalLarge_mustBeTrue() throws InvalidSequenceException {
        Detector detector = new Detector(4);
        String[] dnaWithMutantAtDiagonal7 = {"ATGCGAATGCGAA","CAGTGCTTCTGTT","AGAATGCACTTAC","TCTCTGCAGTGCC",
                "TTCTGTAGAATGA","CACTTATCTCTGT","TTCTGTAGAATGA","CAGTGCATCTGTT","AGAATGCACTTAC","ATGCGAATGCGAA",
                "TCTCTGCAGTGCC","CACTTATCTCTGT","AGAATGCACTTAC"};

        boolean actual = detector.isMutant(dnaWithMutantAtDiagonal7);

//        A T G C G A A T G C G A A
//        C A G T G C T T C T G T T
//        A G A A T G C A C T T A C
//        T C(T)C T G C A G T G C C
//        T T C(T)G T A G A A T G A
//        C A C T(T)A T C T C T G T
//        T T C T G(T)A G A A T G A
//        C A G T G C A T C T G T T
//        A G A A T G C A C T T A C
//        A T G C G A A T G C G A A
//        T C T C T G C A G T G C C
//        C A C T T A T C T C T G T
//        A G A A T G C A C T T A C

        assertTrue(actual);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/samples/unit/large-sequence.csv")
    void isMutant_givenMutantDNAExtraLarge_mustBeTrue(ArgumentsAccessor arguments) throws InvalidSequenceException {
        Detector detector = new Detector(4);
        Object[] argumentsArray = arguments.toArray();
        String[] largeDna65x65 = (String[]) Arrays.asList(argumentsArray).toArray();

        boolean actual = detector.isMutant(largeDna65x65);

        assertTrue(actual);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/samples/unit/large-sequence.csv")
    void isMutant_givenHumanDNAExtraLarge_mustBeFalse(ArgumentsAccessor arguments) throws InvalidSequenceException {
        Detector detector = new Detector(12);
        Object[] argumentsArray = arguments.toArray();
        String[] largeDna65x65 = (String[]) Arrays.asList(argumentsArray).toArray();

        boolean actual = detector.isMutant(largeDna65x65);

        assertFalse(actual);
    }
}