package Claude3Sols;

import exceptions.GameHasEndedException;
import exceptions.InvalidMoveException;
import exceptions.TurnOrderViolationException;
import org.junit.Assert;
import org.junit.Test;
import tasks.ConnectFourEngine;

public class ConnectFourEngineClaude3Tests {

    public ConnectFourEngine getBaseEngine() {
        return new ConnectFourEngineClaude3();
    }

    @Test
    public void checkPieceTest() throws GameHasEndedException, TurnOrderViolationException, InvalidMoveException {
        ConnectFourEngine engine = getBaseEngine();
        engine.newGame();

        assert(engine.checkPieceAtCell(1,1) == 0);

        engine.makeMove(1, 1);
        assert(engine.checkPieceAtCell(6,1) == 1);
        engine.makeMove(2, 2);
        assert(engine.checkPieceAtCell(6,2) == 2);
    }

    @Test
    public void dropPieceTest1() throws GameHasEndedException, TurnOrderViolationException, InvalidMoveException {
        ConnectFourEngine engine = getBaseEngine();
        engine.newGame();

        engine.makeMove(3, 1);

        //Single piece was dropped successfully
        assert(engine.checkPieceAtCell(6,3) == 1);
    }

    @Test
    public void dropPieceTest2() throws GameHasEndedException, TurnOrderViolationException, InvalidMoveException {
        ConnectFourEngine engine = getBaseEngine();
        engine.newGame();

        engine.makeMove(3, 1);
        engine.makeMove(3, 2);

        //Two pieces were dropped on top of one another successfully
        assert(engine.checkPieceAtCell(6,3) == 1);
        assert(engine.checkPieceAtCell(5,3) == 2);
    }

    @Test
    public void dropPieceInputRangeTest() throws GameHasEndedException, TurnOrderViolationException, InvalidMoveException {
        ConnectFourEngine engine = getBaseEngine();
        engine.newGame();

        //See if inputs between 1 and 7 are correctly accepted
        engine.makeMove(1, 1);
        engine.makeMove(7, 2);

    }

    @Test
    public void dropPieceInvalidTest1() throws GameHasEndedException, TurnOrderViolationException, InvalidMoveException {
        ConnectFourEngine engine = getBaseEngine();
        engine.newGame();

        try {
            engine.makeMove(0, 1);
            Assert.fail();
        } catch(InvalidMoveException e) {
            //0 should throw an exception as an invalid move
        }
    }

    @Test
    public void dropPieceInvalidTest2() throws GameHasEndedException, TurnOrderViolationException, InvalidMoveException {
        ConnectFourEngine engine = getBaseEngine();
        engine.newGame();

        try {
            engine.makeMove(8, 1);
            Assert.fail();
        } catch(InvalidMoveException e) {
            //8 should throw an exception as an invalid move
        }
    }

    @Test
    public void dropPieceInvalidTest3() throws GameHasEndedException, TurnOrderViolationException, InvalidMoveException {
        ConnectFourEngine engine = getBaseEngine();
        engine.newGame();

        engine.makeMove(1, 1);
        engine.makeMove(1, 2);
        engine.makeMove(1, 1);
        engine.makeMove(1, 2);
        engine.makeMove(1, 1);
        engine.makeMove(1, 2);

        try {
            engine.makeMove(1, 1);
            Assert.fail();
        } catch(InvalidMoveException e) {
            //Column 1 can only hold six pieces. Any further drops in column 1 should throw an exception
        }
    }

    @Test
    public void dropPieceTurnOrderTest1() throws GameHasEndedException, TurnOrderViolationException, InvalidMoveException {
        ConnectFourEngine engine = getBaseEngine();
        engine.newGame();

        try {
            engine.makeMove(1, 2);
            Assert.fail();
        } catch(TurnOrderViolationException e) {
            //Player 1 always starts. So this should be an exception
        }
    }

    @Test
    public void dropPieceTurnOrderTest2() throws GameHasEndedException, TurnOrderViolationException, InvalidMoveException {
        ConnectFourEngine engine = getBaseEngine();
        engine.newGame();

        engine.makeMove(1, 1);

        try {
            engine.makeMove(1, 1);
            Assert.fail();
        } catch(TurnOrderViolationException e) {
            //makeMove should change the current player after execution. So this should throw an exception
        }
    }

    @Test
    public void dropPieceTurnOrderTest3() throws GameHasEndedException, TurnOrderViolationException, InvalidMoveException {
        ConnectFourEngine engine = getBaseEngine();
        engine.newGame();

        engine.makeMove(1, 1);
        engine.newGame();

        //newGame() should reset the current player. So this should not throw an exception
        engine.makeMove(1, 1);
    }

    @Test
    public void newGameTest() throws GameHasEndedException, TurnOrderViolationException, InvalidMoveException {
        ConnectFourEngine engine = getBaseEngine();
        engine.newGame();

        engine.makeMove(1, 1);
        assert(engine.checkPieceAtCell(6,1) == 1);

        engine.newGame();

        //This piece should be reset back to 0
        assert(engine.checkPieceAtCell(6,1) == 0);
    }

    @Test
    public void winTest1() throws GameHasEndedException, TurnOrderViolationException, InvalidMoveException {
        ConnectFourEngine engine = getBaseEngine();
        engine.newGame();

        //Player 1 should win with a vertical connect four after these moves
        assert(!engine.makeMove(1, 1));
        assert(!engine.makeMove(2, 2));
        assert(!engine.makeMove(1, 1));
        assert(!engine.makeMove(2, 2));
        assert(!engine.makeMove(1, 1));
        assert(!engine.makeMove(2, 2));
        assert(engine.makeMove(1, 1));

        assert(engine.checkWinner() == 1);

    }

    @Test
    public void winTest2() throws GameHasEndedException, TurnOrderViolationException, InvalidMoveException {
        ConnectFourEngine engine = getBaseEngine();
        engine.newGame();

        //Player 1 should win with a horizontal connect four after these moves
        assert(!engine.makeMove(1, 1));
        assert(!engine.makeMove(1, 2));
        assert(!engine.makeMove(2, 1));
        assert(!engine.makeMove(2, 2));
        assert(!engine.makeMove(3, 1));
        assert(!engine.makeMove(3, 2));
        assert(engine.makeMove(4, 1));

        assert(engine.checkWinner() == 1);

    }

    @Test
    public void winTest3() throws GameHasEndedException, TurnOrderViolationException, InvalidMoveException {
        ConnectFourEngine engine = getBaseEngine();
        engine.newGame();

        //Player 1 should win with a diagonal (bottom to top) connect four after these moves
        assert(!engine.makeMove(1, 1));
        assert(!engine.makeMove(2, 2));
        assert(!engine.makeMove(2, 1));
        assert(!engine.makeMove(3, 2));
        assert(!engine.makeMove(3, 1));
        assert(!engine.makeMove(4, 2));
        assert(!engine.makeMove(3, 1));
        assert(!engine.makeMove(4, 2));
        assert(!engine.makeMove(4, 1));
        assert(!engine.makeMove(6, 2));
        assert(engine.makeMove(4, 1));

        assert(engine.checkWinner() == 1);

    }

    @Test
    public void winTest4() throws GameHasEndedException, TurnOrderViolationException, InvalidMoveException {
        ConnectFourEngine engine = getBaseEngine();
        engine.newGame();

        //Player 2 should win with a diagonal (top to bottom) connect four after these moves
        assert(!engine.makeMove(1, 1));
        assert(!engine.makeMove(1, 2));
        assert(!engine.makeMove(1, 1));
        assert(!engine.makeMove(1, 2));
        assert(!engine.makeMove(2, 1));
        assert(!engine.makeMove(2, 2));
        assert(!engine.makeMove(3, 1));
        assert(!engine.makeMove(2, 2));
        assert(!engine.makeMove(5, 1));
        assert(!engine.makeMove(3, 2));
        assert(!engine.makeMove(5, 1));
        assert(engine.makeMove(4, 2));

        assert(engine.checkWinner() == 2);

    }

    @Test
    public void drawTest() throws GameHasEndedException, TurnOrderViolationException, InvalidMoveException {
        ConnectFourEngine engine = getBaseEngine();
        engine.newGame();

        //There should be a draw after these moves
        assert(!engine.makeMove(1, 1));
        assert(!engine.makeMove(2, 2));
        assert(!engine.makeMove(1, 1));
        assert(!engine.makeMove(2, 2));
        assert(!engine.makeMove(1, 1));
        assert(!engine.makeMove(2, 2));
        assert(!engine.makeMove(3, 1));
        assert(!engine.makeMove(4, 2));
        assert(!engine.makeMove(3, 1));
        assert(!engine.makeMove(4, 2));
        assert(!engine.makeMove(3, 1));
        assert(!engine.makeMove(4, 2));
        assert(!engine.makeMove(5, 1));
        assert(!engine.makeMove(6, 2));
        assert(!engine.makeMove(5, 1));
        assert(!engine.makeMove(6, 2));
        assert(!engine.makeMove(5, 1));
        assert(!engine.makeMove(6, 2));
        assert(!engine.makeMove(2, 1));
        assert(!engine.makeMove(1, 2));
        assert(!engine.makeMove(2, 1));
        assert(!engine.makeMove(1, 2));
        assert(!engine.makeMove(2, 1));
        assert(!engine.makeMove(1, 2));
        assert(!engine.makeMove(4, 1));
        assert(!engine.makeMove(3, 2));
        assert(!engine.makeMove(4, 1));
        assert(!engine.makeMove(3, 2));
        assert(!engine.makeMove(4, 1));
        assert(!engine.makeMove(3, 2));
        assert(!engine.makeMove(6, 1));
        assert(!engine.makeMove(5, 2));
        assert(!engine.makeMove(6, 1));
        assert(!engine.makeMove(5, 2));
        assert(!engine.makeMove(6, 1));
        assert(!engine.makeMove(5, 2));
        assert(!engine.makeMove(7, 1));
        assert(!engine.makeMove(7, 2));
        assert(!engine.makeMove(7, 1));
        assert(!engine.makeMove(7, 2));
        assert(!engine.makeMove(7, 1));
        assert(engine.makeMove(7, 2));

        assert(engine.checkWinner() == 3);

    }

    @Test
    public void gameEnded1() throws GameHasEndedException, TurnOrderViolationException, InvalidMoveException {
        ConnectFourEngine engine = getBaseEngine();
        engine.newGame();

        //Player 1 should win with a vertical connect four after these moves
        assert(!engine.makeMove(1, 1));
        assert(!engine.makeMove(2, 2));
        assert(!engine.makeMove(1, 1));
        assert(!engine.makeMove(2, 2));
        assert(!engine.makeMove(1, 1));
        assert(!engine.makeMove(2, 2));
        assert(engine.makeMove(1, 1));

        try {
            engine.makeMove(1, 1);
            Assert.fail();
        } catch(GameHasEndedException e) {
            //The game has ended, so this exception is expected
        }

    }

}
