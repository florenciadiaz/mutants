package ar.test.meli.mutants.model;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;

import java.util.ArrayList;
import java.util.List;

class Sequence {

    private final String[] dna;
    private final int minNBRepetitionToVerifyMutant;

    private List<NitrogenousBase> table;

    private Sequence(String[] dna, int minNBRepetitionToVerifyMutant) {
        this.dna = dna;
        this.minNBRepetitionToVerifyMutant = minNBRepetitionToVerifyMutant;
    }

    public static Sequence of(String[] dna, int minNBRepetitionToVerifyMutant) throws InvalidSequenceException {
        validateInputSequence(dna, minNBRepetitionToVerifyMutant);
        return new Sequence(dna, minNBRepetitionToVerifyMutant);
    }

    private static void validateInputSequence(String[] dna, int minNBRepetitionToVerifyMutant) throws InvalidSequenceException {
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

    public boolean hasMutantDNA() throws InvalidSequenceException {
        return this.checkRowsAndBuildNitrogenousBaseIfNotMutant()
                || this.checkColumns()
                || this.checkLeftToRightDiagonal()
                || this.checkRightToLeftDiagonal();
    }

    private boolean checkRowsAndBuildNitrogenousBaseIfNotMutant()
            throws InvalidSequenceException {
        if (this.table == null) {
            int rowCount = this.dna.length;
            this.table = new ArrayList<>(rowCount);
            for (String row : this.dna) {
                validateNitrogenousBase(row);
                int columnCount = row.length();
                validateDimensions(rowCount, columnCount);
                NitrogenousBase nitrogenousBaseRow = NitrogenousBase.of(row, columnCount,
                        this.minNBRepetitionToVerifyMutant);
                if (nitrogenousBaseRow.isMutant()) {
                    return true;
                }
                this.table.add(nitrogenousBaseRow);
            }
        }
        return false;
    }

    private void validateNitrogenousBase(String row) throws InvalidSequenceException {
        if (!NitrogenousBase.isValid(row)) {
            throw new InvalidSequenceException("DNA sequence must contain only valid nitrogenous bases");
        }
    }

    private void validateDimensions(int rowCount, int columnCount) throws InvalidSequenceException {
        if (columnCount != rowCount){
            throw new InvalidSequenceException("DNA sequence must have same amount of rows and columns");
        }
    }

    private boolean checkColumns() {
        int tableSize = this.table.size();
        for (int columnIndex = 0; columnIndex < tableSize; columnIndex++) {
            int repetitionCount = 1;
            for (int rowIndex = 0; rowIndex < tableSize; rowIndex++) {
                NitrogenousBaseType actualValue = table.get(rowIndex).get(columnIndex);
                if (rowIndex + 1 < tableSize) {
                    NitrogenousBaseType nextValue = table.get(rowIndex + 1).get(columnIndex);
                    repetitionCount = (actualValue == nextValue) ? (repetitionCount + 1) : 1;
                    if (repetitionCount == this.minNBRepetitionToVerifyMutant) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkRightToLeftDiagonal() {
        int tableSize = table.size();
        int diagonalSize = (2 * tableSize) - 1;
        for (int i = 0; i < diagonalSize; i++) {
            int rowIndex = (i < tableSize - 1) ? i : (tableSize - 1);
            int columnIndex = (i <= tableSize - 1) ? 0 : (i - tableSize + 1);
            int repetitionCount = 1;
            while (rowIndex >= 0 && columnIndex <= tableSize - 1) {
                NitrogenousBaseType actualValue = this.table.get(rowIndex).get(columnIndex);
                rowIndex--;
                columnIndex++;
                if (rowIndex >= 0 && columnIndex <= tableSize - 1) {
                    NitrogenousBaseType nextValue = this.table.get(rowIndex).get(columnIndex);
                    repetitionCount = (actualValue == nextValue) ? (repetitionCount + 1) : 1;
                    if (repetitionCount == this.minNBRepetitionToVerifyMutant) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkLeftToRightDiagonal() {
        int tableSize = this.table.size();
        int diagonalSize = (2 * tableSize) - 1;
        for (int i = 0; i < diagonalSize; i++) {
            int rowIndex = (i <= tableSize - 1) ? 0 : (i - tableSize + 1);
            int columnIndex = (i < tableSize - 1) ? (tableSize - 1 - i) : 0;
            int repetitionCount = 1;
            while (columnIndex <= tableSize - 1 && rowIndex <= tableSize - 1) {
                NitrogenousBaseType actualValue = this.table.get(rowIndex).get(columnIndex);
                columnIndex++;
                rowIndex++;
                if (columnIndex <= tableSize - 1 && rowIndex <= tableSize - 1) {
                    NitrogenousBaseType nextValue = this.table.get(rowIndex).get(columnIndex);
                    repetitionCount = (actualValue == nextValue) ? (repetitionCount + 1) : 1;
                    if (repetitionCount == this.minNBRepetitionToVerifyMutant) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
