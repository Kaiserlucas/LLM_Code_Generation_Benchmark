package Claude3Sols;

import exceptions.SudokuNotSolvableException;
import tasks.SudokuSolverBacktracking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SudokuSolverClaude3 implements SudokuSolverBacktracking {

    @Override
    public int[][] readSudokuFromCSV(String filepath) throws IOException {
        int[][] sudokuGrid = new int[9][9];
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String line;
        int row = 0;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            for (int col = 0; col < 9; col++) {
                sudokuGrid[row][col] = Integer.parseInt(values[col]);
            }
            row++;
        }
        reader.close();
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
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (i != row && j != col && sudokuGrid[i][j] == value) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean solveSudoku(int[][] sudokuGrid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudokuGrid[row][col] == 0) {
                    for (int value = 1; value <= 9; value++) {
                        sudokuGrid[row][col] = value;
                        if (isSudokuMoveValid(sudokuGrid, row, col) && solveSudoku(sudokuGrid)) {
                            return true;
                        }
                        sudokuGrid[row][col] = 0;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int[][] recursivelySolveSudokuGrid(String filepath) throws IOException, SudokuNotSolvableException {
        int[][] sudokuGrid = readSudokuFromCSV(filepath);
        if (solveSudoku(sudokuGrid)) {
            return sudokuGrid;
        } else {
            throw new SudokuNotSolvableException("The Sudoku grid is not solvable.");
        }
    }
}