package ar.test.meli.mutants.model;

import java.util.*;
import java.util.function.Consumer;

enum NitrogenousBaseType {
    A, T, C, G
}

class NitrogenousBase implements Iterable<List<NitrogenousBaseType>> {

    private final List<List<NitrogenousBaseType>> table;

    private static final String allNitrogenousBases = Arrays.stream(NitrogenousBaseType.values())
            .map(Enum::name)
            .reduce(String::concat)
            .orElse(null);

    private static final String validNitrogenousBasesRegex = String.format("[%s]+", allNitrogenousBases);

    NitrogenousBase(int size) {
        this.table = new ArrayList<>(size);
    }

    private static NitrogenousBase getMutantKeySequences(int minNBRepetitionToVerifyMutant) {
        NitrogenousBaseType[] values = NitrogenousBaseType.values();
        NitrogenousBase mutantKeySequences = new NitrogenousBase(values.length);

        for (NitrogenousBaseType nitrogenousBaseType : values) {
            List<NitrogenousBaseType> mutantKeySequence = new ArrayList<>(minNBRepetitionToVerifyMutant);
            for (int j = 0; j < minNBRepetitionToVerifyMutant; j++) {
                mutantKeySequence.add(nitrogenousBaseType);
            }
            mutantKeySequences.add(mutantKeySequence);
        }
        return mutantKeySequences;
    }

    public static boolean isValid(String sample) {
        return sample.matches(validNitrogenousBasesRegex);
    }

    public void add(List<NitrogenousBaseType> mutantKeySequence) {
        this.table.add(mutantKeySequence);
    }

    public int size() {
        return this.table.size();
    }

    public List<NitrogenousBaseType> get(int index) {
        return this.table.get(index);
    }

    @Override
    public Iterator<List<NitrogenousBaseType>> iterator() {
        return this.table.iterator();
    }

    @Override
    public void forEach(Consumer<? super List<NitrogenousBaseType>> action) {
        this.table.forEach(action);
    }

    @Override
    public Spliterator<List<NitrogenousBaseType>> spliterator() {
        return this.table.spliterator();
    }

    public boolean isMutant(int minNBRepetitionToVerifyMutant) {
        NitrogenousBase mutantKeySequences = getMutantKeySequences(minNBRepetitionToVerifyMutant);
        for (List<NitrogenousBaseType> row : this.table) {
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
