package ar.test.meli.mutants.model;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;

import java.util.Arrays;
import java.util.Collections;

public class Detector {

    boolean isMutant(String[] dna) throws InvalidSequenceException {
        validateInputSequence(dna);
        Sequence sequence = new Sequence(dna);
        NitrogenousBaseType[][] mutantKeySequences = NitrogenousBase.getMutantKeySequences();
        return checkRows(sequence, mutantKeySequences)
                || checkColumns(sequence, mutantKeySequences)
                || checkLeftToRightDiagonal(sequence, mutantKeySequences)
                || checkRightToLeftDiagonal(sequence, mutantKeySequences);
    }

    private void validateInputSequence(String[] dna) throws InvalidSequenceException {
        if (dna == null) {
            throw new InvalidSequenceException("DNA sequence cannot be null");
        }
        if (dna.length == 0) {
            throw new InvalidSequenceException("DNA sequence cannot be empty");
        }
        if (dna.length < NitrogenousBase.MINIMUM_NB_REPETITION_TO_CHECK_MUTANT) {
            throw new InvalidSequenceException(String.format("DNA sequence must have at least %d " +
                    "nitrogenous bases per row", NitrogenousBase.MINIMUM_NB_REPETITION_TO_CHECK_MUTANT));
        }
    }

    private boolean checkRows(Sequence sequence, NitrogenousBaseType[][] mutantKeySequences) throws InvalidSequenceException {
        NitrogenousBaseType[][] table = sequence.toTable();
        return findSequences(table, mutantKeySequences);
    }

    private boolean checkColumns(Sequence sequence, NitrogenousBaseType[][] mutantKeySequences) throws InvalidSequenceException {
        NitrogenousBaseType[][] table = sequence.transpose();
        return this.findSequences(table, mutantKeySequences);
    }

    private boolean checkLeftToRightDiagonal(Sequence sequence, NitrogenousBaseType[][] mutantKeySequences)
            throws InvalidSequenceException {
        NitrogenousBaseType[][] table = sequence.leftToRightDiagonal();
        return this.findSequences(table, mutantKeySequences);
    }

    private boolean checkRightToLeftDiagonal(Sequence sequence, NitrogenousBaseType[][] mutantKeySequences)
            throws InvalidSequenceException {
        NitrogenousBaseType[][] table = sequence.rightToLeftDiagonal();
        return this.findSequences(table, mutantKeySequences);
    }

    private boolean findSequences(NitrogenousBaseType[][] table, NitrogenousBaseType[][] mutantKeySequences) {
        for (NitrogenousBaseType[] row : table) {
            for (NitrogenousBaseType[] mutantKeySequence : mutantKeySequences) {
                if (contains(row, mutantKeySequence)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean contains(NitrogenousBaseType[] sample, NitrogenousBaseType[] valuesToFind) {
        return Collections.indexOfSubList(Arrays.asList(sample), Arrays.asList(valuesToFind)) >= 0;
    }
}
