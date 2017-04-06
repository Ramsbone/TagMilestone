/*
 * Abstract class for Players and Non Playable Characters (NPC's)
 */
package entities;

import java.util.ArrayList;

public abstract class Character {
    
    private String name;
    private int gold;
    private int health;
    private int healthMaxSize;
    private int damage;
    private int defaultDamage;
    private int protection;
    private int defaultProtection;
    private Weapon weapon;
    private Armour armour;
    public ArrayList<Item> inventory;
    public ArrayList<Item> potionInventory;
    
    public Character(String name, Room currentRoom) {
        this.name = name;
        this.gold = 0;
        this.health = 100;
        this.healthMaxSize = 100;
        this.defaultDamage = 10;
        this.damage = defaultDamage;
        this.defaultProtection = 0;
        this.protection = defaultProtection;
        this.weapon = null;
        this.armour = null;
        this.inventory = new ArrayList();
        this.potionInventory = new ArrayList();

    }
    
    public Character(String name, 
            String description, int startHealth, int startDamage, int startProtection, int startGold) {
        this.name = name;
        this.gold = startGold;
        this.health = startHealth;
        this.healthMaxSize = startHealth;
        this.defaultDamage = startDamage;
        this.damage = defaultDamage;
        this.defaultProtection = startProtection;
        this.protection = defaultProtection;
        this.inventory = new ArrayList();
        this.potionInventory = new ArrayList();
        
    }
    
    
    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
    
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public ArrayList<Item> getPotionInventory() {
        return potionInventory;
    }

    public int getHealthMaxSize() {
        return healthMaxSize;
    }

    public void setHealthMaxSize(int healthMaxSize) {
        int oldMax = this.healthMaxSize;
        this.healthMaxSize = healthMaxSize;
        if(this.health == oldMax){
            this.health = this.healthMaxSize;
        }
        
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
        //this.weapon = weapon;
        //this.addToInventory(weapon);
        if (this.getWeapon() != null) {
            this.setDamage(this.getDamage()-this.getWeapon().getDamageIncrease());
        }
        int value = ((Weapon) weapon).getDamageIncrease();
        weapon.changeDamage(this, value);
        this.weapon = weapon;
        if (!this.inventory.contains(weapon)){
            this.addToInventory((Weapon)weapon);
        }
    }

    public void setArmour(Armour armour) {
        //this.armour = armour;
        //this.addToInventory(armour);
        if (this.getArmour() != null) {
            this.setProtection(this.getProtection()-this.getArmour().getProtectionIncrease());
        }
        int value = ((Armour) armour).getProtectionIncrease();
        armour.changeProtection(this, value);
        this.armour = armour;
        if (!this.inventory.contains(armour)){
            this.addToInventory((Armour)armour);
        }
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

    public void addToInventory(Item i) {
        inventory.add(i);
    }

    public void removeFromInventory(Item i) {
        inventory.remove(i);
    }

            
    public void addToPotionInventory(Item i) {
        potionInventory.add(i);
    }

    public void removeFromPotionInventory(Item i) {
        potionInventory.remove(i);
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

        for (Item item : potionInventory) {
            String check = item.getName().replaceAll(" ", "");
            if (name.equals(check.toLowerCase())) {
                output = item;
            }
        }

        return output;
    }
}
