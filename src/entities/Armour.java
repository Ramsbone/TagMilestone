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
    
   public ArrayList<String> useItem(Character c){
        ArrayList<String> output = new ArrayList<String>();
        output.add(this.getName());
        output.add(Integer.toString(this.getProtectionIncrease()));
        c.setArmour(this);
        output.add(Integer.toString(c.getDefaultProtection()));
        return output;
    }    
}
