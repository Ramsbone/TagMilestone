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
    
    
    public ArrayList<String> useItem(Character c){
                
        ArrayList<String> output = new ArrayList<String>();
        output.add(this.getName());
        output.add(Integer.toString(this.getHealthChange()));
        
        
        return output;
    }
      
}
