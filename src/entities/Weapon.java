/*
* Weapon entity class, subclass of the Item class
*/

package entities;

import java.util.ArrayList;

public class Weapon extends Item {

    private final int damageIncrease;

    public Weapon(String name, int damageIncrease, String description) {
        super(name, description);
        this.damageIncrease = damageIncrease;
    }

    public int getDamageIncrease() {
        return damageIncrease;
    } 
//    
//    public Item use(Player p, Item item){   
//        p.setWeapon((Weapon) item);
//        return (Weapon) item;
//    }
    public ArrayList<String> useItem(Character c){
        ArrayList<String> output = new ArrayList<String>();
        output.add(this.getName());
        output.add(Integer.toString(this.getDamageIncrease()));
        c.setWeapon(this);
        output.add(Integer.toString(c.getDefaultDamage()));
        return output;
    }
    
}
