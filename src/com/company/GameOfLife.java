package com.company;

public class GameOfLife {
    private boolean[][] data;
    private boolean[] neighborhood = new boolean[8];
    private int generation = 0;

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public GameOfLife(boolean[][] data) {
        this.data = data;
    }

    public boolean[][] getData() {
        return data;
    }

    public void setData(boolean[][] data) {
        this.data = data;
    }

    public void setCellValue(int x, int y, boolean value) {
        data[x][y] = value;
    }

    public GameOfLife(int columnsNumber, int rowsNumber){
        this.data = new boolean[columnsNumber][rowsNumber];
    }

    public int getNumberOfRows() {
        return data.length;
    }

    public int getNumberOfColumns() {
        return data[0].length;
    }

    public boolean getCellValue(int x, int y) {
        if (x >= 0 && y >= 0 && x < data.length && y < data[0].length)
            return data[x][y];

        return false;
    }

    public synchronized boolean[][] generateNextStep(){
        boolean nextGenerationData[][] = new boolean[getNumberOfRows()][getNumberOfColumns()];

        for (int x = 0; x < getNumberOfRows(); x++) {
            for (int y = 0; y < getNumberOfColumns(); y++) {
                nextGenerationData[x][y] = willCellSurvive(
                        getCellValue(x, y),

                        getCellValue(x - 1, y - 1),
                        getCellValue(x, y - 1),
                        getCellValue(x + 1, y - 1),

                        getCellValue(x - 1, y),
                        getCellValue(x + 1, y ),

                        getCellValue(x - 1, y + 1),
                        getCellValue(x, y + 1),
                        getCellValue(x + 1, y + 1));
            }
        }

        return data = nextGenerationData;
    }

    public boolean willCellSurvive(boolean current,
                                   boolean neighbor1, boolean neighbor2, boolean neighbor3,
                                   boolean neighbor4, boolean neighbor5,
                                   boolean neighbor6, boolean neighbor7, boolean neighbor8) {
        neighborhood[0] = neighbor1;
        neighborhood[1] = neighbor2;
        neighborhood[2] = neighbor3;
        neighborhood[3] = neighbor4;
        neighborhood[4] = neighbor5;
        neighborhood[5] = neighbor6;
        neighborhood[6] = neighbor7;
        neighborhood[7] = neighbor8;

        int counter = 0;

        for (int i = 0; i < 8; i++)
            counter += neighborhood[i] ? 1 : 0;

        if (current)
            return counter == 2 || counter == 3;
        else
            return counter == 3;
    }
}
