package fr.esiee;

import fr.esiee.player.Player;

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
    private int winningNumber;

    /**
     * Default Constructor
     * Create a new ArrayList for the {@link Alignment#boxes}
     * Initialize the {@link Alignment#winningNumber} at 1 (default value)
     */
    public Alignment() {
        this(new ArrayList<>(), 1);
    }

    /**
     * Constructor
     * Create a new ArrayList for the {@link Alignment#boxes}
     * @param winningNumber The number of points needed to make the lineup win  {@link Alignment#winningNumber}
     */
    public Alignment(int winningNumber) {
        this(new ArrayList<>(), winningNumber);
    }

    /**
     * Constrcuteur (Private)
     * @param boxes The arraylist containing the boxes
     * @param winningNumber The number of points needed to make the lineup win {@link Alignment#winningNumber}
     */
    private Alignment(ArrayList<Box> boxes, int winningNumber) {
        this.boxes = boxes;
        this.winningNumber = winningNumber;
    }
    /**
     * Select one box
     * @param i The position of the box
     * @return The box if she exist, null else
     */
    public Box getBox(int i){
        return this.getBoxes().get(i);
    }

    /**
     * Get the ArrayList with all the boxes
     * @return An arraylist with all the boxes contained in the "Alignment"
     */
    public ArrayList<Box> getBoxes() {
        return this.boxes;
    }

    /**
     * Add a box in the Alignement
     * @param box The box to add in the lignement
     * @return Current object (this)
     */
    public Alignment add(Box box){
        this.boxes.add(box);
        return this;
    }
    public Alignment addAll(Box... boxes){
        for(Box box : boxes){
            this.add(box);
        }
        return this;
    }
    /**
     * To know if a player "have win" an alignment
     * @param player The player
     * @return TRUE if the player (param) have win, else false
     */
    public boolean earnedBy(Player player){
        return earnedBy() == player;
    }

    /**
     * Get the player who win the alignment
     * @return The player if someone have win, else null
     */
    public Player earnedBy(){
        int currentPlayerBoxNumber = 1;
        Player lastPlayer = this.getBox(0).getOwner();
        for(int i = 1; i < this.size(); i++){
            final Box box = this.getBox(i);
            if(box.haveOwner()) {
                Player owner = box.getOwner();
                if (!owner.equals(lastPlayer)) currentPlayerBoxNumber = 1;
                else {
                    currentPlayerBoxNumber++;
                    if(currentPlayerBoxNumber >= this.winningNumber) return owner;
                }
                lastPlayer = owner;
            }
        }
        return null;
    }

    /**
     * Return the size of the alignement
     * @return the size of the alignement (int)
     */
    public int size(){
        return this.getBoxes().size();
    }

    /**
     * Compares the specified object with this alignment for equality.
     * @param o The object to compare
     * @return true if the object are the sime, else false
     */
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

    @Override
    public String toString() {
        return "Alignment{" +
                "boxes=" + boxes.toString() +
                ", winningNumber=" + winningNumber +
                '}';
    }
}
