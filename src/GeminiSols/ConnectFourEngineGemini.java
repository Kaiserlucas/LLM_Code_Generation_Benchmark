package GeminiSols;

import exceptions.GameHasEndedException;
import exceptions.InvalidMoveException;
import exceptions.TurnOrderViolationException;
import tasks.ConnectFourEngine;

public class ConnectFourEngineGemini implements ConnectFourEngine {

    private int[][] board; // 2D array to represent the game board
    private int currentPlayer = 1; // Tracks the current player (1 or 2)
    private boolean gameOver = false; // Flag indicating if the game has ended

    public ConnectFourEngineGemini() {
        board = new int[6][7]; // Initialize the board with empty cells (0)
    }

    @Override
    public void newGame() {
        board = new int[6][7]; // Reset board to empty state
        currentPlayer = 1; // Player 1 starts
        gameOver = false;
    }

    @Override
    public boolean makeMove(int column, int playerId) throws TurnOrderViolationException, InvalidMoveException, GameHasEndedException {
        if (gameOver) {
            throw new GameHasEndedException("Game is already over.");
        }
        if (playerId != currentPlayer) {
            throw new TurnOrderViolationException("It is not player " + playerId + "'s turn.");
        }
        if (column < 1 || column > 7) {
            throw new InvalidMoveException("Invalid column number. Choose between 1 and 7.");
        }

        // Check if the chosen column is full
        if (board[0][column - 1] != 0) {
            throw new InvalidMoveException("Column " + column + " is full. Choose another column.");
        }

        // Find the first empty row in the chosen column
        int row = 5;
        while (row >= 0 && board[row][column - 1] != 0) {
            row--;
        }

        // Place the player's piece in the empty cell
        board[row][column - 1] = playerId;

        // Check if the move resulted in a win or draw
        gameOver = checkWinner() != 0 || isBoardFull();

        // Switch player for the next turn
        currentPlayer = (currentPlayer % 2) + 1;

        return gameOver;
    }

    @Override
    public int checkWinner() {
        // Check for horizontal wins
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] != 0 && board[row][col] == board[row][col + 1] && board[row][col] == board[row][col + 2] && board[row][col] == board[row][col + 3]) {
                    return board[row][col];
                }
            }
        }

        // Check for vertical wins
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 3; row++) {
                if (board[row][col] != 0 && board[row][col] == board[row + 1][col] && board[row][col] == board[row + 2][col] && board[row][col] == board[row + 3][col]) {
                    return board[row][col];
                }
            }
        }

        // Check for diagonal wins (top-left to bottom-right)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] != 0 && board[row][col] == board[row + 1][col + 1] && board[row][col] == board[row + 2][col + 2] && board[row][col] == board[row + 3][col + 3]) {
                    return board[row][col];
                }
            }
        }

        // Check for diagonal wins (bottom-left to top-right)
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] != 0 && board[row][col] == board[row - 1][col + 1] && board[row][col] == board[row - 2][col + 2] && board[row][col] == board[row - 3][col + 3]) {
                    return board[row][col];
                }
            }
        }

        // If no winner, check for a draw
        return isBoardFull() ? 3 : 0;
    }

    @Override
    public int checkPieceAtCell(int row, int column) {
        if (row < 1 || row > 6 || column < 1 || column > 7) {
            throw new IllegalArgumentException("Invalid row or column number.");
        }
        return board[row - 1][column - 1];
    }

    private boolean isBoardFull() {
        for (int col = 0; col < 7; col++) {
            if (board[0][col] == 0) {
                return false;
            }
        }
        return true;
    }
}
