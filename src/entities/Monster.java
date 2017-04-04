/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;

/**
 *
 * @author Christian
 */
public class Monster extends Character{
    
    private final String description;
    
    public Monster(String name, 
            Weapon weapon, 
            Armour armour,
            String description,
            ArrayList<Item> itemsForMonster) {
        super(name, weapon, armour, description);
        this.description = description;
        
        for (Item i: itemsForMonster){
            this.addToInventory(i);
        }
    }
    
    
    public void monsterDies(Player p){     //removes the first mnster in the list
        Room currentRoom = p.getCurrentRoom();
        currentRoom.setGold(currentRoom.getGold() + this.getGold());
        for (Item i: this.inventory){
            currentRoom.addToInventory(i);
        }
        this.setGold(0);
        this.inventory.clear();
        p.getCurrentRoom().getMonsterList().remove(this);
    }
    
    
    
}
