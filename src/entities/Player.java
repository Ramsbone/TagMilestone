/*
* Player entity class, which hold the data of the player.
 */
package entities;

import java.util.ArrayList;

/*
Her er Christian: jeg skal nu lave switchen om. kl. er 11.42

*/

public class Player {

    private final String name;
    private Room currentRoom;
    private int gold;
    private int health;
    private int healthMaxSize;
    private int damage;
    private int defaultDamage;
    private int protection;
    private int defaultProtection;
    private int defaultInventorySize;
    private int inventoryMaxSize;
    private Weapon weapon;
    private Armour armour;
    private ArrayList<Item> inventory;

    public Player(String name, Room currentRoom) {
        this.name = name;
        this.currentRoom = currentRoom;
        this.gold = 0;
        this.health = 100;
        this.healthMaxSize = 100;
        this.defaultDamage = 10;
        this.damage = defaultDamage;
        this.defaultProtection = 0;
        this.protection = defaultProtection;
        this.defaultInventorySize = 6;
        this.inventoryMaxSize = defaultInventorySize;
        this.weapon = null;
        this.armour = null;
        this.inventory = new ArrayList();
    }
    
    /**
     * checks if player inventory is empty
     */
    public boolean isInventoryEmpty() {
        return this.inventory.isEmpty();
    }
    
    /**
     * checks if player inventory exceeds the defined maximum
     */
    public boolean isInventoryFull() {
        return (this.inventory.size() == inventoryMaxSize);
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public int getHealthMaxSize() {
        return healthMaxSize;
    }

    public void setHealthMaxSize(int healthMaxSize) {
        this.healthMaxSize = healthMaxSize;
    }

    public int getDefaultDamage() {
        return defaultDamage;
    }

    public void setDefaultDamage(int defaultDamage) {
        this.defaultDamage = defaultDamage;
    }

    public int getDefaultProtection() {
        return defaultProtection;
    }

    public void setDefaultProtection(int defaultProtection) {
        this.defaultProtection = defaultProtection;
    }

    public int getInventoryMaxSize() {
        return inventoryMaxSize;
    }

    public void setInventoryMaxSize(int inventoryMaxSize) {
        this.inventoryMaxSize = inventoryMaxSize;
    }

    
    public Weapon getWeapon() {
        return weapon;
    }

    public Armour getArmour() {
        return armour;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getProtection() {
        return protection;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setArmour(Armour armour) {
        this.armour = armour;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setProtection(int protection) {
        this.protection = protection;
    }

    public String getName() {
        return name;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void addToInventory(Item i) {
        inventory.add(i);
    }

    public void removeFromInventory(Item i) {
        inventory.remove(i);
    }

     /**
     * Checks if an item with a specific name is present in player inventory
     * 
     * @param name the name searched for in inventory
     * @return if Item with name is found then returns the item, else null is returned
     */
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
