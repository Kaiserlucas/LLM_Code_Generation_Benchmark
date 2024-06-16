package BingSols;

import exceptions.SudokuNotSolvableException;
import tasks.SudokuSolverBacktracking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SudokuSolverBing implements SudokuSolverBacktracking {

    @Override
    public int[][] readSudokuFromCSV(String filepath) throws IOException {
        // Read the CSV file and populate the Sudoku grid
        int[][] sudokuGrid = new int[9][9];
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                for (int col = 0; col < 9; col++) {
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
        // Check row, column, and 3x3 area for validity
        for (int i = 0; i < 9; i++) {
            if (sudokuGrid[row][i] == num || sudokuGrid[i][col] == num) {
                return false;
            }
        }
        int startRow = row / 3 * 3;
        int startCol = col / 3 * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (sudokuGrid[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int[][] recursivelySolveSudokuGrid(String filepath) throws IOException, SudokuNotSolvableException {
        int[][] sudokuGrid = readSudokuFromCSV(filepath);
        if (!solveSudoku(sudokuGrid, 0, 0)) {
            throw new SudokuNotSolvableException("The Sudoku puzzle is not solvable.");
        }
        return sudokuGrid;
    }

    private boolean solveSudoku(int[][] sudokuGrid, int row, int col) {
        if (row == 9) {
            return true; // Entire grid is filled
        }
        if (col == 9) {
            return solveSudoku(sudokuGrid, row + 1, 0); // Move to next row
        }
        if (sudokuGrid[row][col] != 0) {
            return solveSudoku(sudokuGrid, row, col + 1); // Cell already filled, move to next column
        }
        for (int num = 1; num <= 9; num++) {
            if (isSudokuMoveValid(sudokuGrid, row, col)) {
                sudokuGrid[row][col] = num;
                if (solveSudoku(sudokuGrid, row, col + 1)) {
                    return true;
                }
                sudokuGrid[row][col] = 0; // Backtrack
            }
        }
        return false; // No valid number found
    }
}
