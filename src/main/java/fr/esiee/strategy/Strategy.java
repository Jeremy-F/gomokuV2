package fr.esiee.strategy;

import fr.esiee.Board;
import fr.esiee.player.Player;

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
public class Strategy {



    public static boolean playRandom(Board board, Player player) {
        int randomLine = (int) (Math.random() * board.dimension());
        int randomColumn = (int) (Math.random() * board.dimension());
        if (board.getBox(randomLine, randomColumn).hasOwner()) {
            return Strategy.playRandom(board,player);
        } else {
            board.play(randomLine, randomColumn);
        }
        return true;
    }

}