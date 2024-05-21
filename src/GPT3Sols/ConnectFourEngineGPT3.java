package GPT3Sols;

import exceptions.GameHasEndedException;
import exceptions.InvalidMoveException;
import exceptions.TurnOrderViolationException;
import tasks.ConnectFourEngine;

public class ConnectFourEngineGPT3 implements ConnectFourEngine {
    private int[][] board; // Represents the game board
    private boolean gameOver; // Indicates if the game is over
    private int winner; // Stores the winner of the game
    private int currentPlayer; // Indicates the current player

    // Initialize the game board and other variables
    public void newGame() {
        board = new int[6][7];
        gameOver = false;
        winner = 0;
        currentPlayer = 1; // Player 1 starts
    }

    // Make a move and return true if this move ended the game
    public boolean makeMove(int column, int playerId) throws TurnOrderViolationException, InvalidMoveException, GameHasEndedException {
        if (gameOver) {
            throw new GameHasEndedException("Game has already ended.");
        }

        if (playerId != currentPlayer) {
            throw new TurnOrderViolationException("It's not your turn.");
        }

        if (column < 1 || column > 7) {
            throw new InvalidMoveException("Invalid column number.");
        }

        int colIndex = column - 1; // Convert to 0-indexed column

        // Find an empty row in the specified column to place the piece
        int rowIndex = -1;
        for (int i = 5; i >= 0; i--) {
            if (board[i][colIndex] == 0) {
                rowIndex = i;
                break;
            }
        }

        if (rowIndex == -1) {
            throw new InvalidMoveException("Column is full.");
        }

        // Place the player's piece on the board
        board[rowIndex][colIndex] = playerId;

        // Check for a winner
        if (checkWinner() != 0) {
            gameOver = true;
            return true;
        }

        // Check for a draw
        if (isBoardFull()) {
            gameOver = true;
            winner = 3; // Draw
            return true;
        }

        // Switch to the next player
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        return false;
    }

    // Check if there is a winner or a draw
    public int checkWinner() {
        // Check rows
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != 0 && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3]) {
                    winner = board[i][j];
                    return winner;
                }
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] != 0 && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j]) {
                    winner = board[i][j];
                    return winner;
                }
            }
        }

        // Check diagonals (top-left to bottom-right)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != 0 && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3]) {
                    winner = board[i][j];
                    return winner;
                }
            }
        }

        // Check diagonals (bottom-left to top-right)
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != 0 && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3]) {
                    winner = board[i][j];
                    return winner;
                }
            }
        }

        return winner; // 0 if game is ongoing, 3 if there was a draw
    }

    // Check who the piece at the specified cell belongs to
    public int checkPieceAtCell(int row, int column) {
        return board[row - 1][column - 1]; // Convert to 0-indexed row and column
    }

    // Check if the board is full
    private boolean isBoardFull() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
