package ar.test.meli.mutants.model;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;

import java.util.Arrays;

class Sequence {

    private static String allNitrogenousBases = Arrays.stream(NitrogenousBase.values())
            .map(Enum::name)
            .reduce(String::concat)
            .orElse(null);

    private static String validNitrogenousBasesRegex = String.format("[%s]+", allNitrogenousBases);

    private final String[] dna;

    Sequence(String[] dna) {
        this.dna = dna;
    }

    Character[][] toTable() throws InvalidSequenceException {
        int rowCount = this.dna.length;
        Character[][] table =  new Character[rowCount][];
        for (int i = 0; i < rowCount; i++) {
            String row = this.dna[i];
            validateNitrogenousBase(row);
            int columnCount = row.length();
            validateDimensions(rowCount, columnCount);
            table[i] = getColumns(row, columnCount);
        }
        return table;
    }

    private void validateNitrogenousBase(String row) throws InvalidSequenceException {
        if (!row.matches(validNitrogenousBasesRegex)) {
            throw new InvalidSequenceException("DNA sequence must contain only valid nitrogenous bases");
        }
    }

    private void validateDimensions(int rowCount, int columnCount) throws InvalidSequenceException {
        if (columnCount != rowCount){
            throw new InvalidSequenceException("DNA sequence must have same ammount of rows and columms");
        }
    }

    private Character[] getColumns(String row, int columnCount) {
        Character[] columns = new Character[columnCount];
        for (int j = 0; j < columnCount; j++) {
            columns[j] = row.charAt(j);
        }
        return columns;
    }
}
