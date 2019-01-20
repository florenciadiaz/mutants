package ar.test.meli.mutants.model;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;

public class Detector {

    private final int minNBRepetitionToVerifyMutant;

    public Detector(int minNBRepetitionToVerifyMutant) {
        this.minNBRepetitionToVerifyMutant = minNBRepetitionToVerifyMutant;
    }

    public boolean isMutant(String[] dna) throws InvalidSequenceException {
        validateInputSequence(dna);
        Sequence sequence = new Sequence(dna);
        return checkRows(sequence)
                || checkColumns(sequence)
                || checkLeftToRightDiagonal(sequence)
                || checkRightToLeftDiagonal(sequence);
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

    private boolean checkRows(Sequence sequence) throws InvalidSequenceException {
        NitrogenousBase table = sequence.toTable();
        return table.isMutant(this.minNBRepetitionToVerifyMutant);
    }

    private boolean checkColumns(Sequence sequence) throws InvalidSequenceException {
        NitrogenousBase table = sequence.transpose();
        return table.isMutant(this.minNBRepetitionToVerifyMutant);
    }

    private boolean checkLeftToRightDiagonal(Sequence sequence) throws InvalidSequenceException {
        NitrogenousBase table = sequence.leftToRightDiagonal();
        return table.isMutant(this.minNBRepetitionToVerifyMutant);
    }

    private boolean checkRightToLeftDiagonal(Sequence sequence) throws InvalidSequenceException {
        NitrogenousBase table = sequence.rightToLeftDiagonal();
        return table.isMutant(this.minNBRepetitionToVerifyMutant);
    }
}
