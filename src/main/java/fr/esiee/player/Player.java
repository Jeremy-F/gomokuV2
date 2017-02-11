package fr.esiee.player;
import fr.esiee.Board;
import javafx.scene.paint.Color;
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

}