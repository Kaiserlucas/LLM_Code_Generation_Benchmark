package tasks;

import exceptions.GameHasEndedException;
import exceptions.InvalidMoveException;
import exceptions.TurnOrderViolationException;

public interface ConnectFourEngine {

    //Implements a standard ConnectFour game between two players
    //Should protect against all kinds of illegal moves players can make, or making moves while it is not your turn
    //Once the game has ended, no new moves should be able to be made

    //Player Ids in this game are always either 1 or 2. Player 1 starts
    //The board is 6 spaces high and 7 spaces wide

    //Initialize a new game
    public void newGame();

    //Make a move, player ID is either 1 or 2
    //Additionally, return true if this move ended the game
    //Input for the columns ranges from 1-7, so take care with 0 indexed arrays
    public boolean makeMove(int column, int playerId) throws TurnOrderViolationException, InvalidMoveException, GameHasEndedException;

    //0 if game is ongoing, 1 or 2 if player 1 or 2 have won, 3 if there was a draw
    public int checkWinner();

    //Check who the piece at the specified cell belongs to. 0, if it is empty. 1 or 2 if there is a player piece there
    //Uses 1 indexing for the inputs, so be careful with 0 indexed arrays
    public int checkPieceAtCell(int row, int column);

}
