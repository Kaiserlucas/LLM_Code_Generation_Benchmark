package tasks;

import exceptions.SudokuNotSolvableException;

import java.io.IOException;

public interface SudokuSolverBacktracking {

    // Reads a 9x9 Sudoku puzzle from a CSV file. Empty cells are denoted as 0
    public int[][] readSudokuFromCSV(String filepath) throws IOException;

    //Check if the entry at [row][col] violates any sudoku rules (same number in row, column or 3x3 area)
    public boolean isSudokuMoveValid(int[][] sudokuGrid, int row, int col);

    //Call readSudokuFromCSV to get the grid, then solve it recursively using a backtracking algorithm
    //All empty cells will be denoted as 0 in the CSV files
    //Use the function isSudokuMoveValid in this function
    public int[][] recursivelySolveSudokuGrid(String filepath) throws IOException, SudokuNotSolvableException;
}
