package ar.test.meli.mutants.model;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DetectorTests {

    @Test
    void isMutantWithHumanDNAMustBeFalse() throws InvalidSequenceException {
        Detector detector = new Detector();

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
    void isMutantWithMutantSequenceAtRowMustBeTrue() throws InvalidSequenceException {
        Detector detector = new Detector();

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
    void isMutantWithMutantSequenceAtColumnMustBeTrue() throws InvalidSequenceException {
        Detector detector = new Detector();

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
    void isMutantWithMutantSequenceAtLeftToRightDiagonalMustBeFalse() throws InvalidSequenceException {
        Detector detector = new Detector();

        String[] dnaWithMutantAtDiagonal5 = {"ATGCGC","CAGTAC","TTAAGT","AGAATG","CACCTA","TCACTG"};
        boolean actual = detector.isMutant(dnaWithMutantAtDiagonal5);

//       (A)T G C G C
//        C(A)G T A C
//        T T(A)A G T
//        A G A(A)T G
//        C A C C T A
//        T C A C T G

        assertTrue(actual);
    }

    @Test
    void isMutantWithMutantSequenceAtRightToLeftDiagonalMustBeFalse() throws InvalidSequenceException {
        Detector detector = new Detector();

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
    void isMutantWithMutantSequenceAtRightToLeftUpperDiagonalMustBeFalse() throws InvalidSequenceException {
        Detector detector = new Detector();

        String[] dnaWithMutantAtDiagonal4 = {"ATGCGA","CTTTGC","TGTTGT","AGAATG","CACCTA","TCACTG"};
        boolean actual = detector.isMutant(dnaWithMutantAtDiagonal4);

//        A(T)G C G A
//        C T(T)T G C
//        T G T(T)G T
//        A G A A(T)G
//        C A C C T A
//        T C A C T G

        assertTrue(actual);
    }

    @Test
    void isMutantWithMutantSequenceAtRightToLeftLowerDiagonalMustBeFalse() throws InvalidSequenceException {
        Detector detector = new Detector();

        String[] dnaWithMutantAtDiagonal7 = {"ATGCGA","CTATGC","CGTTGT","ACAATG","CACCTA","TCACTG"};
        boolean actual = detector.isMutant(dnaWithMutantAtDiagonal7);

//        "A T G C G A"
//        "C T A T G C"
//       "(C)G T T G T"
//        "A(C)A A T G"
//        "C A(C)C T A"
//        "T C A(C)T G"

        assertTrue(actual);
    }
}