package fr.esiee;
import fr.esiee.player.Player;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
public class Box {

	private SimpleObjectProperty<Player> owner;

	public Box() {
		this.owner = new SimpleObjectProperty<>();
	}

    /**
     * Verify if the Box have a owner
     * @return boolean true if have owner, else false
     */
	public boolean haveOwner(){
		return this.owner.get() != null;
	}

    /**
     * Create a circle for the box
     * @return A Node wich contain the representation of the box
     */
	public Node toNode(){

	    Circle circle = new Circle();

        circle.setRadius(40);

        if(haveOwner()){
            circle.setStroke(Color.BLACK); // black border
            circle.setFill(this.owner.get().getColor());  // player color for the circle color
        }else{
		    circle.setFill(Color.WHITE); // By default, the circle is white
        }


		return circle;//*/
	}

    public Box setOwner(Player owner) {
        this.owner.setValue(owner);
        return this;
    }

    /**
     *
     * @return The owner {@link Player}
     */
    public Player getOwner() {
        return owner.get();
    }

    /**
     *
     * @return The ower property
     */
    public SimpleObjectProperty<Player> ownerProperty() {
        return owner;
    }

    @Override
    public String toString() {
        return "Box{" +
                "owner=" + getOwner() +
                '}';
    }
}