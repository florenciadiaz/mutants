package ar.test.meli.mutants.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static List<List<NitrogenousBaseType>> getMutantKeySequences() {
        List<NitrogenousBaseType> values = Arrays.asList(NitrogenousBaseType.values());
        List<List<NitrogenousBaseType>> mutantKeySequences = new ArrayList<>(values.size());

        for (NitrogenousBaseType nitrogenousBaseType:
             values) {
            List<NitrogenousBaseType> mutantKeySequence = new ArrayList<>(MINIMUM_NB_REPETITION_TO_CHECK_MUTANT);
            for (int j = 0; j < MINIMUM_NB_REPETITION_TO_CHECK_MUTANT; j++) {
                mutantKeySequence.add(nitrogenousBaseType);
            }
            mutantKeySequences.add(mutantKeySequence);
        }
        return mutantKeySequences;
    }

    public static boolean isValid(String sample) {
        return sample.matches(validNitrogenousBasesRegex);
    }
}
