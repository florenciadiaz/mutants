package ar.test.meli.mutants.model;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;

import java.util.Collections;
import java.util.List;

public class Detector {

    private final int minNBRepetitionToVerifyMutant;

    public Detector(int minNBRepetitionToVerifyMutant) {
        this.minNBRepetitionToVerifyMutant = minNBRepetitionToVerifyMutant;
    }

    public boolean isMutant(String[] dna) throws InvalidSequenceException {
        validateInputSequence(dna);
        Sequence sequence = new Sequence(dna);
        List<List<NitrogenousBaseType>> mutantKeySequences = NitrogenousBase.getMutantKeySequences(this.minNBRepetitionToVerifyMutant);
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
        if (dna.length < minNBRepetitionToVerifyMutant) {
            throw new InvalidSequenceException(String.format("DNA sequence must have at least %d " +
                    "nitrogenous bases per row", minNBRepetitionToVerifyMutant));
        }
    }

    private boolean checkRows(Sequence sequence, List<List<NitrogenousBaseType>> mutantKeySequences)
            throws InvalidSequenceException {
        List<List<NitrogenousBaseType>> table = sequence.toTable();
        return findSequences(table, mutantKeySequences);
    }

    private boolean checkColumns(Sequence sequence, List<List<NitrogenousBaseType>> mutantKeySequences)
            throws InvalidSequenceException {
        List<List<NitrogenousBaseType>> table = sequence.transpose();
        return this.findSequences(table, mutantKeySequences);
    }

    private boolean checkLeftToRightDiagonal(Sequence sequence, List<List<NitrogenousBaseType>> mutantKeySequences)
            throws InvalidSequenceException {
        List<List<NitrogenousBaseType>> table = sequence.leftToRightDiagonal();
        return this.findSequences(table, mutantKeySequences);
    }

    private boolean checkRightToLeftDiagonal(Sequence sequence, List<List<NitrogenousBaseType>> mutantKeySequences)
            throws InvalidSequenceException {
        List<List<NitrogenousBaseType>> table = sequence.rightToLeftDiagonal();
        return this.findSequences(table, mutantKeySequences);
    }

    private boolean findSequences(List<List<NitrogenousBaseType>> table,
                                  List<List<NitrogenousBaseType>> mutantKeySequences) {
        for (List<NitrogenousBaseType> row : table) {
            for (List<NitrogenousBaseType> mutantKeySequence : mutantKeySequences) {
                if (contains(row, mutantKeySequence)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean contains(List<NitrogenousBaseType> sample, List<NitrogenousBaseType> valuesToFind) {
        return Collections.indexOfSubList(sample, valuesToFind) >= 0;
    }
}
