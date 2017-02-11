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
    public IA(String name, Color color) {
        super(name, color);
    }

    /*public int eval(){
        if(this.getBoard().isFinished()){
            if(this.haveWin()){
                return 1000;
            }else if(this.wonByOtherPlayer()){
                return -100;
            }else{
                return 0;
            }
        }
        return -3;
    }*/

    @Override
    public boolean play(Board board) {
        int randomLine   = (int)(Math.random() * board.dimension());
        int randomColumn = (int)(Math.random() * board.dimension());
        if(board.getBox(randomLine, randomColumn).haveOwner()){
            return play(board);
        }else {
            board.play(randomLine, randomColumn);
        }
        return true;
    }
}
