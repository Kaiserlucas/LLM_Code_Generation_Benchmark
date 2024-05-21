package BingSols;

import exceptions.GameHasEndedException;
import exceptions.InvalidMoveException;
import exceptions.TurnOrderViolationException;
import tasks.ConnectFourEngine;

public class ConnectFourEngineBing implements ConnectFourEngine {

    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private int[][] board;
    private boolean gameEnded;
    private int winner; // 0 if ongoing, 1 or 2 if player 1 or 2 has won, 3 if draw
    private int currentPlayer;

    public ConnectFourEngineBing() {
        newGame();
    }

    @Override
    public void newGame() {
        board = new int[ROWS][COLUMNS];
        gameEnded = false;
        winner = 0;
        currentPlayer = 1;
    }

    @Override
    public boolean makeMove(int column, int playerId) throws TurnOrderViolationException, InvalidMoveException, GameHasEndedException {
        if (gameEnded) {
            throw new GameHasEndedException("Game has already ended.");
        }
        if (playerId != currentPlayer) {
            throw new TurnOrderViolationException("It's not player " + playerId + "'s turn.");
        }
        if (column < 1 || column > COLUMNS) {
            throw new InvalidMoveException("Invalid column. Column must be between 1 and 7.");
        }

        // Find the first empty row in the specified column
        int row = -1;
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][column - 1] == 0) {
                row = i;
                break;
            }
        }

        if (row == -1) {
            throw new InvalidMoveException("Column " + column + " is full.");
        }

        // Place the player's piece
        board[row][column - 1] = playerId;

        // Check for a win
        if (checkWin(row, column - 1)) {
            winner = playerId;
            gameEnded = true;
            return true;
        }

        // Check for a draw
        if (isBoardFull()) {
            winner = 3; // Draw
            gameEnded = true;
        }

        // Switch to the next player
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        return false;
    }

    @Override
    public int checkWinner() {
        return winner;
    }

    @Override
    public int checkPieceAtCell(int row, int column) {
        return board[row][column];
    }

    private boolean checkWin(int row, int col) {
        // Check horizontally
        int count = 0;
        for (int i = Math.max(0, col - 3); i <= Math.min(COLUMNS - 1, col + 3); i++) {
            if (board[row][i] == currentPlayer) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // Check vertically
        count = 0;
        for (int i = Math.max(0, row - 3); i <= Math.min(ROWS - 1, row + 3); i++) {
            if (board[i][col] == currentPlayer) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // Check diagonally (positive slope)
        count = 0;
        for (int i = -3; i <= 3; i++) {
            int r = row + i;
            int c = col + i;
            if (r >= 0 && r < ROWS && c >= 0 && c < COLUMNS) {
                if (board[r][c] == currentPlayer) {
                    count++;
                    if (count == 4) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }

        // Check diagonally (negative slope)
        count = 0;
        for (int i = -3; i <= 3; i++) {
            int r = row + i;
            int c = col - i;
            if (r >= 0 && r < ROWS && c >= 0 && c < COLUMNS) {
                if (board[r][c] == currentPlayer) {
                    count++;
                    if (count == 4) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }

        return false;
    }


    private boolean isBoardFull() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
