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
    Potion pot = new Potion("TestPotion", 10,"Den er go'");
    Weapon wea = new Weapon("TestWeapon", 10, "Den er skarp");
    
    public CharacterTest() {
    }
    
    @Before
    public void setUp() {
        tp1 = new Player("TestPerson", null);
        tp2 = new Player("TestPerson", null);
        tp2.setHealth(50);
        tp2.addToInventory(wea);
        tp2.addToPotionInventory(pot);
        
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


    
    @Test
    public void testEmptyInventoriesCheckForItem() {
        String name = "TestWeapon";
        Character instance = tp1;
        Item expResult = null;
        Item result = instance.checkForItem(name);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }

    @Test
    public void testEmptyPotionInventoriesCheckForItem() {
        String name = "TestPotion";
        Character instance = tp1;
        Item expResult = null;
        Item result = instance.checkForItem(name);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }

    @Test
    public void testWithWeaponCheckForItem() {
        String name = "TestWeapon".toLowerCase();
        Character instance = tp2;
        Item expResult = wea;
        Item result = instance.checkForItem(name);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }

    @Test
    public void testWithPotionCheckForItem() {
        String name = "TestPotion".toLowerCase();
        Character instance = tp2;
        Item expResult = pot;
        Item result = instance.checkForItem(name);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }

    
}
