package ar.test.meli.mutants.model;

import java.util.Arrays;

class NitrogenousBase {

    enum NitrogenousBaseType {
        A, T, C, G
    }

    //TODO get from configuration
    public static int MinRepetitionForMutant = 4;

    private static String allNitrogenousBases = Arrays.stream(NitrogenousBaseType.values())
            .map(Enum::name)
            .reduce(String::concat)
            .orElse(null);

    private static String validNitrogenousBasesRegex = String.format("[%s]+", allNitrogenousBases);

    public static Character[][] getMutantKeySequences() {
        Character[][] mutantKeySequences = new Character[MinRepetitionForMutant][];
        NitrogenousBaseType[] values = NitrogenousBaseType.values();
        for (int i = 0, valuesLength = values.length; i < valuesLength; i++) {
            NitrogenousBaseType nitrogenousBaseType = values[i];
            Character[] mutantKeySequence = new Character[NitrogenousBaseType.values().length];
            for (int j = 0; j < 4; j++) {
                mutantKeySequence[j] = nitrogenousBaseType.name().charAt(0);
            }
            mutantKeySequences[i] = mutantKeySequence;
        }
        return mutantKeySequences;
    }

    public static boolean isValid(String sample) {
        return sample.matches(validNitrogenousBasesRegex);
    }
}
