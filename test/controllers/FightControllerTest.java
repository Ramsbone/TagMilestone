/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Character;
import entities.Player;
import entities.Room;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author GertLehmann
 */
public class FightControllerTest {
    
    Player tp1,tp2;
    Room r1,r2;
    
    public FightControllerTest() {
    }
    
    @Before
    public void setUp() {
        r1 = new Room("TestRoom1","",0);
        r2 = new Room("TestRoom2","",0);
        r1.setDoors(r2, null, null, null);

        tp1 = new Player("TestPerson1",r1);
        tp2 = new Player("TestPerson2",r1);
        
        tp1.setMoveDirection("s");
        tp2.setMoveDirection("w");
        
    }


    @Test
    public void testPositiveHit() {
        int damage = 50;
        Character character = tp1;
        FightController instance = new FightController();
        instance.hit(damage, character);
        int expResult = 50;
        int result = character.getHealth();
        assertEquals(expResult, result, 0.0);
        //fail("The test case is a prototype.");
    }


    @Test
    public void testNegativeHit() {
        int damage = -50;
        Character character = tp1;
        FightController instance = new FightController();
        instance.hit(damage, character);
        int expResult = 150;
        int result = character.getHealth();
        assertEquals(expResult, result, 0.0);
        //fail("The test case is a prototype.");
    }

    
    @Test
    public void testZeroHit() {
        int damage = 0;
        Character character = tp1;
        FightController instance = new FightController();
        instance.hit(damage, character);
        int expResult = 100;
        int result = character.getHealth();
        assertEquals(expResult, result, 0.0);
        //fail("The test case is a prototype.");
    }

//    @Test
//    public void testIsValidCommand() {
//        System.out.println("isValidCommand");
//        FightController instance = new FightController();
//        String expResult = "";
//        String result = instance.isValidCommand();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }

//    @Test
//    public void testWordReplacer() {
//        System.out.println("wordReplacer");
//        String input = "";
//        FightController instance = new FightController();
//        String expResult = "";
//        String result = instance.wordReplacer(input);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }


    @Test
    public void testPossibleFlee() {
        Player player = tp1;
        FightController instance = new FightController();
        boolean expResult = true;
        boolean result = instance.flee(player);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }

    @Test
    public void testNotPossibleFlee() {
        Player player = tp2;
        FightController instance = new FightController();
        boolean expResult = false;
        boolean result = instance.flee(player);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    

    
}
