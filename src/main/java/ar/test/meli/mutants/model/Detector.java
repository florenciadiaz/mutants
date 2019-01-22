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
        return sequence.hasMutantDNA(this.minNBRepetitionToVerifyMutant);
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
}
