package Llama3Sols;

import exceptions.GameHasEndedException;
import exceptions.InvalidMoveException;
import exceptions.TurnOrderViolationException;
import tasks.ConnectFourEngine;

public class ConnectFourEngineLlama3 implements ConnectFourEngine {

    private int[][] board; // 6x7 board, 0-indexed
    private int currentPlayer; // 1 or 2
    private boolean gameOver; // true if game has ended

    public ConnectFourEngineLlama3() {
        // Initialize board and game state
        board = new int[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = 0; // 0 means empty cell
            }
        }
        currentPlayer = 1; // Player 1 starts
        gameOver = false;
    }

    @Override
    public void newGame() {
        // Reset board and game state
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = 0; // 0 means empty cell
            }
        }
        currentPlayer = 1; // Player 1 starts
        gameOver = false;
    }

    @Override
    public boolean makeMove(int column, int playerId) throws TurnOrderViolationException, InvalidMoveException, GameHasEndedException {
        if (gameOver) {
            throw new GameHasEndedException("");
        }
        if (playerId != currentPlayer) {
            throw new TurnOrderViolationException("");
        }
        if (column < 1 || column > 7) {
            throw new InvalidMoveException("");
        }
        // Find the next available row in the specified column
        int row = 0;
        while (row < 6 && board[row][column - 1] != 0) {
            row++;
        }
        if (row == 6) {
            throw new InvalidMoveException(""); // column is full
        }
        board[row][column - 1] = playerId;
        // Check if this move won the game
        if (checkWinner() != 0) {
            gameOver = true;
            return true;
        }
        // Switch player
        currentPlayer = 3 - currentPlayer; // 1 becomes 2, 2 becomes 1
        return false;
    }

    @Override
    public int checkWinner() {
        // Check horizontal wins
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != 0 && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3]) {
                    return board[i][j];
                }
            }
        }
        // Check vertical wins
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] != 0 && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j]) {
                    return board[i][j];
                }
            }
        }
        // Check diagonal wins (top-left to bottom-right)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != 0 && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3]) {
                    return board[i][j];
                }
            }
        }
        // Check diagonal wins (bottom-left to top-right)
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != 0 && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3]) {
                    return board[i][j];
                }
            }
        }
        // Check for draw
        for (int j = 0; j < 7; j++) {
            if (board[0][j] == 0) {
                return 0; // game is not over yet
            }
        }
        return 3; // draw
    }

    @Override
    public int checkPieceAtCell(int row, int column) {
        return board[row - 1][column - 1];
    }
}