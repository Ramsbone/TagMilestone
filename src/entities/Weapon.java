/*
* Weapon entity class, subclass of the Item class
*/

package entities;

public class Weapon extends Item {

    private final int damageIncrease;

    public Weapon(String name, int damageIncrease, String description) {
        super(name, description);
        this.damageIncrease = damageIncrease;
    }

    public int getDamageIncrease() {
        return damageIncrease;
    } 
    
    
}
