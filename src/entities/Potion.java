/*
* Potion entity class, subclass of the Item class
*/

package entities;

import java.util.ArrayList;

public class Potion extends Item {
    
    private final int healthChange;

    public Potion(String name, int healthChange, String description) {
        super(name, description);
        this.healthChange = healthChange;
        
        
    }

    public int getHealthChange() {
        return healthChange;
    }
    
    
    public void useItem(Character c){
        

        int characterHealth = c.getHealth();
        int characterMaxHealth = c.getHealthMaxSize();
        int newHealth = characterHealth + this.getHealthChange();
        if (newHealth > characterMaxHealth) {
            newHealth = characterMaxHealth;
        }
        c.setHealth(newHealth);
        c.removeFromPotionInventory(this);
        
    }
      
}
