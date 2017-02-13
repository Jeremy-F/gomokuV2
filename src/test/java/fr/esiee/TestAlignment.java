package fr.esiee;

import fr.esiee.player.Person;
import fr.esiee.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
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
public class TestAlignment {

    Alignment alignment;
    Person player ;


    @Before
    public void setUp() throws Exception {
        alignment = new Alignment(5);
        player = new Person("You", null);
        alignment.addAll(new Box().setOwner(player), new Box().setOwner(player), new Box(), new Box().setOwner(player),new Box());
    }

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


    //to do : IMPROVE
    @Test
    public void countNbrOfBoxAligneByAtBeginning(){
        int res = alignment.countNbrOfBoxAligneBy(player,2);
        assertEquals("EXpected 1 alignmenet", 1, res);
    }
    @Test
    public void countNbrOfBoxAligneByAtEnd(){
        alignment = new Alignment(5);
        alignment.addAll(new Box().setOwner(player),
                         new Box(), new Box(),
                         new Box().setOwner(player),new Box().setOwner(player));
        int res = alignment.countNbrOfBoxAligneBy(player,2);
        assertEquals("EXpected 1 alignmenet", 1, res);
    }
    @Test
    public void countNbrOfBoxAligneAtTheMiddle(){
        alignment = new Alignment(5);
        alignment.addAll(new Box(),  new Box().setOwner(player),   new Box().setOwner(player),new Box(),new Box());
        int res = alignment.countNbrOfBoxAligneBy(player,2);
        assertEquals("EXpected 1 alignmenet", 1, res);
    }

    @Test
    public void countNbrOfBoxAlignedAndSharingABox(){
        alignment = new Alignment(5);
        alignment.addAll(new Box(),  new Box().setOwner(player),   new Box().setOwner(player),new Box().setOwner(player),new Box());
        int res = alignment.countNbrOfBoxAligneBy(player,2);
        assertEquals("EXpected 2 alignmenets", 2, res);
    }

    @Test
    public void countNbrOfBoxAligneTwice(){
        alignment = new Alignment(5);
        alignment.addAll(new Box().setOwner(player), new Box().setOwner(player),  new Box(), new Box().setOwner(player), new Box().setOwner(player));
        int res = alignment.countNbrOfBoxAligneBy(player,2);
        assertEquals("EXpected 2 alignmenets", 2, res);
    }
}
