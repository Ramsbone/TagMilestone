/*
* Room Controller class of the program. 
*/
package controllers;

import boundary.Boundary;
import entities.Player;
import entities.Room;
import java.util.ArrayList;
import java.util.Random;

public class RoomController {

    private final Builder build = new Builder();
    private final Boundary ui = new Boundary();
    private ArrayList<Room> roomList = new ArrayList<Room>();
    
    //Build adventure using build-controller
    public void roomBuilder(Player player) {
        roomList = build.buildAdventure(player);
    }

    //Used when new room is entered to show room info and to check for NPC's.
    public void enterRoom(Player player) {
        

        checkForBoobyTrap(player, player.getCurrentRoom());
        checkForTaxcollector(player, player.getCurrentRoom());
        checkForThief(player, player.getCurrentRoom());
    }

    //Debug feature to show all rooms and what is in the rooms.
    public void roomDebugger() {

        for (Room r : roomList) {
            ui.debugRoom(r);
        }
        ui.showNumberOfRooms(roomList.size());
    }

    //Checks if a boobytrap is present in a room and if so makes changes to player health.
    private void checkForBoobyTrap(Player player, Room r) {
        int randomHealthRemoved;
        if (r.isBoobytrap()) {
            randomHealthRemoved = randomInt(//you will lose between 25% and 50% of your health
                    player.getHealth() / 4, //minimum
                    player.getHealth() / 2 //maximum
            );
            player.setHealth(player.getHealth() - randomHealthRemoved);
            r.setBoobytrap(false);
            ui.boobyTrapReleased(player);
        }
    }

    //Checks if a thief is in the room. Alsp checks if playes has any gold.
    //In case the player is not carrying any gold the thief leaves the room and enters another room.
    private void checkForThief(Player player, Room r) {
        int randomGoldRemoved;
        //The thief will sneak away if the player doesnt have any gold.
        if (r.isThief() && player.getGold() == 0) {
            Room newRoomForNPC = build.newRandomRoomForNPC();
            newRoomForNPC.setThief(true);
            newRoomForNPC.setGold(0);
            r.setThief(false);
            ui.aNPCsneaksAway();
        }
        if (r.isThief()) {
            randomGoldRemoved = randomInt(//you will lose at least half of your gold:
                    player.getGold() / 2, //minimum
                    player.getGold() //maximum
            );
            player.setGold(player.getGold() - randomGoldRemoved);
            r.setThief(false);
            ui.encounterThief(player);
        }
    }

    //Checks if a taxcollector is in the room. Alsp checks if playes has any gold.
    //In case the player is not carrying any gold the taxcollector leaves the room and enters another room.
    private void checkForTaxcollector(Player player, Room r) {
        int randomGoldRemoved;
        //The taxcollector will sneak away if the player doesnt have any gold.
        if (r.isTaxcollector() && player.getGold() == 0) {
            Room newRoomForNPC = build.newRandomRoomForNPC();
            newRoomForNPC.setTaxcollector(true);
            newRoomForNPC.setGold(0);
            r.setTaxcollector(false);
            ui.aNPCsneaksAway();
        }
        if (r.isTaxcollector()) {
            randomGoldRemoved = randomInt(//you might lose as much as half of your gold:
                    1, //minimum
                    player.getGold() / 2 //maximum
            );
            player.setGold(player.getGold() - randomGoldRemoved);
            r.setTaxcollector(false);
            ui.encounterTaxcollector(player);
        }
    }

    //Is used to establish how much gold a thief or a taxcollector should take from player.
    private int randomInt(int min, int max) { //if min = 5 and max = 10, range should be 5 incluse -> 10 inclusive.
        Random r = new Random();
        int output;
        try {
            output = r.nextInt(max - min + 1) + min;
        } catch (Exception e) {
            output = 1; //catch set to 1 to make sure that a positive integer is returned.
        }
        return output;
    }

}
