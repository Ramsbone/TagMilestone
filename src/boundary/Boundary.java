/*
 * Boundery class, communicates with the user. 
 */
package boundary;

import entities.Armour;
import entities.Item;
import entities.Monster;
import java.util.ArrayList;
import java.util.Scanner;
import entities.Player;
import entities.Potion;
import entities.Room;
import entities.Spell;
import entities.Potion;
import entities.Furniture;
import entities.Portal;
import entities.Treasure;
import entities.Weapon;

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

    public String inputPlayerName() {

        System.out.println("***********************************************************************************"
                + "\n\t\t\tWelcome to Text Adventure Game!"
                + "\n***********************************************************************************");
        System.out.print("Please enter your name: ");
        String name = scan.nextLine();
        return name;
    }

    public void showStartText() {

        String output = "*********************************************************************************"
                + "\n\t\t\tType 'help' for a list of commands."
                + "\n***********************************************************************************"
                + "\nYou have been travelling across the land in search for the legendary dwarfen mines."
                + "\nLegend says the mines is not only abandoned, but also swarming with treasures left "
                + "\nbehind! Your search have led you to the side of an ancient mountain,"
                + "\nwhere it is said there is a secret entrance to the mines."
                + "\nA strange symbol in the rock catches your eyes."
                + "\nYou touch the symbol and the mountain starts to rumble."
                + "\nSuddenly the ground beneath you disappear!";
        System.out.println(output);
    }

    public void showEndText(int gold) {

        String output = "***********************************************************************************"
                + "\nThe sunlight blinds you, as you step outside."
                + "\nYou have finally found your way out, and relieved you breath in the fresh air."
                + "\nBehind you is a great adventure, dangers and propably still unfound treasure."
                + "\nBut for now you are pleased to see the sun again, and more adventure must wait "
                + "\nfor another day."
                + "\n***********************************************************************************"
                + "\nYou have collected " + gold + " gold"
                + "\n***********************************************************************************";
        System.out.println(output);
    }

    public void showHelp() {

        String output = "***********************************************************************************"
                + "\nChoose from following commands:"
                + "\n***********************************************************************************"
                + "\n-'help'\t\t\t\t: Shows the commands which can be typed"
                + "\n-'status'\t\t\t: Shows the player statistics"
                + "\n-'quit'\t\t\t\t: Quits the game, process will not be saved"
                + "\n-'west','east','north','south'  : Moves (if possible) the player in the given direction."
                + "\n-'take gold'\t\t\t: Picks up all the gold in the room"
                + "\n-'take <item>'\t\t\t: Picks up the item from the room"
                + "\n-'drop gold'\t\t\t: Drops some or all gold in the current room"
                + "\n-'drop <item>'\t\t\t: Drops item in the current room"
                + "\n-'use <item>'\t\t\t: Hold/wear/drink the item"
                + "\n-'inventory'\t\t\t: Shows players inventory and "
                + "\n-'look'\t\t\t\t: Shows room info"
                + "\n-'look <item>'\t\t\t: Shows item info"
                + "\n***********************************************************************************";
        System.out.println(output);
    }

    public void showMonster(Monster monster) {
        String output ="***********************************************************************************"
                +"\nYou have encountered " + monster.getName() + "."
                + "\n" + monster.getDescription();
                

        System.out.println(output);
    }

    public void showCombatStats(Monster monster, Player player) {
        String output = "***********************************************************************************"
                + "\n" + player.getName() + " has " + player.getHealth() +  " / " + player.getHealthMaxSize() + "hp \t * \t" + monster.getName() + " has " + monster.getHealth() + " hp.";
        System.out.println(output);
        if (!player.isInventoryEmpty()) {
            showPlayerInventory(player);
        }

    }

    public void showStat(Player player) {

        String output = "***********************************************************************************"
                + "\nPlayer " + player.getName() + " has " + player.getHealth() + " / " + player.getHealthMaxSize()+ " hp."
                + "\nYou inflict " + player.getDamage() + " damage points and got " + player.getProtection() + " protection points.";

        if (player.getWeapon() != null) {
            output += "\nYou hold " + player.getWeapon().getName() + " in your hands. ";
        }

        if (player.getArmour() != null) {
            output += "\nYou wear " + player.getArmour().getName() + " as armour. ";
        }

        output += "\n***********************************************************************************";
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

    public void usedQuitCommand() {

        String output = "***********************************************************************************"
                + "\nYou have chosen to quit the game.";
        System.out.println(output);
    }

    public String inputRestartRequest() {
        System.out.println("***********************************************************************************");
        System.out.print("Do you want to play again (y/n): ");
        String request = scan.nextLine();
        return request;
    }

    public void restartGame() {
        String output = "***********************************************************************************"
                + "\nYou have chosen to restart the game. Good luck.";
        System.out.println(output);
    }
    
    public void died() {

        String output = "***********************************************************************************"
                + "\nSorry, you suffered from a horrible and grusome death. ";
        System.out.println(output);
    }

    public void comeBack() {
        String output = "***********************************************************************************"
                        + "\nPlease come again and play TAG another time."
                        + "\n***********************************************************************************";
        System.out.println(output);

    }
    
    public void lookAtItem(Item item) {

        String output = "***********************************************************************************"
                + "\n\t\t\t\t" + item.getName()
                + "\n***********************************************************************************\n"
                + item.getDescription()
                + "\n***********************************************************************************";
        if (item instanceof Weapon) {
                output += "\nThis weapon will increase your damage with " + ((Weapon) item).getDamageIncrease() + " points."
                + "\n***********************************************************************************";
        }
        if (item instanceof Armour) {
                output += "\nThis armour will increase your protection with " + ((Armour) item).getProtectionIncrease() + " points."
                + "\n***********************************************************************************";
        }
        if (item instanceof Treasure) {
                output += "\nThis treasure have a value of " + ((Treasure) item).getValue() + " gold pieces."
                + "\n***********************************************************************************";
            
        }
        
        
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
                + "\nGold amount      : " + (player.getGold()==0 ? "" : (player.getGold() + " pieces of gold."))
                + "\nPlayer inventory : " + (player.isInventoryEmpty() ? "" : showItems(player.getInventory()))
                + "\nPotion inventory : " + (player.isPotionInventoryEmpty() ? "" : showItems(player.getPotionInventory()))
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

        String output = "***********************************************************************************"
                + "\nHighscore:\tRank \t\tPlayer \t\t\tGold"
                + "\n***********************************************************************************";

        for (int i = 0; i < infoFromHighscore.size(); i++) {
            output += "\n\t\t" + infoFromHighscore.get(i);
        }

        System.out.println(output);

    }

    public void boobyTrapReleased(Player player) {

        String output = "***********************************************************************************"
                + "\nYou have encountered a boobytrap in the room and releases it by accident."
                + "\nPlayer " + player.getName() + " has " + player.getHealth() + " / " + player.getHealthMaxSize()+ " hp.";

        System.out.println(output);
    }

    public void encounterThief(Player player) {

        String output = "***********************************************************************************"
                + "\nYou have encountered a thief that steals some of your gold."
                + "\nPlayer " + player.getName() + " has " + player.getGold() + " pieces of gold.";
        System.out.println(output);

    }

    public void encounterTaxcollector(Player player) {

        String output = "***********************************************************************************"
                + "\nYou have encountered a taxcollector who collects some of your gold in tax."
                + "\nPlayer " + player.getName() + " has " + player.getGold() + " pieces of gold.";
        
        System.out.println(output);

    }

    public void aNPCsneaksAway() {

        String output = "You hear a faint noise. You look around but see nothing...pobably just a rat..."
                + "\n***********************************************************************************";
        System.out.println(output);
    }

    public void monsterAttack(Monster monster, int monsterhit, Player player) {
        String output = monster.getName() + " attacks you and inflicts " + monsterhit + " damage." + "You have now " + player.getHealth() +  " / " +player.getHealthMaxSize() +" hp"
                + "\n***********************************************************************************";
        System.out.println(output);
    }
     public void monsterAttackIfPlayerDead(Monster monster, int monsterhit, Player player) {
        String output = "***********************************************************************************"
                + "\n" + monster.getName() + " attacks you and inflicts " + monsterhit + " damage."
                + "\n***********************************************************************************";
        System.out.println(output);
    }
    

    public void outputWrongWay() {
        System.out.println("Sorry, this directions is blocked, try again.");
    }

    public void unknownParameter(String command) {
        System.out.println("unknown parameter for the " + command + " command.");
    }

    public void unknownRequest() {
        System.out.println("unknown respond - please type y or n.");
    }

    public void cantCarryMore(Item item) {
        if (item instanceof Potion) {
            System.out.println("You do not have room for more potions.");
        } else
        {
            System.out.println("You can't carry anymore items.");
        }
    }

    public void cantPickUpObject() {
        System.out.println("You can't pick up this object.");
    }
    
    
    public void useItem(Weapon weapon, Player player){
        System.out.println("You hold " + 
                weapon.getName() + 
                " and your now inflict " + 
                player.getDamage()+  
                " damage points when attacking.");
        System.out.println("***********************************************************************************");
    
    }
    
    public void useItem(Armour armour, Player player){
        System.out.println("You now wear " + 
                armour.getName()+
                " as armour and has "+
                player.getProtection()+
                " points in total protection."); 
        System.out.println("***********************************************************************************");
    }
    
    public void useItem(Potion potion, Player player){
        System.out.println("You drink " + 
                potion.getName() + 
                " and now has " + 
                player.getHealth() + 
                " hp.");
    }
    
    public void useItem(Spell spell, Player p){
        String output = "***********************************************************************************"
                + "\nYou have read the " + spell.getName() + ", and following changes inflict on you"
                + "\n***********************************************************************************";
        
        if (spell.getDamageChange() != 0) {

            output += "\nYour damage has been changed to " + 
                    p.getDamage();

        }
        if (spell.getProtectionChange() != 0) {

            output += "\nYour protection has been changed to " + 
                    (p.getProtection());

        }
        if (spell.getHealthChange() != 0) {

            output += "\nYour maximum health has been changed to " + 
                    (p.getHealthMaxSize()) +" hp";

        }
        output += "\n***********************************************************************************";
        output += "\nAfter reading the spell it vanishes into to the air.";
        output += "\n***********************************************************************************";

        System.out.println(output);

    }
    
    public void useItem(Treasure treasure){
        System.out.println(treasure.getName() + " cannot be used right now but is worth " + treasure.getValue() + " gold pieces.");
    }
    
    
    public void useItem(Portal portal, ArrayList<Boolean> moveOn){
        String output = "***********************************************************************************"
                + "\nYou are been pulled into a spiralling light and teleported to another place."
                + "\nThe bright light and the fast movement makes you feel a little dizzy and confused.";

        System.out.println(output);
    }
    
    public void goldInChest(int i){
        String output = "You found "+i+" gold and added it to your inventory";
        System.out.println(output);
    }
    

    public void pickUpItemFirst() {
        System.out.println("You can't use this item before you carry it.");
    }
    
    
    
    //the following functions does not have to be changed --christian - 2017-04-06
    
    public void cantUseThisObject(){
        System.out.println("You can't use this object");
    }
    
    public void pickedUpItem(String name) {
        System.out.println("You have picked up " + name);
        System.out.println("***********************************************************************************");
    }

    public void droppedItem(String name) {
        System.out.println("You dropped " + name);
        System.out.println("***********************************************************************************");
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
                    + ", ";
        }

        //get monster and items from monster:
        String monsterLine = "";
        String monsterName = "";
        String monsterInventory = "";
        for (int i = 0; i < r.getMonsterList().size(); i++) {
            try {
                monsterName += r.getMonsterList().get(i).getName();

                try {
                    for (int j = 0; j < r.getMonsterList().get(i).getInventory().size(); j++) {
                        monsterInventory += r.getMonsterList().get(i).getInventory().get(j).getName();
                        monsterInventory += ", ";
                    }
                } catch (Exception e) {
                    monsterInventory += "";
                }

            } catch (Exception e) {
                monsterName += "";
            }

            monsterLine += RED + monsterName + BLACK + "--" + monsterInventory + " ***";
            monsterName = "";
            monsterInventory = "";

        }

        String output
                = BLUE + name + BLACK
                + " Gold: " + RED + gold + BLACK
                + " GC: " + CYAN + goldchance + BLACK
                + " \n\t" + rooms
                + "\n\t" + inventoryString
                + "\n\t" + boobytrap + thief + taxcollector
                + "\n\t" + monsterLine;

        System.out.println(output);

    }

    public void showNumberOfRooms(int value) {
        System.out.println("Rooms: " + value);

    }

    public void playerAttack(int playerhit, Player player) {
        String output = "***********************************************************************************"
                + "\nPlayer " + player.getName() + " attacks and inflicts " + playerhit + " damage.";

        System.out.println(output);
    }

    public void playerFlees(Player player) {
        String output = "***********************************************************************************"
                + "\nPlayer " + player.getName() + " has succesfully fled";

        System.out.println(output);

    }

    public void cannotFlee() {
        String output = "***********************************************************************************"
                + "\nIt is not possible to flee from this room";
        System.out.println(output);
    }

    public void monsterDefeated(Monster monster, Player player, int monsterGold) {
        String output = "***********************************************************************************"
                + "\nPlayer " + player.getName() + " has defeated " + monster.getName() + " and it drops " + monsterGold + " pieces of gold on the ground.";
        System.out.println(output);
    }

    public void playerDefeated(Monster monster, Player player) {
        String output = "Player " + player.getName() + " has been killed by " + monster.getName() + ".";
        System.out.println(output);
    }
    
    public void killCounterFeedBack(Player player){
        String output = "***********************************************************************************"
                + "\nYou have won the battle and gain a level, your maximum health increases to " + player.getHealthMaxSize() + " hp";
        System.out.println(output);
    }

    public void showCombatCommands() {
        String output = "***********************************************************************************"
                + "\nFollowing commands can be used during combat:"
                + "\nattack/a: \t attack the monster"
                + "\nuse <item>: \t use an item in your inventory"
                + "\nflee: \t\t flees to your previous room (if possible)";
        
        System.out.println(output);
    }
    
     

}
