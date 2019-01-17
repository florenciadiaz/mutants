package ar.test.meli.mutants.model;

import ar.test.meli.mutants.model.exception.InvalidSequenceException;

class Sequence {

    private final String[] dna;
    private Character[][] table;

    Sequence(String[] dna) {
        this.dna = dna;
    }

    Character[][] toTable() throws InvalidSequenceException {
        if (this.table == null) {
            int rowCount = this.dna.length;
            Character[][] table = new Character[rowCount][];
            for (int i = 0; i < rowCount; i++) {
                String row = this.dna[i];
                validateNitrogenousBase(row);
                int columnCount = row.length();
                validateDimensions(rowCount, columnCount);
                table[i] = getColumns(row, columnCount);
            }
            this.table = table;
        }
        return this.table;
    }

    public Character[][] transpose() throws InvalidSequenceException {
        Character[][] table = this.toTable();
        Character[][] temp = new Character[table.length][table.length];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                temp[j][i] = table[i][j];
            }
        }
        return temp;
    }

    public Character[][] rightToLeftDiagonal() throws InvalidSequenceException {
        Character[][] table = this.toTable();
        Character[][] temp = new Character[(2 * table.length) - 1][table.length];
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

    public Character[][] leftToRightDiagonal() throws InvalidSequenceException {
        Character[][] table = this.toTable();
        Character[][] temp = new Character[(2 * table.length) - 1][table.length];
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
