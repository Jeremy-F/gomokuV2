package fr.esiee;

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
public class Alignment {
    private ArrayList<Box> boxes;
    public Alignment() {
        this.boxes = new ArrayList<>();
    }

    public Box getBox(int i){
        return this.getBoxes().get(i);
    }

    public ArrayList<Box> getBoxes() {
        return this.boxes;
    }

    public Alignment add(Box box){
        this.boxes.add(box);
        return this;
    }

    public int size(){
        return this.getBoxes().size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alignment alignment = (Alignment) o;
        for(int i = 0; i < this.getBoxes().size(); i++){
            if(this.getBox(i).equals(alignment.getBox(i))){
                return false;
            }
        }
        return true;
    }
}
