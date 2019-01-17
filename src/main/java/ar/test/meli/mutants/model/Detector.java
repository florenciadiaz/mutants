package ar.test.meli.mutants.model;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;

import java.util.Arrays;
import java.util.Collections;

public class Detector {

    boolean isMutant(String[] dna) throws InvalidSequenceException {
        validateInputSequence(dna);
        Sequence sequence = new Sequence(dna);
        Character[][] mutantKeySequences = NitrogenousBase.getMutantKeySequences();
        return checkRows(sequence, mutantKeySequences)
                || checkColumns(sequence, mutantKeySequences)
                || checkLeftToRightDiagonal(sequence, mutantKeySequences)
                || checkRightToLeftDiagonal(sequence, mutantKeySequences);
    }

    private void validateInputSequence(String[] dna) throws InvalidSequenceException {
        if (dna == null) {
            throw new InvalidSequenceException("DNA sequence cannot be null.");
        }
    }

    private boolean checkRows(Sequence sequence, Character[][] mutantKeySequences) throws InvalidSequenceException {
        Character[][] table = sequence.toTable();
        return findSequences(table, mutantKeySequences);
    }

    private boolean checkColumns(Sequence sequence, Character[][] mutantKeySequences) throws InvalidSequenceException {
        Character[][] table = sequence.transpose();
        return this.findSequences(table, mutantKeySequences);
    }

    private boolean checkLeftToRightDiagonal(Sequence sequence, Character[][] mutantKeySequences)
            throws InvalidSequenceException {
        Character[][] table = sequence.leftToRightDiagonal();
        return this.findSequences(table, mutantKeySequences);
    }

    private boolean checkRightToLeftDiagonal(Sequence sequence, Character[][] mutantKeySequences)
            throws InvalidSequenceException {
        Character[][] table = sequence.rightToLeftDiagonal();
        return this.findSequences(table, mutantKeySequences);
    }

    private boolean findSequences(Character[][] table, Character[][] mutantKeySequences) {
        for (Character[] row : table) {
            for (Character[] mutantKeySequence : mutantKeySequences) {
                if (contains(row, mutantKeySequence)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean contains(Character[] sample, Character[] valuesToFind) {
        return Collections.indexOfSubList(Arrays.asList(sample), Arrays.asList(valuesToFind)) >= 0;
    }
}
