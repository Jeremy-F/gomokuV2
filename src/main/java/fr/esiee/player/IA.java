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


    @Override
    public boolean play(Board board) {

        return Strategy.playRandom(board, this);
    }



}
