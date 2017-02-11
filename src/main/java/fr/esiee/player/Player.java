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
            //todo : remove
            //System.out.println("you win");
        }
		else
		    if (wonByOtherPlayerOn(board)) {
                points = MIN_POINT + points;
                //todo : remove
                //System.out.println("you lost");
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
            board.play(bestMove.getLine(), bestMove.getColumn());
        } catch (Exception e) {
            System.out.println("playSmart0 : Pas de bestMove possible");
            return false;
        }
        return true;
    }

    private Box getBestMove(Board simulationBoard) throws Exception {

        int maxValue = Integer.MIN_VALUE;

        ArrayList<Box> freeBoxes = simulationBoard.getAllEmptyBox();
        Box bestMove = freeBoxes.get(0);
//        System.out.println("-------Computing bestMove for " + this);
        Board savBoard = cloneBoardForSimulation(simulationBoard);
        for (Box box : freeBoxes) {
            //simulate(box,simulationBoard);
            simulateOneTurn(simulationBoard,box);
            CoupleMinMax coupleMinMax = minMax(simulationBoard, DEPTH);
            if (coupleMinMax.getMin() > maxValue) {
                maxValue = coupleMinMax.getMin();
                bestMove = box;
            }

//            System.out.println(box + "is evaluated as " + coupleMinMax);
            //cancel(box,simulationBoard);
            simulationBoard = savBoard;
        }
//        System.out.println("-------End Computing bestMove for " + this + "results in " + bestMove);
        return bestMove;
    }

    private Board cloneBoardForSimulation(Board board) {
        Board simulationBoard = board.clone();
        Player ennemy = simulationBoard.getNextPlayer();
       // System.out.println("Ennem u : " + ennemy + "===" + this);
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
        return simulationBoard;
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
        //System.out.println("--------------- On simule pour : "+ this);
        //It should not play if the game is over
        if (board.isFinished()) {
            return;
        }
       // System.out.println("--------------- Pour "+ this + "il simule en : " + place);
        this.execSimulation(board, place);

    }


    private void cancel(Box place, Board board) throws Exception {
        board.getBox(place.getLine(), place.getColumn()).setOwner(null);
        board.setNumberOfMoves(board.getNumberOfMoves()- 1);
        board.setCurrentPlayer(this);
    }


    public CoupleMinMax minMax (Board simulationBoard, int depth) throws Exception {
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
       // Board simulationBoard = cloneBoardForSimulation(simulationBoard);
        Board savBoard = cloneBoardForSimulation(simulationBoard);
        for (Box box : freeBoxes){
            Player nextPlayer = simulationBoard.getOtherPlayer(this);
            nextPlayer.simulate(box,simulationBoard );
            //simulateOneTurn(simulationBoard,box);
            CoupleMinMax newCoupleMinMax = minMax(simulationBoard,depth-1);
            if (newCoupleMinMax.getMin() > coupleMinMax.getMax())
                coupleMinMax.setMax(newCoupleMinMax.getMin());
            if (newCoupleMinMax.getMax() < coupleMinMax.getMin())
                coupleMinMax.setMin(newCoupleMinMax.getMax());
            //cancel(box,simulationBoard);
            simulationBoard = savBoard;
        }
        return coupleMinMax;

    }
}