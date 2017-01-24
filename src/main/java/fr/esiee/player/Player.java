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
	private Board board;

	private Player(String name, Color color) {
		this(name, color,null);
	}

	public Player(String name, Color color, Board board) {
		this.name = name;
		this.color = color;
		this.board = board;
	}

	public Board getBoard() {
		return this.board;
	}
	abstract public boolean play();

	public Color getColor() {
		return this.color;
	}

	@Override
	public String toString() {
		return "Player{" +
				"name='" + name.toString() + '\'' +
				", color=" + color.toString() +
				", board=" + board.toString() +
				'}';
	}
}