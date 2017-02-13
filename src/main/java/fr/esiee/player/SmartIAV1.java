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
public class SmartIAV1 extends IA {

    public SmartIAV1(String name, Color color) {
        super(name, color);
    }
    @Override
    public boolean play(Board board) {
        try {
            return playSmart0(board);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
