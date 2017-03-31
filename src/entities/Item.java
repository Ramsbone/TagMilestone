/*
* Item entity class, is a superclass for all item-type classes. 
*/

package entities;

import exceptions.InitiationException;

public abstract class Item {
    private final String name;
    private final String description;
    
    public Item(String name, String description){
        this.name = name;
        this.description = description;
        validateName();
    }

    public void changeHealth(Player player, int value) {
        int sum = value + player.getHealth();
        if (sum > player.getHealthMaxSize()) {
            sum = player.getHealthMaxSize();
        }
        player.setHealth(sum);
    }
    
    public void validateName(){
        if (this.getName().split(" ").length > 2) {
            throw new InitiationException("class: Items: Invalid name - max 2 words!");
        }
    }
    
    
    //Override i sub - så anvendes ikke.
    public String getName() {
        return name;
    }
    //Override i sub - så anvendes ikke.
    public String getDescription(){
        return this.description;
    }
    
    public void changeDamage(Player player, int value) {
        player.setDamage(player.getDefaultDamage() + value);
    }
  
    public void changeProtection(Player player, int value) {
        player.setProtection(player.getDefaultProtection() + value);
    }
    
    
    public void pickUpItem(Player player) {
        player.addToInventory(this);
        player.getCurrentRoom().removeFromInventory(this);
    }
    
    public void DropItem(Player player) {
        player.getCurrentRoom().addToInventory(this);
        player.removeFromInventory(this);
    } 
 
    
}
