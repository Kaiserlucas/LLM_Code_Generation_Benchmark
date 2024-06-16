package Llama3Sols;

import exceptions.SudokuNotSolvableException;
import tasks.SudokuSolverBacktracking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SudokuSolverLlama3 implements SudokuSolverBacktracking {

    @Override
    public int[][] readSudokuFromCSV(String filepath) throws IOException {
        int[][] sudokuGrid = new int[9][9];
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {

                // Initial output of this, which did not compile
                // String[] values = lineplits(",");

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
        int value = sudokuGrid[row][col];
        // Check row
        for (int i = 0; i < 9; i++) {
            if (i != col && sudokuGrid[row][i] == value) {
                return false;
            }
        }
        // Check column
        for (int i = 0; i < 9; i++) {
            if (i != row && sudokuGrid[i][col] == value) {
                return false;
            }
        }
        // Check 3x3 area
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((startRow + i != row || startCol + j != col) && sudokuGrid[startRow + i][startCol + j] == value) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int[][] recursivelySolveSudokuGrid(String filepath) throws IOException, SudokuNotSolvableException {
        int[][] sudokuGrid = readSudokuFromCSV(filepath);
        return recursivelySolveSudokuGrid(sudokuGrid, 0, 0);
    }

    private int[][] recursivelySolveSudokuGrid(int[][] sudokuGrid, int row, int col) throws SudokuNotSolvableException {
        if (row == 9 - 1 && col == 9) {
            return sudokuGrid; // Sudoku is solved
        }
        if (col == 9) {
            row++;
            col = 0;
        }
        if (sudokuGrid[row][col] != 0) {
            return recursivelySolveSudokuGrid(sudokuGrid, row, col + 1);
        }
        for (int value = 1; value <= 9; value++) {
            sudokuGrid[row][col] = value;
            if (isSudokuMoveValid(sudokuGrid, row, col)) {
                int[][] solution = recursivelySolveSudokuGrid(sudokuGrid, row, col + 1);
                if (solution != null) {
                    return solution;
                }
            }
            sudokuGrid[row][col] = 0; // Backtrack
        }
        throw new SudokuNotSolvableException("Sudoku is not solvable");
    }
}