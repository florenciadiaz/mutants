package ar.test.meli.mutants.model;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;

/**
 * Detects whether a given DNA belongs to a mutant or not.
 */
public class Detector {

    private final int minNBRepetitionToVerifyMutant;

    public Detector(int minNBRepetitionToVerifyMutant) {
        this.minNBRepetitionToVerifyMutant = minNBRepetitionToVerifyMutant;
    }

    /**
     * Returns <tt>true</tt> if a mutant sequence on the given DNA is detected.
     *
     * @param dna contains an NxN sequence of DNA
     * @return <tt>true</tt> if a mutant sequence on the given DNA is detected
     * @throws InvalidSequenceException if the given sequence is not valid
     */
    public boolean isMutant(String[] dna) throws InvalidSequenceException {
        Sequence sequence = Sequence.of(dna, this.minNBRepetitionToVerifyMutant);
        return sequence.hasMutantDNA();
    }
}
