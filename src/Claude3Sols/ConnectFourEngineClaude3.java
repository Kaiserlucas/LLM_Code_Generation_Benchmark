package Claude3Sols;

import exceptions.GameHasEndedException;
import exceptions.InvalidMoveException;
import exceptions.TurnOrderViolationException;
import tasks.ConnectFourEngine;

public class ConnectFourEngineClaude3 implements ConnectFourEngine {
    private int[][] board;
    private int currentPlayer;
    private boolean gameEnded;

    public ConnectFourEngineClaude3() {
        newGame();
    }

    @Override
    public void newGame() {
        board = new int[6][7];
        currentPlayer = 1;
        gameEnded = false;
    }

    @Override
    public boolean makeMove(int column, int playerId) throws TurnOrderViolationException, InvalidMoveException, GameHasEndedException {
        if (gameEnded) {
            throw new GameHasEndedException("The game has already ended.");
        }

        if (playerId != currentPlayer) {
            throw new TurnOrderViolationException("It's not your turn.");
        }

        if (column < 1 || column > 7) {
            throw new InvalidMoveException("Invalid column number.");
        }

        int row = findFirstEmptyRow(column - 1);
        if (row == -1) {
            throw new InvalidMoveException("The column is full.");
        }

        board[row][column - 1] = playerId;
        currentPlayer = (currentPlayer == 1) ? 2 : 1;

        if (checkWinner() != 0) {
            gameEnded = true;
            return true;
        }

        return false;
    }

    @Override
    public int checkWinner() {
        // Check rows
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] != 0 &&
                        board[row][col] == board[row][col + 1] &&
                        board[row][col] == board[row][col + 2] &&
                        board[row][col] == board[row][col + 3]) {
                    return board[row][col];
                }
            }
        }

        // Check columns
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 3; row++) {
                if (board[row][col] != 0 &&
                        board[row][col] == board[row + 1][col] &&
                        board[row][col] == board[row + 2][col] &&
                        board[row][col] == board[row + 3][col]) {
                    return board[row][col];
                }
            }
        }

        // Check diagonals
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] != 0 &&
                        board[row][col] == board[row + 1][col + 1] &&
                        board[row][col] == board[row + 2][col + 2] &&
                        board[row][col] == board[row + 3][col + 3]) {
                    return board[row][col];
                }
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 6; col > 2; col--) {
                if (board[row][col] != 0 &&
                        board[row][col] == board[row + 1][col - 1] &&
                        board[row][col] == board[row + 2][col - 2] &&
                        board[row][col] == board[row + 3][col - 3]) {
                    return board[row][col];
                }
            }
        }

        // Check for a draw
        for (int col = 0; col < 7; col++) {
            if (board[0][col] == 0) {
                return 0; // Game is not over, still empty spaces
            }
        }
        return 3; // Draw
    }

    @Override
    public int checkPieceAtCell(int row, int column) {
        if (row < 1 || row > 6 || column < 1 || column > 7) {
            throw new IllegalArgumentException("Invalid row or column number.");
        }
        return board[row - 1][column - 1];
    }

    private int findFirstEmptyRow(int column) {
        for (int row = 5; row >= 0; row--) {
            if (board[row][column] == 0) {
                return row;
            }
        }
        return -1;
    }
}