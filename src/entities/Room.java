/*
* Room entity class, holds all data of the rooms, including references to neighbor
*/

package entities;

import java.util.ArrayList;
import java.util.Random;

public class Room {
    
    private final String name;
    private final String description;
    private Room north;
    private Room south;
    private Room east;
    private Room west;
    private int gold;
    private final int goldchance;
    private boolean boobytrap;
    private boolean taxcollector;
    private boolean thief;
    private ArrayList<Item> inventory;


    public Room(String name, String description, int goldchance) {
        this.name = name;
        this.description = description;
        this.goldchance = goldchance;
        this.north = null;
        this.south = null;
        this.east = null;
        this.west = null;
        this.boobytrap = false;
        this.taxcollector = false;
        this.thief = false;
        this.inventory = new ArrayList();
        
        //the following code makes the amount of gold different in each room.
        //the amount is weighted based on "goldchance".
        Random r = new Random();
        int myran = r.nextInt((5 * goldchance)+1);           
        if (myran < goldchance && goldchance < 10 ) {
            myran = 0;
        }
        this.gold = myran;
        
    }
    
    public boolean isInventoryEmpty(){
        return this.inventory.isEmpty();
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }
    
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Room getNorth() {
        return north;
    }

    public Room getSouth() {
        return south;
    }

    public Room getEast() {
        return east;
    }

    public Room getWest() {
        return west;
    }

    public int getGold() {
        return gold;
    }

//    public void setDescription(String description) {
//        this.description = description;
//    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getGoldchance() {
        return goldchance;
    }

//    public void setGoldchance(int goldchance) {
//        this.goldchance = goldchance;
//    }
    
    //used to initialize rooms when creating the structure of the map.
    public void setDoors(Room north, Room south, Room east, Room west){
        this.north = north;
        this.south = south;
        this.east  = east;
        this.west = west;
    }

    public void setBoobytrap(boolean boobytrap) {
        this.boobytrap = boobytrap;
    }

    public boolean isBoobytrap() {
        return boobytrap;
    }

    public void setTaxcollector(boolean taxcollector) {
        this.taxcollector = taxcollector;
    }

    public boolean isTaxcollector() {
        return taxcollector;
    }

    public void setThief(boolean thief) {
        this.thief = thief;
    }

    public boolean isThief() {
        return thief;
    }
    
    public void addToInventory(Item i) {
        inventory.add(i);
    }
    
    public void removeFromInventory(Item i) {
        inventory.remove(i);
    }
    
    public Item checkForItem(String name) {
        Item output = null;

        for (Item item : inventory) {
            String check = item.getName().replaceAll(" ", "");
            if (name.equals(check.toLowerCase())) {
                output = item;
            }
        }

        return output;
    }
    
}
