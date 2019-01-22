package ar.test.meli.mutants.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

enum NitrogenousBaseType {
    A, T, C, G;

    public static NitrogenousBaseType of(char value) {
        return valueOf(String.valueOf(value));
    }
}

class NitrogenousBase {

    private final List<NitrogenousBaseType> row;
    private boolean isMutant;

    private static final String allNitrogenousBases = Arrays.stream(NitrogenousBaseType.values())
            .map(Enum::name)
            .reduce(String::concat)
            .orElse(null);

    private static final String validNitrogenousBasesRegex = String.format("[%s]+", allNitrogenousBases);

    private NitrogenousBase(int size) {
        this.row = new ArrayList<>(size);
    }

    public static boolean isValid(String sample) {
        return sample.matches(validNitrogenousBasesRegex);
    }

    public static NitrogenousBase of(String row, int columnCount, int minNBRepetitionToVerifyMutant) {
        NitrogenousBase nitrogenousBase = new NitrogenousBase(columnCount);
        int repetitionCount = 1;
        for (int i = 0; i < columnCount; i++) {
            NitrogenousBaseType actualValue = NitrogenousBaseType.of(row.charAt(i));
            if (i + 1 < columnCount) {
                NitrogenousBaseType nextValue = NitrogenousBaseType.of(row.charAt(i + 1));
                repetitionCount = (actualValue == nextValue) ? (repetitionCount + 1) : 1;
                if (repetitionCount == minNBRepetitionToVerifyMutant) {
                    nitrogenousBase.isMutant = true;
                }
            }
            nitrogenousBase.add(actualValue);
        }
        return nitrogenousBase;
    }

    public boolean isMutant() {
        return this.isMutant;
    }

    public NitrogenousBaseType get(int index) {
        return this.row.get(index);
    }

    private void add(NitrogenousBaseType value) {
        this.row.add(value);
    }
}
