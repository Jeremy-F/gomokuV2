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
public class IA extends Player{

    public IA(String name, Color color, Board board) {
        super(name, color, board);
    }

    @Override
    public boolean play() {
        int randomLine   = (int)(Math.random() * this.getBoard().getBoxes().size());
        int randomColumn = (int)(Math.random() * this.getBoard().getBoxes().size());
        if(this.getBoard().getBox(randomLine, randomColumn).haveOwner()){
            return play();
        }else {
            this.getBoard().play(randomLine, randomColumn);
        }
        return true;
    }
}
