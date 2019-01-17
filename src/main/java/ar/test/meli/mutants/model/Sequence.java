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

    public Character[][] leftToRightDiagonal() throws InvalidSequenceException {
        Character[][] table = this.toTable();
        Character[][] temp = new Character[table.length + table.length - 1][table.length];
        for (int i = 0; i < table.length - 1; i++) {
            int j = 0;
            int t = i;
            while (t >= 0) {
                temp[i][j] = table[t][j];
                t--;
                j++;
            }
        }
        for (int j = 0; j < table.length; j++) {
            int i = table.length - 1;
            int t = j;
            int k = table.length - 1;
            int l = 0;
            while (t < table.length) {
                temp[k + j][l] = table[i][t];
                t++;
                i--;
                l++;
            }
        }
        return temp;
    }

    public Character[][] rightToLeftDiagonal() throws InvalidSequenceException {
        Character[][] table = this.toTable();
        Character[][] temp = new Character[table.length + table.length - 1][table.length];

        for (int i = 0; i < table.length; i++) {
            int j = table.length - 1;
            int t = i;
            int k = 0;
            while (t >= 0) {
                temp[i][k] = table[t][j];
                t--;
                j--;
                k++;
            }
        }
        for (int j = 0; j < table.length; j++) {
            int i = table.length - 1;
            int t = i - j;
            int k = table.length - 1;
            int l = 0;
            while (t >= 0) {
                temp[k + j][l] = table[i][t];
                t--;
                i--;
                l++;
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
