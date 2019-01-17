package ar.test.meli.mutants.model;

import java.util.Arrays;

enum NitrogenousBaseType {
    A, T, C, G
}

class NitrogenousBase {

    //TODO get from configuration
    public static final int MINIMUM_NB_REPETITION_TO_CHECK_MUTANT = 4;

    private static final String allNitrogenousBases = Arrays.stream(NitrogenousBaseType.values())
            .map(Enum::name)
            .reduce(String::concat)
            .orElse(null);

    private static final String validNitrogenousBasesRegex = String.format("[%s]+", allNitrogenousBases);

    public static NitrogenousBaseType[][] getMutantKeySequences() {
        NitrogenousBaseType[][] mutantKeySequences = new NitrogenousBaseType[MINIMUM_NB_REPETITION_TO_CHECK_MUTANT][];
        NitrogenousBaseType[] values = NitrogenousBaseType.values();
        for (int i = 0, valuesLength = values.length; i < valuesLength; i++) {
            NitrogenousBaseType nitrogenousBaseType = values[i];
            NitrogenousBaseType[] mutantKeySequence = new NitrogenousBaseType[NitrogenousBaseType.values().length];
            for (int j = 0; j < MINIMUM_NB_REPETITION_TO_CHECK_MUTANT; j++) {
                mutantKeySequence[j] = nitrogenousBaseType;
            }
            mutantKeySequences[i] = mutantKeySequence;
        }
        return mutantKeySequences;
    }

    public static boolean isValid(String sample) {
        return sample.matches(validNitrogenousBasesRegex);
    }
}
