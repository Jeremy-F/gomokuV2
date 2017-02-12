package fr.esiee.player;
import fr.esiee.Board;
import fr.esiee.Box;
import fr.esiee.core.CoupleMinMax;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 *****************************************************
 * ,----.     E3T - Esiee Paris      ,--.            *
 * '  .-./    ,---. ,--,--,--. ,---. |  |,-.,--.,--. *
 * |  | .---.| .-. ||        || .-. ||     /|  ||  | *
 * '  '--'  |' '-' '|  |  |  |' '-' '|  \  \'  ''  ' *
 * `------'  `---' `--`--`--' `---' `--'`--'`------' *
 *    Alexandre Causse            Jérémy Fornarino   *
 *****************************************************
 * @author Alexandre Causse & Jérémy Fornarino   [E3T]
 */
public abstract class Player {
	private String name;
	private Color color;

	private static int MAX_POINT = 1000;
	private static int MIN_POINT = -1000;
	private static int AVERAGE_POINT = 0;
    private static int DEPTH = 3;

	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
	}

    public void setName(String name) {
        this.name = name;
    }

	abstract public boolean play(Board board);

	public Color getColor() {
		return this.color;
	}

	@Override
	public String toString() {
		return "Player{" +
				"name='" + name.toString() + '}';
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return (name != null ? name.equals(player.name) : player.name == null) && (color != null ? color.equals(player.color) : player.color == null);
    }

    public boolean haveWinOn(Board board){
	    return board.isWonBy(this);
    }

    public boolean wonByOtherPlayerOn(Board board) {
        return board.isFinished() && !this.haveWinOn(board) && board.whoWon() != null;
    }




	public int evaluate(Board board) {
	    int points = board.getNumberOfMoves();

		if (haveWinOn(board)) {
            points =  MAX_POINT - points;
            //System.out.println(this + " winning in points" + points);
        }
		else
		    if (wonByOtherPlayerOn(board)) {
                points = MIN_POINT + points;
               // System.out.println(this + " loosing in points" + points);
                }
            else
                points = computeAverageValue(board);

		return points;
	}

    private int computeAverageValue(Board board) {
        //todo change size

        final int alignementSize = 2;
        Player otherPlayer = board.getOtherPlayer(this);

        final int getnumberOfAlignementsOfThis = board.getnumberOfAlignementsOf(this, alignementSize);

        final int getnumberOfAlignementsOfOther = board.getnumberOfAlignementsOf(otherPlayer, alignementSize);
        //System.out.println("for " + this + "against" + otherPlayer +
        //        getnumberOfAlignementsOfThis + " against " + getnumberOfAlignementsOfOther);
        return getnumberOfAlignementsOfThis - getnumberOfAlignementsOfOther;
    }


    // precondition : the game is not over
    public boolean playSmart0(Board board){

        Board newBoard = cloneBoardForSimulation(board);
        Box bestMove = null;
        try {

            bestMove = getBestMove(newBoard);
            // useless ?
            board.setCurrentPlayer(this);
            board.play(bestMove.getLine(), bestMove.getColumn());
            //System.out.println("I compute best move for " + this + "and plays " + bestMove);
        } catch (Exception e) {
            System.out.println("playSmart0 : Pas de bestMove possible");
            return false;
        }
        return true;
    }

    private Box getBestMove(Board simulationBoard) throws Exception {

        int maxValue = Integer.MIN_VALUE;

        ArrayList<Box> copYfreeBoxes = simulationBoard.getAllEmptyBox();
        ArrayList<Box>freeBoxes = new ArrayList<>();
        for (Box b : copYfreeBoxes)
            freeBoxes.add(new Box(b.getLine(),b.getColumn()));

        //useless?
        Box bestMove = freeBoxes.get(0);

        Board savBoard = cloneBoardForSimulation(simulationBoard);

        for (Box box : freeBoxes) {
            //We play for the current Player one move

            simulateOneTurn(simulationBoard,box);
            //We try all the possibilities and return the min and max
            CoupleMinMax coupleMinMax = minMax(simulationBoard, DEPTH);
            //System.out.println("I try for me " + this + " : " + box + "and get " + coupleMinMax + simulationBoard.isWonBy(this));

            if (coupleMinMax.getMin() > maxValue) {
                maxValue = coupleMinMax.getMin();
                bestMove = box;
               // System.out.println("Best choice :   " + box +" because  " + coupleMinMax );
            }
            //We cancel all moves
            simulationBoard = savBoard;

        }

        //System.out.println("I Tried " + bestMove +" for " + this + "and get " + maxValue );
        return bestMove;
    }

    private Board cloneBoardForSimulation(Board board) {
        Board simulationBoard = board.clone();
        Player ennemy = simulationBoard.getNextPlayer();
        //We want the ennemy to be so clever than us
       // System.out.println("Ennem u : " + ennemy + "===" + this);
        makeEnnemySmartForSimulation(simulationBoard, ennemy);
        return simulationBoard;
    }

    private void makeEnnemySmartForSimulation(Board simulationBoard, Player ennemy) {
        if(!ennemy.getClass().equals(this.getClass())){
            SmartIAV1 newEnemy = new SmartIAV1("Copy of " + ennemy.name, ennemy.getColor());
            simulationBoard.setOtherPlayer(newEnemy) ;

            for(SimpleObjectProperty<Box> propertyBox : simulationBoard.getBoxes()){
                Player owner = propertyBox.get().getOwner();
                if(owner != null) {
                    if (owner.name.equals(ennemy.name)) {
                        propertyBox.get().setOwner(newEnemy);
                    }
                }
            }
        }
    }

    private void execSimulation(Board board, Box bestMove){

        Player nextPlayer = simulateOneTurn(board, bestMove);

        if(board.isFinished()){
            board.setNumberOfMoves(board.getNumberOfMoves()+1);
            //System.out.println("JE ME STOP PARCEQUE la simulation est TERMINEe");
            return;
        }
        try {
            Box nextPlayerBestMove = nextPlayer.getBestMove(board);
            nextPlayer.simulate(nextPlayerBestMove, board);
        } catch (Exception e) {
            System.out.println("Pas de best move, enfin ok");
            return;
        }
    }

    private Player simulateOneTurn(Board board, Box bestMove) {
        board.getBox(bestMove.getLine(), bestMove.getColumn()).setOwner(this);

        Player nextPlayer = board.getOtherPlayer(this);
        board.setCurrentPlayer(nextPlayer);
        board.setNumberOfMoves(board.getNumberOfMoves()+1);
        return nextPlayer;
    }

    private void simulate(Box place, Board board) throws Exception {
        //It should not play if the game is over
        if (board.isFinished()) {
            return;
        }
        this.execSimulation(board, place);

    }

    //todo : to be removed
/*
    private void cancel(Box place, Board board) throws Exception {
        board.getBox(place.getLine(), place.getColumn()).setOwner(null);
        board.setNumberOfMoves(board.getNumberOfMoves()- 1);
        board.setCurrentPlayer(this);
    }*/


/*
According to the state of the game, we try other moves to compute the better and the worth situations for us.

Precondition : The current player just plays or the game is over.
 */
    private CoupleMinMax minMax (Board simulationBoard, int depth) throws Exception {

        CoupleMinMax coupleMinMax = new CoupleMinMax(Integer.MAX_VALUE,Integer.MIN_VALUE);
        if(depth == 0 || simulationBoard.isFinished()) {
            int eval = evaluate(simulationBoard);
            coupleMinMax.setMax(eval);
            coupleMinMax.setMin(eval);
            return coupleMinMax;
        }

        coupleMinMax.setMin(Integer.MAX_VALUE);
        coupleMinMax.setMax(Integer.MIN_VALUE);

        //for all free boxes (potential moves)
        ArrayList<Box> freeBoxes = simulationBoard.getAllEmptyBox();
        Board savBoard = cloneBoardForSimulation(simulationBoard);
        //We only play for the other player.
        Player nextPlayer = simulationBoard.getOtherPlayer(this);
        for (Box box : freeBoxes){
            nextPlayer.simulate(box,simulationBoard );
            //nextPlayer.simulateOneTurn(simulationBoard,box);
            //The game should be over but minMax should be on the current player
            CoupleMinMax newCoupleMinMax = minMax(simulationBoard,depth-1);
            if (newCoupleMinMax.getMin() > coupleMinMax.getMax())
                coupleMinMax.setMax(newCoupleMinMax.getMin());
            if (newCoupleMinMax.getMax() < coupleMinMax.getMin())
                coupleMinMax.setMin(newCoupleMinMax.getMax());
            simulationBoard = savBoard;
        }
        return coupleMinMax;

    }
}