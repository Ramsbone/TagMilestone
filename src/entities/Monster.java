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
            String description,
            int startHealth,
            int startDamage,
            int startProtection,
            int startGold
            ) {
        
        super(name, description, startHealth, startDamage, startProtection, startGold);
        this.description = description;
        
    }
    
    public String getDescription(){
        return description;
    }
    
    public int monsterDies(Player p){     //removes the first mnster in the list
        Room currentRoom = p.getCurrentRoom();
        int monsterGold = this.getGold();
        currentRoom.setGold(currentRoom.getGold() + monsterGold);
        for (Item i: this.inventory){
            currentRoom.addToInventory(i);
        }
        this.setGold(0);
        this.inventory.clear();
        p.getCurrentRoom().getMonsterList().remove(this);
    return monsterGold;
    }
   
}
