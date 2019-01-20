package ar.test.meli.mutants.model;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;

import java.util.ArrayList;
import java.util.List;

class Sequence {

    private final String[] dna;
    private List<List<NitrogenousBaseType>> table;

    Sequence(String[] dna) {
        this.dna = dna;
    }

    public List<List<NitrogenousBaseType>> toTable() throws InvalidSequenceException {
        if (this.table == null) {
            int rowCount = this.dna.length;
            List<List<NitrogenousBaseType>> table = new ArrayList<>(rowCount);
            for (String row : this.dna) {
                validateNitrogenousBase(row);
                int columnCount = row.length();
                validateDimensions(rowCount, columnCount);
                table.add(buildColumns(row, columnCount));
            }
            this.table = table;
        }
        return this.table;
    }

    public List<List<NitrogenousBaseType>> transpose() throws InvalidSequenceException {
        List<List<NitrogenousBaseType>> table = this.toTable();
        int tableSize = table.size();
        List<List<NitrogenousBaseType>> transposed = new ArrayList<>(tableSize);

        for (int i = 0; i < tableSize; i++) {
            List<NitrogenousBaseType> column = new ArrayList<>();
            for (List<NitrogenousBaseType> row : table) {
                column.add(row.get(i));
            }
            transposed.add(column);
        }
        return transposed;
    }

    public List<List<NitrogenousBaseType>> rightToLeftDiagonal() throws InvalidSequenceException {
        List<List<NitrogenousBaseType>> table = this.toTable();
        int tableSize = table.size();
        int diagonalSize = (2 * tableSize) - 1;
        List<List<NitrogenousBaseType>> temp = new ArrayList<>(diagonalSize);

        for (int i = 0; i < diagonalSize; i++) {
            List<NitrogenousBaseType> row = new ArrayList<>();
            int originRow = (i < tableSize - 1) ? i : (tableSize - 1);
            int originColumn = (i <= tableSize - 1) ? 0 : (i - tableSize + 1);
            while (originRow >= 0 && originColumn <= tableSize - 1) {
                row.add(table.get(originRow).get(originColumn));
                originRow--;
                originColumn++;
            }
            temp.add(row);
        }
        return temp;
    }

    public List<List<NitrogenousBaseType>> leftToRightDiagonal() throws InvalidSequenceException {
        List<List<NitrogenousBaseType>> table = this.toTable();
        int tableSize = table.size();
        int diagonalSize = (2 * tableSize) - 1;
        List<List<NitrogenousBaseType>> temp = new ArrayList<>(diagonalSize);

        for (int i = 0; i < diagonalSize; i++) {
            List<NitrogenousBaseType> row = new ArrayList<>();
            int originRow = (i <= tableSize - 1) ? 0 : (i - tableSize + 1);
            int originColumn = (i < tableSize - 1) ? (tableSize - 1 - i) : 0;
            while (originColumn <= tableSize - 1 && originRow <= tableSize - 1) {
                row.add(table.get(originRow).get(originColumn));
                originColumn++;
                originRow++;
            }
            temp.add(row);
        }
        return temp;
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

    private ArrayList<NitrogenousBaseType> buildColumns(String row, int columnCount) {
        ArrayList<NitrogenousBaseType> columns = new ArrayList<>(columnCount);
        for (int j = 0; j < columnCount; j++) {
            columns.add(NitrogenousBaseType.valueOf(String.valueOf(row.charAt(j))));
        }
        return columns;
    }
}
