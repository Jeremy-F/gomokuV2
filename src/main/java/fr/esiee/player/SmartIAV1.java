package fr.esiee.player;

import fr.esiee.Board;
import javafx.scene.paint.Color;

/**
 * Created by blay on 11/02/2017.
 * Modified by blay on 11/02/2017.
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
