/*
* Item entity class, is a superclass for all item-type classes. 
 */
package entities;

import exceptions.InitiationException;

public abstract class Item {

    private final String name;
    private final String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        validateName();
    }

    public void validateName() {
        if (this.getName().split(" ").length > 2) {
            throw new InitiationException("class: Items: Invalid name - max 2 words!");
        }
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Changes the players damage points, when item is used.
     * @param player the player from which you get the damage points
     * @param value the value which is added to the players damage points
     */
    public void changeDamage(Player player, int value) {
        player.setDamage(player.getDefaultDamage() + value);
    }

     /**
     * Changes the players protection points, when item is used.
     * @param player the player from which you get the protection points
     * @param value the value which is added to the players protection points
     */
    public void changeProtection(Player player, int value) {
        player.setProtection(player.getDefaultProtection() + value);
    }

    /**
     * Changes the players health points, when item is used.
     * Before applying value it is checked if the players new health value exceeds the maximum health value for the player. 
     * If so, the health is set to maximum health.
     * @param player the player from which you get the health points
     * @param value the value which is added to the players health points
     */
    public void changeHealth(Player player, int value) {
        int sum = value + player.getHealth();
        if (sum > player.getHealthMaxSize()) {
            sum = player.getHealthMaxSize();
        }
        player.setHealth(sum);
    }

    /**
     * Adds item to the player's inventory, and removes it from player's currentRoom inventory
     */
    public void pickUpItem(Player player) {
        player.addToInventory(this);
        player.getCurrentRoom().removeFromInventory(this);
    }

     /**
     * Removes item from the player's inventory, and adds it to player's currentRoom inventory 
     */
    public void DropItem(Player player) {
        player.getCurrentRoom().addToInventory(this);
        player.removeFromInventory(this);
    }

}
