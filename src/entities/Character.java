/*
 * Abstract class for Players and Non Playable Characters (NPC's)
 */
package entities;

import java.util.ArrayList;

public abstract class Character {
    
    private final String name;
    private final String description;
    private Room currentRoom;
    //private int gold;
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
    
    public Character(String name, Room currentRoom) {
        this.name = name;
        this.description = null;
        this.currentRoom = currentRoom;
        //this.gold = 0;
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
    
    public Character(String name, 
            Room currentRoom, 
            Weapon weapon, 
            Armour armour,
            String description) {
        this.name = name;
        this.description = description;
        this.currentRoom = currentRoom;
        //this.gold = 0;
        this.health = 100;
        this.healthMaxSize = 100;
        this.defaultDamage = 10;
        this.damage = defaultDamage;
        this.defaultProtection = 0;
        this.protection = defaultProtection;
        this.defaultInventorySize = 6;
        this.inventoryMaxSize = defaultInventorySize;
        
        //this.weapon = weapon;
        //this.addToInventory(weapon);
        int value = ((Weapon) weapon).getDamageIncrease();
        weapon.changeDamage(this, value);
        this.setWeapon((Weapon) weapon);
        
        //this.armour = armour;
        //this.addToInventory(armour);
        value = ((Armour) armour).getProtectionIncrease();
        armour.changeProtection(this, value);
        this.setArmour((Armour) armour);
        
        
        this.inventory = new ArrayList();
    }
    
    /**
     * checks if character inventory is empty
     */
    public boolean isInventoryEmpty() {
        return this.inventory.isEmpty();
    }
    
    /**
     * checks if character inventory exceeds the defined maximum
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
     * Checks if an item with a specific name is present in character inventory
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
