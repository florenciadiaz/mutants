package ar.test.meli.mutants.model;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;

public class Detector {

    boolean isMutant(String[] dna) throws InvalidSequenceException {
        ValidateInputSequence(dna);
        Sequence sequence = new Sequence(dna);
        Character[][] table = sequence.toTable();
        return true;
    }

    private void ValidateInputSequence(String[] dna) throws InvalidSequenceException {
        if (dna == null)
        {
            throw new InvalidSequenceException("DNA sequence cannot be null.");
        }
    }
}
