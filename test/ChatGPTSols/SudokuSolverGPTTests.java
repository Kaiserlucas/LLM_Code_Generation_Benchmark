package ChatGPTSols;

import exceptions.GameHasEndedException;
import exceptions.InvalidMoveException;
import exceptions.SudokuNotSolvableException;
import exceptions.TurnOrderViolationException;
import org.junit.Assert;
import org.junit.Test;
import tasks.SudokuSolverBacktracking;

import java.io.IOException;

public class SudokuSolverGPTTests {

    public SudokuSolverBacktracking getSolver() {
        return new SudokuSolverGPT();
    }

    public int[][] getSolvableSudokuExample() {
        int[][] sudoku = new int[9][9];

        sudoku[0][1] = 5;
        sudoku[0][6] = 3;
        sudoku[1][0] = 6;
        sudoku[1][3] = 8;
        sudoku[2][3] = 2;
        sudoku[2][7] = 4;
        sudoku[3][5] = 4;
        sudoku[3][7] = 8;
        sudoku[4][3] = 6;
        sudoku[4][7] = 2;
        sudoku[5][1] = 3;
        sudoku[6][4] = 3;
        sudoku[6][5] = 5;
        sudoku[6][6] = 1;
        sudoku[7][4] = 7;
        sudoku[7][6] = 5;
        sudoku[8][0] = 2;

        return sudoku;
    }

    @Test
    public void readCSVTest() throws IOException {
        SudokuSolverBacktracking solver = getSolver();

        int[][] sudoku = solver.readSudokuFromCSV("resources/solvableSudoku.csv");
        int[][] controlSudoku = getSolvableSudokuExample();

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(sudoku[i][j] != controlSudoku[i][j]) {
                    Assert.fail();
                }
            }
        }

    }

    @Test
    public void checkValidMoveTest() {
        SudokuSolverBacktracking solver = getSolver();

        int[][] exampleSudoku = getSolvableSudokuExample();

        exampleSudoku[1][1] = 7;

        assert(solver.isSudokuMoveValid(exampleSudoku, 1, 1));

    }

    @Test
    public void checkInvalidMoveRowTest() {
        SudokuSolverBacktracking solver = getSolver();

        int[][] exampleSudoku = getSolvableSudokuExample();

        exampleSudoku[0][0] = 3;

        assert(!solver.isSudokuMoveValid(exampleSudoku, 1, 1));

    }

    @Test
    public void checkInvalidMoveColTest() {
        SudokuSolverBacktracking solver = getSolver();

        int[][] exampleSudoku = getSolvableSudokuExample();

        exampleSudoku[0][0] = 2;

        assert(!solver.isSudokuMoveValid(exampleSudoku, 1, 1));

    }

    @Test
    public void checkInvalidMove3x3Test() {
        SudokuSolverBacktracking solver = getSolver();

        int[][] exampleSudoku = getSolvableSudokuExample();

        exampleSudoku[2][2] = 5;

        assert(!solver.isSudokuMoveValid(exampleSudoku, 1, 1));

    }

    @Test
    public void unsolvableSudokuTest() throws IOException {
        SudokuSolverBacktracking solver = getSolver();

        int[][] exampleSudoku = getSolvableSudokuExample();

        exampleSudoku[2][2] = 5;

        try {
            solver.recursivelySolveSudokuGrid("resources/unsolvableSudoku.csv");
            Assert.fail();
        } catch (SudokuNotSolvableException e) {
            //Exception was expected
        }

    }

    @Test
    public void solvableSudokuTest() throws IOException, SudokuNotSolvableException {
        SudokuSolverBacktracking solver = getSolver();

        int[][] exampleSudoku = getSolvableSudokuExample();

        exampleSudoku[2][2] = 5;


        int[][] solution = solver.recursivelySolveSudokuGrid("resources/solvableSudoku.csv");
        int[][] canonicalSolution = solver.readSudokuFromCSV("resources/solvableSudokuSolution.csv");

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(solution[i][j] != canonicalSolution[i][j]) {
                    Assert.fail();
                }
            }
        }

    }

}
