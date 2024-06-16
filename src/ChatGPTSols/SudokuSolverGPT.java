package ChatGPTSols;

import exceptions.SudokuNotSolvableException;
import tasks.SudokuSolverBacktracking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SudokuSolverGPT implements SudokuSolverBacktracking {

    @Override
    public int[][] readSudokuFromCSV(String filepath) throws IOException {
        int[][] sudokuGrid = new int[9][9];
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null && row < 9) {
                String[] values = line.split(",");
                for (int col = 0; col < 9; col++) {
                    sudokuGrid[row][col] = Integer.parseInt(values[col].trim());
                }
                row++;
            }
        }
        return sudokuGrid;
    }

    @Override
    public boolean isSudokuMoveValid(int[][] sudokuGrid, int row, int col) {
        int num = sudokuGrid[row][col];
        for (int i = 0; i < 9; i++) {
            // Check row and column
            if ((i != col && sudokuGrid[row][i] == num) || (i != row && sudokuGrid[i][col] == num)) {
                return false;
            }
        }

        // Check 3x3 subgrid
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if ((i != row || j != col) && sudokuGrid[i][j] == num) {
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
            throw new SudokuNotSolvableException("Sudoku puzzle is not solvable.");
        }
        return sudokuGrid;
    }

    private boolean solveSudoku(int[][] sudokuGrid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudokuGrid[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        sudokuGrid[row][col] = num;
                        if (isSudokuMoveValid(sudokuGrid, row, col) && solveSudoku(sudokuGrid)) {
                            return true;
                        }
                        sudokuGrid[row][col] = 0; // Reset on backtrack
                    }
                    return false; // Trigger backtrack
                }
            }
        }
        return true; // Puzzle solved
    }

}
