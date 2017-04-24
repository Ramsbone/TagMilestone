
package controllers;

import entities.Item;
import entities.Room;
import java.util.ArrayList;


public class DebugBuilder {
    
    //Colors are used only in our debugging function.
    private final String BLACK = (char) 27 + "[30m";
    private final String RED = (char) 27 + "[31m";
    private final String GREEN = (char) 27 + "[32m";
    private final String YELLOW = (char) 27 + "[33m";
    private final String BLUE = (char) 27 + "[34m";
    private final String PURPLE = (char) 27 + "[35m";
    private final String CYAN = (char) 27 + "[36m";
    
    
    
    //Shows a list of all the rooms in alphabetic order.
    //Also shows attributes if not null or false.
    public String debug(Room r){
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
        
        return output;
    }
    
}
