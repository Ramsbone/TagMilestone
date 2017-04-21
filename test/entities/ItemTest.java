/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import exceptions.InitiationException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lene_
 */
public class ItemTest {

    Room r1, r2;
    Weapon badName, w1;
    Armour goodName;
    Potion potion;
    Player p1, p2, p3;

    public ItemTest() {
    }

    @Before
    public void setUp() {
        potion = new Potion("drink", 10, "");
        w1 = new Weapon("testweapon", 0, "");
        r1 = new Room("testroom1", "", 0);
        r2 = new Room("testroom2", "", 0);
        r1.addToInventory(potion);
        r1.addToInventory(w1);

        p1 = new Player("Player1", r1);
        p2 = new Player("Player2", r2);
        p3 = new Player("Player3", r2);
        p2.setHealth(80);
        p3.setHealth(98);

        p2.addToInventory(w1);
        p3.addToPotionInventory(potion);

    }

    @Test
    public void testbadNameValidateName() {
        try {
            badName = new Weapon("to long name", 0, "");
            fail();
        } catch (InitiationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGoodNameValidateName() {

        try {
            goodName = new Armour("shield", 0, "");
            assertTrue(true);
        } catch (InitiationException e) {
            fail();
        }
    }

    @Test
    public void testFullHealthChangeHealth() {
        Player player = p1;
        int value = 10;
        Item instance = potion;
        instance.changeHealth(player, value);
        int expResult = 100;
        int result = p1.getHealth();
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testnotFullHealthChangeHealth() {
        Player player = p2;
        int value = 10;
        Item instance = potion;
        instance.changeHealth(player, value);
        int expResult = 90;
        int result = p2.getHealth();
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testAlmostFullHealthChangeHealth() {
        Player player = p3;
        int value = 10;
        Item instance = potion;
        instance.changeHealth(player, value);
        int expResult = 100;
        int result = p3.getHealth();
        assertEquals(expResult, result, 0.0);

    }

    @Test
    public void testPotionEmptyPickUpItem() {
        Player player = p1;
        Item instance = potion;
        instance.pickUpItem(player);
        boolean expected = false;
        boolean result = p1.isPotionInventoryEmpty();
        assertTrue("Expected: " + expected + "    Found: " + result, result == expected);
    }

    @Test
    public void testWeaponEmptyPickUpItem() {
        Player player = p1;
        Item instance = w1;
        instance.pickUpItem(player);
        boolean expected = false;
        boolean result = p1.isInventoryEmpty();
        assertTrue("Expected: " + expected + "    Found: " + result, result == expected);
    }

    @Test
    public void testWeaponDropItem() {
        Player player = p2;
        Item instance = w1;
        instance.dropItem(player);
        boolean expected = true;
        boolean result = p1.isInventoryEmpty();
        assertTrue("Expected: " + expected + "    Found: " + result, result == expected);

    }

    @Test
    public void testPotionDropItem() {
        Player player = p3;
        Item instance = potion;
        instance.dropItem(player);
        boolean expected = true;
        boolean result = p1.isInventoryEmpty();
        assertTrue("Expected: " + expected + "    Found: " + result, result == expected);

    }

}
