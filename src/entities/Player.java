/*
* Player entity class, which hold the data of the player.
 */
package entities;

import java.util.ArrayList;

/*
Player klassen er lavet om af Christian Lykke d. 2017-03-04
Hele den gamle player klasse ligger udkommenteret til sidst i filen
*/

public class Player extends Character{

    private int defaultInventorySize;
    private int inventoryMaxSize;
    private int potionInventoryMaxSize;
    private Room currentRoom;
    private String moveDirection;
    private int killCounter;
    
    public Player(String name, Room currentRoom) {
        super(name);
        this.currentRoom = currentRoom;
        this.defaultInventorySize = 6;
        this.inventoryMaxSize = defaultInventorySize;
        this.killCounter = 0;
        this.potionInventoryMaxSize = 5;
    }
     
    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        
        this.currentRoom = currentRoom;
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
    
    /**
     * checks if potion inventory is empty
     */
    public boolean isPotionInventoryEmpty() {
        return this.potionInventory.isEmpty();
    }
    
    /**
     * checks if potion inventory exceeds the defined maximum
     */
    public boolean isPotionInventoryFull() {
        return (this.potionInventory.size() == potionInventoryMaxSize);
    }
    
    
    public int getInventoryMaxSize() {
        return inventoryMaxSize;
    }

    public void setInventoryMaxSize(int inventoryMaxSize) {
        this.inventoryMaxSize = inventoryMaxSize;
    }

    public String getMoveDirection() {
        return moveDirection;
    }

    public void setMoveDirection(String moveDirection) {
        this.moveDirection = moveDirection;
    }

    public int getKillCounter() {
        return killCounter;
    }

    public void setKillCounter(int killCounter) {
        this.killCounter = killCounter;
        super.setHealthMaxSize(super.getHealthMaxSize()+10);
    }    

}

