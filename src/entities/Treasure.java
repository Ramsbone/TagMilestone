/*
* Treasure entity class, subclass of the Item class
*/
package entities;

public class Treasure extends Item {
    
    private int value;
    
    public Treasure(String name, String description, int value){
        super(name, description);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
