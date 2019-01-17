package ar.test.meli.mutants.model;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;

class Sequence {

    private final String[] dna;
    private NitrogenousBaseType[][] table;

    Sequence(String[] dna) {
        this.dna = dna;
    }

    NitrogenousBaseType[][] toTable() throws InvalidSequenceException {
        if (this.table == null) {
            int rowCount = this.dna.length;
            NitrogenousBaseType[][] table = new NitrogenousBaseType[rowCount][];
            for (int i = 0; i < rowCount; i++) {
                String row = this.dna[i];
                validateNitrogenousBase(row);
                int columnCount = row.length();
                validateDimensions(rowCount, columnCount);
                table[i] = buildColumns(row, columnCount);
            }
            this.table = table;
        }
        return this.table;
    }

    public NitrogenousBaseType[][] transpose() throws InvalidSequenceException {
        NitrogenousBaseType[][] table = this.toTable();
        NitrogenousBaseType[][] temp = new NitrogenousBaseType[table.length][table.length];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                temp[j][i] = table[i][j];
            }
        }
        return temp;
    }

    public NitrogenousBaseType[][] rightToLeftDiagonal() throws InvalidSequenceException {
        NitrogenousBaseType[][] table = this.toTable();
        NitrogenousBaseType[][] temp = new NitrogenousBaseType[(2 * table.length) - 1][table.length];
        for (int row = 0; row < (2 * table.length) - 1; row++) {
            int column = 0;
            int originRow = (row < table.length - 1) ? row : table.length - 1;
            int originColumn = (row <= table.length - 1) ? 0 : row - table.length + 1;
            while (originRow >= 0 && originColumn <= table.length - 1) {
                temp[row][column] = table[originRow][originColumn];
                originRow--;
                originColumn++;
                column++;
            }
        }
        return temp;
    }

    public NitrogenousBaseType[][] leftToRightDiagonal() throws InvalidSequenceException {
        NitrogenousBaseType[][] table = this.toTable();
        NitrogenousBaseType[][] temp = new NitrogenousBaseType[(2 * table.length) - 1][table.length];
        for (int row = 0; row < (2 * table.length) - 1; row++) {
            int column = 0;
            int originRow = (row <= table.length - 1) ? 0 : row - table.length + 1;
            int originColumn = (row < table.length - 1) ? table.length - 1 - row : 0;
            while (originColumn <= table.length - 1 && originRow <= table.length - 1) {
                temp[row][column] = table[originRow][originColumn];
                originColumn++;
                originRow++;
                column++;
            }
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

    private NitrogenousBaseType[] buildColumns(String row, int columnCount) {
        NitrogenousBaseType[] columns = new NitrogenousBaseType[columnCount];
        for (int j = 0; j < columnCount; j++) {
            columns[j] = NitrogenousBaseType.valueOf(String.valueOf(row.charAt(j)));
        }
        return columns;
    }
}
