package fr.esiee.player;
import fr.esiee.Board;
import fr.esiee.Box;
import fr.esiee.core.CoupleMinMax;
import fr.esiee.strategy.Strategy;
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

        return Strategy.playRandom(board, this);

/*        //It should not play if the game is up
        if (board.isFinished()) {
            //todo : remove
            System.out.println("game is over ! ");
            return false;
        }

        int randomLine   = (int)(Math.random() * board.dimension());
        int randomColumn = (int)(Math.random() * board.dimension());
        if(board.getBox(randomLine, randomColumn).hasOwner()){
            return play(board);
        }else {
            board.play(randomLine, randomColumn);
        }
        return true;*/
    }





}
