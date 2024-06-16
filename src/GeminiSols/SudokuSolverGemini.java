package GeminiSols;

import exceptions.SudokuNotSolvableException;
import tasks.SudokuSolverBacktracking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SudokuSolverGemini implements SudokuSolverBacktracking {

    private static final int GRID_SIZE = 9;

    @Override
    public int[][] readSudokuFromCSV(String filepath) throws IOException {
        int[][] sudokuGrid = new int[GRID_SIZE][GRID_SIZE];

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length != GRID_SIZE) {
                    throw new IOException("Invalid CSV format. Expected " + GRID_SIZE + " values per row.");
                }

                for (int col = 0; col < GRID_SIZE; col++) {
                    sudokuGrid[row][col] = Integer.parseInt(values[col]);
                }
                row++;
            }
        }
        return sudokuGrid;
    }

    @Override
    public boolean isSudokuMoveValid(int[][] sudokuGrid, int row, int col) {
        int num = sudokuGrid[row][col];

        // Check row
        for (int i = 0; i < GRID_SIZE; i++) {
            if (i != col && sudokuGrid[row][i] == num) {
                return false;
            }
        }

        // Check column
        for (int i = 0; i < GRID_SIZE; i++) {
            if (i != row && sudokuGrid[i][col] == num) {
                return false;
            }
        }

        // Check 3x3 subgrid
        int subgridRowStart = (row / 3) * 3;
        int subgridColStart = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int subgridRow = subgridRowStart + i;
                int subgridCol = subgridColStart + j;
                if (subgridRow != row && subgridCol != col && sudokuGrid[subgridRow][subgridCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int[][] recursivelySolveSudokuGrid(String filepath) throws IOException, SudokuNotSolvableException {
        int[][] sudokuGrid = readSudokuFromCSV(filepath);
        if (!solveSudoku(sudokuGrid)) {
            throw new SudokuNotSolvableException("Sudoku puzzle could not be solved.");
        }
        return sudokuGrid;
    }

    private boolean solveSudoku(int[][] sudokuGrid) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (sudokuGrid[row][col] == 0) {
                    for (int num = 1; num <= GRID_SIZE; num++) {
                        if (isSudokuMoveValid(sudokuGrid, row, col)) {
                            sudokuGrid[row][col] = num;
                            if (solveSudoku(sudokuGrid)) {
                                return true;
                            } else {
                                sudokuGrid[row][col] = 0; // Backtrack
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true; // Solved
    }
}
