package fr.esiee;

import fr.esiee.player.Person;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestAlignment {

    @Test
    public void verifyEarnedByPlayerFirstBox() throws Exception {

        Alignment alignment = new Alignment(3);
        Person player = new Person("UPerosn", null);
        alignment.addAll(new Box().setOwner(player), new Box().setOwner(player), new Box().setOwner(player));
        boolean condition = alignment.earnedBy(player);
        assertTrue("Error occured - earnedByPlayer : FALSE", condition);
    }
    @Test
    public void verifyEarnedByPlayerLasttBox() throws Exception {

        Alignment alignment = new Alignment(3);
        Person player = new Person("Person", null);
        alignment.addAll(new Box(), new Box(), new Box().setOwner(player), new Box().setOwner(player), new Box().setOwner(player));
        boolean condition = alignment.earnedBy(player);
        assertTrue("Arg, j'ai pas gagné", condition);
    }
}
