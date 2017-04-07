/*
* Armour entity class, subclass of the Item class
*/

package entities;

import java.util.ArrayList;

public class Armour extends Item {
    
    private final int protectionIncrease;

    public Armour(String name, int protectionIncrease, String description) {
        super(name, description);
        this.protectionIncrease = protectionIncrease;
    }
  
    public int getProtectionIncrease() {
        return protectionIncrease;
    }
   
}
