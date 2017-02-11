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
    private static int DEPTH = 5;

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
            System.out.println("you win");
        }
		else
		    if (wonByOtherPlayerOn(board)) {
                points = MIN_POINT + points;
                //todo : remove
                System.out.println("you lost");
                }
            else
                return AVERAGE_POINT;

		return points;
	}


    public boolean playSmart0(Board board) throws Exception {
        if (board.isFinished()) {
            //todo : remove
            System.out.println("game is over ! ");
            return false;
        }

        Box bestMove = null;
        int maxValue = Integer.MIN_VALUE;
        ArrayList<SimpleObjectProperty<Box>> freeBoxes = board.getFreeBoxes();
        for (SimpleObjectProperty<Box> box : freeBoxes) {
            Board simulationBoard = board;
            simulate(box,simulationBoard );
            CoupleMinMax coupleMinMax = minMax(simulationBoard, DEPTH);
            if (coupleMinMax.getMin() > maxValue) {
                maxValue = coupleMinMax.getMin();
                bestMove = box.get();
            }
            cancel(box,simulationBoard);
        }
        //todo :improve exception
        if (bestMove == null)
            throw new Exception("Unexpected  inability to choose a move");

        board.play(bestMove.getLine(), bestMove.getColumn());
        return true;
    }

    private void simulate(SimpleObjectProperty<Box> box, Board board) throws Exception {
        Box place = box.get();
        //It should not play if the game is over
        if (board.isFinished()) {
            //todo : remove line and improve exception
            System.out.println("game is over ! ");
            throw new Exception("Unexpected call to simulate : the game is over");
        }
        board.play(place.getLine(), place.getColumn());
    }

    private void cancel(SimpleObjectProperty<Box> box, Board board) throws Exception {
        Box place = box.get();
        //It should not play if the game is over
        if (board.isFinished()) {
            //todo : remove line and improve exception
            System.out.println("game is over ! ");
            throw new Exception("Unexpected call to simulate : the game is over");
        }
        //board.play(place.getLine(), place.getColumn());
        board.getBox(place.getLine(), place.getColumn()).setOwner(null);
        //todo : number of moves has to be reduced

    }


    public CoupleMinMax minMax (Board board, int depth) throws Exception {
        CoupleMinMax coupleMinMax = new CoupleMinMax(0,0);
        if(depth == 0 || board.isFinished())
        {
            int eval = evaluate(board);
            coupleMinMax.setMax(eval);
            coupleMinMax.setMin(eval);
            return coupleMinMax;
        }

        coupleMinMax.setMin(Integer.MAX_VALUE);
        coupleMinMax.setMax(Integer.MIN_VALUE);
        //for all free boxes (potential moves)
        ArrayList<SimpleObjectProperty<Box>> freeBoxes = board.getFreeBoxes();
        for (SimpleObjectProperty<Box> box : freeBoxes){
            Board simulationBoard = board;
            simulate(box,simulationBoard );
            CoupleMinMax newCoupleMinMax = minMax(simulationBoard,depth-1);
            if (newCoupleMinMax.getMin() > coupleMinMax.getMax())
                coupleMinMax.setMax(newCoupleMinMax.getMin());
            if (newCoupleMinMax.getMax() < coupleMinMax.getMin())
                coupleMinMax.setMin(newCoupleMinMax.getMax());
            cancel(box,simulationBoard);
        }
        return coupleMinMax;

    }
}