/*
 * Boundery class, communicates with the user. 
 */
package boundary;

import entities.Item;
import java.util.ArrayList;
import java.util.Scanner;
import entities.Player;
import entities.Room;
import entities.Spell;

public class Boundary {

    //Colors are used only in our debugging function.
    private final String BLACK = (char) 27 + "[30m";
    private final String RED = (char) 27 + "[31m";
    private final String GREEN = (char) 27 + "[32m";
    private final String YELLOW = (char) 27 + "[33m";
    private final String BLUE = (char) 27 + "[34m";
    private final String PURPLE = (char) 27 + "[35m";
    private final String CYAN = (char) 27 + "[36m";

    Scanner scan = new Scanner(System.in);

    public String getCommand() {
        System.out.print("Please enter a command: ");
        String input = scan.nextLine();
        return input;
    }

    public void unknownCommand() {
        System.out.println("Unknown command, please try again");
    }

    public void showStartText() {
        String startText = "***********************************************************************************"
                + "\n\t\t\tWelcome to Text Adventure Game!"
                + "\n***********************************************************************************"
                + "\n\t\t\tType 'help' for a list of commands."
                + "\n***********************************************************************************"
                + "\nYou have been travelling across the land in search for the legendary dwarfen mines."
                + "\nLegend says the mines is not only abandoned, but also swarming with treasures left "
                + "\nbehind! Your search have led you to the side of an ancient mountain,"
                + "\nwhere it is said there is a secret entrance to the mines."
                + "\nA strange symbol in the rock catches your eyes."
                + "\nYou touch the symbol and the mountain starts to rumble."
                + "\nSuddenly the ground beneath you disappear!";

        System.out.println(startText);
    }

    public void showEndText(int gold) {
        String endText = "***********************************************************************************"
                + "\nThe sunlight blinds you, as you step outside."
                + "\nYou have finally found your way out, and relieved you breath in the fresh air."
                + "\nBehind you is a great adventure, dangers and propably still unfound treasure."
                + "\nBut for now you are pleased to see the sun again, and more adventure must wait "
                + "\nfor another day."
                + "\n***********************************************************************************"
                + "\nYou have collected " + gold + " gold"
                + "\n***********************************************************************************";

        System.out.println(endText);
    }

    public String inputPlayerName() {
        System.out.println("***********************************************************************************");
        System.out.print("Please enter your name: ");
        String name = scan.nextLine();
        return name;

    }

    public void showHelp() {
        String help = "***********************************************************************************"
                + "\nChoose from following commands:"
                + "\n***********************************************************************************"
                + "\n-'gold'\t\t\t\t: Shows how much gold is carried"
                + "\n-'help'\t\t\t\t: Shows the commands which can be typed"
                + "\n-'status'\t\t\t: Shows the player statistics"
                + "\n-'quit'\t\t\t\t: Quits the game, process will not be saved"
                + "\n-'west','east','north','south'  : Moves (if possible) the player in the given direction."
                + "\n-'take gold'\t\t\t: Picks up all the gold in the room"
                + "\n-'take <item>'\t\t\t: Picks up the item from the room"
                + "\n-'drop gold'\t\t\t: Drops some or all gold in the current room"
                + "\n-'drop <item>'\t\t\t: Drops item in the current room"
                + "\n-'use <item>'\t\t\t: Hold/wear/drink the item"
                + "\n-'look'\t\t\t\t: Shows room info"
                + "\n-'look <item>'\t\t\t: Shows item info"
                + "\n***********************************************************************************";
        System.out.println(help);
    }

    public void usedQuitCommand() {

        String output = "***********************************************************************************"
                + "\nYou have chosen to quit the game. Come back and play TAG soon again.";

        System.out.println(output);

    }
    
    public void died(){
        System.out.println("***********************************************************************************"
                + "\nSorry, you suffered from a horrible and grusome death. "
                + "\nPlease come again and play TAG another time.");
    }

    public void showStat(Player player) {

        String output = "***********************************************************************************"
                + "\nPlayer " + player.getName() + " has " + player.getHealth() + "% health."
                + "\nYou inflict " + player.getDamage() + " damage points and got " + player.getProtection() + " protection points.";

        if (player.getWeapon() != null) {
            output = output
                    + "\nYou hold " + player.getWeapon().getName() + " in your hands. ";
        }
        if (player.getArmour() != null) {
            output = output
                    + "\nYou wear " + player.getArmour().getName() + " as armour. ";
        }

        output = output + "\n***********************************************************************************";

        System.out.println(output);

    }

    public void showPlayerGold(Player player) {

        String output = "***********************************************************************************"
                + "\nPlayer " + player.getName() + " has " + player.getGold() + " pieces of gold."
                + "\n***********************************************************************************";

        System.out.println(output);
    }

    public void showRoom(Room room) {

        String output = "***********************************************************************************"
                + "\n\t\t\t\t" + room.getName()
                + "\n***********************************************************************************"
                + room.getDescription()
                + "\n***********************************************************************************";

        if (room.getGold() != 0) {

            output += "\nThere are " + room.getGold() + " pieces of gold in the room."
                    + "\n***********************************************************************************";

        }
        if (!room.isInventoryEmpty()) {
            output += "\nItems : " + showItems(room.getInventory())
                    + "\n***********************************************************************************";
        }

        System.out.println(output);

    }

    public void lookAtItem(Item item) {

        String output = "***********************************************************************************"
                + "\n\t\t\t\t" + item.getName()
                + "\n***********************************************************************************\n"
                + item.getDescription()
                + "\n***********************************************************************************";

        System.out.println(output);

    }

    public void lookAtGold(int amount) {

        String output;
        if (amount > 0) {
            output = "***********************************************************************************"
                    + "\n\t\t\t\tGold"
                    + "\n***********************************************************************************\n"
                    + "It looks golden and shiny and the light glitter back from it."
                    + "\n***********************************************************************************";
        } else {
            output = "There is no gold to look at.";
        }
        System.out.println(output);

    }

    public void showPlayerInventory(Player player) {

        String output = "***********************************************************************************"
                + "\nInventory : " + (player.isInventoryEmpty() ? "" : showItems(player.getInventory()))
                + "\n***********************************************************************************";

        System.out.println(output);
    }

    public String showItems(ArrayList<Item> inventory) {
        String output = "";
        for (Item item : inventory) {
            output += item.getName() + " * ";
        }
        return output;
    }

    public void printInfoFromHighscore(ArrayList<String> infoFromHighscore) {

        System.out.println("***********************************************************************************");
        System.out.println("Highscore:");
        System.out.println("\t\tRank \t\tPlayer \t\t\tGold");
        System.out.println("***********************************************************************************");
        for (int i = 0; i < infoFromHighscore.size(); i++) {
            System.out.println("\t\t"+infoFromHighscore.get(i));
        }
        System.out.println("***********************************************************************************");

    }

    public void portalUsed() {

        String output = "***********************************************************************************"
                + "\nYou are been pulled into a spiralling light and teleported to another place."
                + "\nThe bright light and the fast movement makes you feel a little dizzy and confused.";

        System.out.println(output);
    }

    public void boobyTrapReleased(Player player) {

        String output = "You have encountered a boobytrap in the room and releases it by accident."
                + "\nPlayer " + player.getName() + " has " + player.getHealth() + "% health."
                + "\n***********************************************************************************";

        System.out.println(output);
    }

    public void encounterThief(Player player) {

        String output = "You have encountered a thief that steals some of your gold."
                + "\nPlayer " + player.getName() + " has " + player.getGold() + " pieces of gold."
                + "\n***********************************************************************************";

        System.out.println(output);

    }

    public void encounterTaxcollector(Player player) {

        String output = "You have encountered a taxcollector who collects some of your gold in tax."
                + "\nPlayer " + player.getName() + " has " + player.getGold() + " pieces of gold."
                + "\n***********************************************************************************";

        System.out.println(output);

    }

    public void aNPCsneaksAway() {
        String output = "You hear a faint noise. You look around but see nothing...pobably just a rat..."
                + "\n***********************************************************************************";
        System.out.println(output);
    }

    public void outputWrongWay() {
        System.out.println("Sorry, this directions is blocked, try again.");
    }

    public void unknownParameter(String command) {
        System.out.println("unknown parameter for the " + command + " command.");
    }

    public void cantCarryMore() {
        System.out.println("You can't carry anymore.");
    }

    public void cantPickUpPortal() {
        System.out.println("You can't pick up a portal.");
    }

    public void pickUpItemFirst() {
        System.out.println("You can't use this item before you carry it.");
    }

    public void holdItem(String name, int value) {
        System.out.println("You hold " + name + " and your now inflict " + value + " damage points when attacking.");
    }

    public void wearItem(String name, int value) {
        System.out.println("You now wear " + name + " as armour and gain " + value + " points in extra protection.");
    }

    public void drinkHealth(String name, int value) {
        
            System.out.println("You drink " + name + " and now has " + value + "% in health.");
        
        
    }

    public void drinkPoison(String name, int value) {
        if(value > 0){
            System.out.println("You drink " + name + " and now has " + value + "% in health.");
        }
    }

    public void pickedUpItem(String name) {
        System.out.println("You have picked up " + name);
    }

    public void droppedItem(String name) {
        System.out.println("You dropped " + name);
    }

    //Is used when trying to pick up gold.
    public void noGoldInRoom() {
        System.out.println("Sorry, there is no gold in this room");
    }

    public int askHowMuchGoldToDrop() {
        System.out.print("Please enter how much gold you want to drop:");
        String _number = scan.nextLine();
        //Makes sure that input is valid.
        while (!_number.matches("[0-9][0-9]*")) {
            System.out.println("Not a valid amount of gold, please try again");
            _number = scan.nextLine();
        }
        int number = Integer.parseInt(_number);

        return number;
    }

    public void tryToDropTooMuchGold() {
        System.out.println("You want to drop more gold than you have");
    }

    //Shows a list of all the rooms in alphabetic order.
    //Also shows attributes if not null or false.
    public void debugRoom(Room r) {

        String N, S, E, W;

        try {
            N = "N: " + r.getNorth().getName();
        } catch (Exception e) {
            N = "";
        }
        try {
            S = " S: " + r.getSouth().getName();
        } catch (Exception e) {
            S = "";
        }
        try {
            E = " E: " + r.getEast().getName();
        } catch (Exception e) {
            E = "";
        }
        try {
            W = " W: " + r.getWest().getName();
        } catch (Exception e) {
            W = "";
        }

        String boobytrap, thief, taxcollector;

        boobytrap = Boolean.toString(r.isBoobytrap());
        thief = Boolean.toString(r.isThief());
        taxcollector = Boolean.toString(r.isTaxcollector());

        if (boobytrap.equals("false")) {
            boobytrap = "";
        } else {
            boobytrap = "boobytrap: " + GREEN + boobytrap + BLACK;
        }
        if (thief.equals("false")) {
            thief = "";
        } else {
            thief = "Thief: " + GREEN + thief + BLACK;
        }
        if (taxcollector.equals("false")) {
            taxcollector = "";
        } else {
            taxcollector = "Taxcollector: " + GREEN + taxcollector + BLACK;
        }

        String name = r.getName();
        String gold = Integer.toString(r.getGold());
        String goldchance = Integer.toString(r.getGoldchance());
        String rooms = N + S + E + W;

        //get items in inventory:
        ArrayList<Item> inventory;
        String inventoryString = "";
        inventory = r.getInventory();

        for (Item i : inventory) {
            Item itemInstance = i.getClass().cast(i);
            inventoryString = inventoryString 
                    + itemInstance.getName()
                    +", ";
        }
       
        
        String output
                = BLUE + name + BLACK
                + " Gold: " + RED + gold + BLACK
                + " GC: " + CYAN + goldchance + BLACK
                + " \n\t" + rooms
                + "\n\t" + inventoryString
                + "\n\t" + boobytrap + thief + taxcollector;
        
        System.out.println(output);

    }

    public void showNumberOfRooms(int value) {
        System.out.println("Rooms: " + value);

    }

    public void showSpellEffect(Player player, Spell spell) {
        
        
        String output = "***********************************************************************************"
                + "\nYou have read the " + spell.getName() + ", and following changes inflict on you"
                + "\n***********************************************************************************";
              

        if (spell.getDamageChange() != 0) {

            output += "\nYour damage without any weapons has been changed to " + (player.getDefaultDamage());

        }
         if (spell.getProtectionChange() != 0) {

            output += "\nYour protection without any armour has been changed to " + (player.getDefaultProtection());

        }
            if (spell.getHealthChange()!= 0) {

            output += "\nYour maximum health has been changed to " + (player.getHealthMaxSize());         

        }
            output += "\n***********************************************************************************";

        System.out.println(output);
        
    }
   
}
