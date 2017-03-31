/*
* Potion entity class, subclass of the Item class
*/

package entities;

public class Potion extends Item {
    
    private final int healthChange;

    public Potion(String name, int healthChange, String description) {
        super(name, description);
        this.healthChange = healthChange;
    }

    public int getHealthChange() {
        return healthChange;
    }
    
      
}
