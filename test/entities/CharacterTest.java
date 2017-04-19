/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author GertLehmann
 */
public class CharacterTest {
    
    Character tp1,tp2;
    
    public CharacterTest() {
    }
    
    @Before
    public void setUp() {
        tp1 = new Player("TestPerson", null);
        tp2 = new Player("TestPerson", null);
        tp2.setHealth(50);
        
    }

    @Test
    public void testPositiveValueSetHealthMaxSize() {
        System.out.println("setHealthMaxSize");
        int healthMaxSize = 110;
        Character instance = tp1;
        instance.setHealthMaxSize(healthMaxSize);
        int expResult = 110;
        int result = instance.getHealthMaxSize();
        assertEquals(expResult, result, 0.0);
        //fail("The test case is a prototype.");
    }


    @Test
    public void testNegativeValueSetHealthMaxSize() {
        System.out.println("setHealthMaxSize");
        int healthMaxSize = -10;
        Character instance = tp1;
        instance.setHealthMaxSize(healthMaxSize);
        int expResult = -10;
        int result = instance.getHealthMaxSize();
        assertEquals(expResult, result, 0.0);
        //fail("The test case is a prototype.");
    }


    @Test
    public void testZeroValueSetHealthMaxSize() {
        System.out.println("setHealthMaxSize");
        int healthMaxSize = 0;
        Character instance = tp1;
        instance.setHealthMaxSize(healthMaxSize);
        int expResult = 0;
        int result = instance.getHealthMaxSize();
        assertEquals(expResult, result, 0.0);
        //fail("The test case is a prototype.");
    }


    @Test
    public void testWhenNotMaxHealthSetHealthMaxSize() {
        System.out.println("setHealthMaxSize");
        int healthMaxSize = 110;
        Character instance = tp2;
        instance.setHealthMaxSize(healthMaxSize);
        int expResult = 50;
        int result = instance.getHealth();
        assertEquals(expResult, result, 0.0);
        //fail("The test case is a prototype.");
    }


    @Test
    public void testWhenMaxHealthSetHealthMaxSize() {
        System.out.println("setHealthMaxSize");
        int healthMaxSize = 110;
        Character instance = tp1;
        instance.setHealthMaxSize(healthMaxSize);
        int expResult = 110;
        int result = instance.getHealth();
        assertEquals(expResult, result, 0.0);
        //ail("The test case is a prototype.");
    }


    
//    @Test
//    public void testCheckForItem() {
//        System.out.println("checkForItem");
//        String name = "";
//        Character instance = null;
//        Item expResult = null;
//        Item result = instance.checkForItem(name);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }

    
}
