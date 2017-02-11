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
}