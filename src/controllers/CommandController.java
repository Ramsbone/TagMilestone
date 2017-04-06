/*
* Command Controller class of the program. 
* (This is the primary controller of the program, and handles input from the player,
* and calls output from the boundary when needed.
 */
package controllers;

import boundary.Boundary;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import entities.Player;
import entities.Room;
import entities.Item;
import entities.Spell;
import entities.Weapon;
import entities.Armour;
import entities.Furniture;
import entities.Portal;
import entities.Potion;
import entities.furniture.TreasureChest;

public class CommandController {

    private final Boundary ui = new Boundary();
    private final FightController fc = new FightController();
    private final Highscore hs = new Highscore();
    //Debug can be set to true when access is needed to roomList.
    private final boolean DEBUG = true;

    //Launch the game.
    public void start() {

        boolean restartGame = true;

        while (restartGame) {
            RoomController rc = new RoomController();
            Player player = new Player(ui.inputPlayerName(), null);
            rc.roomBuilder(player);
            ui.showStartText();
            restartGame = commandControl(player, rc);
        }
        ui.comeBack();

    }

    //Handles the different commands from the player.
    private boolean commandControl(Player player, RoomController rc) {
        String userInput = "";
        Room nextRoom = null;
        boolean restartGame = false;

        while (!userInput.equals("quit")
                && !userInput.equals("new")
                && !player.getCurrentRoom().getName().equals("Exit Point")
                && player.getHealth() > 0) {

            rc.enterRoom(player);
            
            boolean moveOn = false;
            if (player.getHealth() > 0 && player.getCurrentRoom().getMonster() == null) {
                ui.showRoom(player.getCurrentRoom());
            }
            if (player.getCurrentRoom().getMonster() != null) {
                moveOn = fc.fight(player);
            }


            while (!moveOn && player.getHealth() > 0) {

                userInput = inputAndCheckCommand();
                String[] userInputArray = userInput.split(" ");
                String command = userInputArray[0].toLowerCase();
                String parameter;
                if (userInputArray.length == 2) {
                    parameter = userInputArray[1];
                } else if (userInputArray.length > 2) {
                    parameter = userInputArray[1] + userInputArray[2];
                } else {
                    parameter = "";
                }

                switch (command) {
                    case "help":
                        ui.showHelp();
                        break;
                    case "gold":
                        ui.showPlayerGold(player);
                        break;
                    case "quit":
                        moveOn = true;
                        break;
                    case "new":
                        moveOn = true;
                        break;
                    case "n":
                        moveOn = moving(command, nextRoom, player, moveOn);
                        break;
                    case "s":
                        moveOn = moving(command, nextRoom, player, moveOn);
                        break;
                    case "e":
                        moveOn = moving(command, nextRoom, player, moveOn);
                        break;
                    case "w":
                        moveOn = moving(command, nextRoom, player, moveOn);
                        break;
                    case "take":
                        take(command, parameter, player);
                        break;
                    case "drop":
                        drop(command, parameter, player);
                        break;
                    case "use":
                        moveOn = use(command, parameter, moveOn, player);
                        break;
                    case "look":
                        look(parameter, command, player);
                        break;
                    case "stat":
                        ui.showStat(player);
                        break;
                    case "inv":
                        ui.showPlayerInventory(player);
                        break;
                    case "hs":
                        infoFromHighscore(player);
                        break;
                    default:
                        rc.roomDebugger();
                }
            }
        }

        if (player.getHealth() <= 0) {
            ui.died();
            restartGame = restartRequest();
            
        } else if (userInput.equals("quit")) {
            ui.usedQuitCommand();

        } else if (userInput.equals("new")) {
            ui.restartGame();
            restartGame = true;

        } else {
            ui.showEndText(player.getGold());
            infoFromHighscore(player);
            writeToHighscoreDocument(player);
            restartGame = restartRequest();
        }

        return restartGame;
    }

    private boolean moving(String command, Room nextRoom, Player player, boolean moveOn) {
        switch (command) {
            case "n":
                nextRoom = player.getCurrentRoom().getNorth();
                break;
            case "s":
                nextRoom = player.getCurrentRoom().getSouth();
                break;
            case "e":
                nextRoom = player.getCurrentRoom().getEast();
                break;
            default:
                nextRoom = player.getCurrentRoom().getWest();
        }

        if (nextRoom == null) {
            ui.outputWrongWay();
        } else {
            player.setCurrentRoom(nextRoom);
            player.setMoveDirection(command);
            moveOn = true;
        }
        return moveOn;
    }

    private void take(String command, String parameter, Player player) {
        if (parameter.equals("gold")) {
            if (player.getCurrentRoom().getGold() == 0) {
                ui.noGoldInRoom();
            } else {
                pickupGold(player.getCurrentRoom(), player);
                ui.showPlayerGold(player);
            }
        } else {
            Item item = player.getCurrentRoom().checkForItem(parameter);
            if (item != null) {
                if ((player.isInventoryFull() && !(item instanceof Potion)) 
                        || (player.isPotionInventoryFull() && (item instanceof Potion))) {
                    ui.cantCarryMore(item);
                } else {
                    if (item.isTakeable() == false) {
                        ui.cantPickUpObject();
                    } else {
                        item.pickUpItem(player);
                        ui.pickedUpItem(item.getName());
                    }
                }
            } else {
                ui.unknownParameter(command);
            }
        }
    }

    private void drop(String command, String parameter, Player player) {
        if (parameter.equals("gold")) {
            int dropped = ui.askHowMuchGoldToDrop();
            if (dropped > player.getGold()) {
                ui.tryToDropTooMuchGold();

            } else {
                dropGold(player.getCurrentRoom(), dropped, player);
                ui.showPlayerGold(player);
            }
        } else {
            Item item = player.checkForItem(parameter);
            if (item != null) {
                item.DropItem(player);
                ui.droppedItem(item.getName());

                if (item instanceof Weapon) {
                    Weapon weaponHolded = player.getWeapon();
                    if (weaponHolded != null) {
                        if (weaponHolded.getName().toLowerCase().equals(parameter)) {
                            player.setDamage(player.getDefaultDamage());
                            player.setWeapon(null);
                        }
                    }
                }

                if (item instanceof Armour) {
                    Armour armourWeared = player.getArmour();
                    if (armourWeared != null) {
                        if (armourWeared.getName().toLowerCase().equals(parameter)) {
                            player.setProtection(player.getDefaultProtection());
                            player.setArmour(null);
                        }
                    }
                }

            } else {
                ui.unknownParameter(command);
            }
        }
    }

    private boolean use(String command, String parameter, boolean moveOn, Player player) {
        Item item = player.checkForItem(parameter);
        if (item != null) {
            if (item instanceof Weapon) {
                ui.useItem((Weapon)item, ((Weapon) item).useItem(player));
                //player.setWeapon((Weapon) item);
//                ui.holdItem(item.getName(), player.getDamage());
            }
            if (item instanceof Armour) {
                ui.useItem((Armour) item, ((Armour) item).useItem(player));
//                player.setArmour((Armour) item);
//                ui.wearItem(item.getName(), player.getProtection());
            }
            if (item instanceof Potion) {
                ui.useItem((Potion) item, ((Potion) item).useItem(player));
//                int value = ((Potion) item).getHealthChange();
//                item.changeHealth(player, value);
//                player.removeFromInventory(item);
//                if (value < 0) {
//                    ui.drinkPoison(item.getName(), player.getHealth());
//                } else {
//                    ui.drinkHealth(item.getName(), player.getHealth());
//                }
            }
            if (item instanceof Spell) {
                //((Spell) item).activateSpell(player);
                ui.useItem((Spell) item, player, ((Spell) item).useItem(player));
//ui.showSpellEffect(player, ((Spell) item));
            }

        } else {
            item = player.getCurrentRoom().checkForItem(parameter);
            if (item != null) {
                if (item instanceof Portal) {
                    Room newRoom = ((Portal) item).getRoomToEnter();
                    player.setCurrentRoom(newRoom);
                    moveOn = true;
                    ui.portalUsed();
                }
                if (item instanceof TreasureChest) {
                    int goldInChest = ((TreasureChest) item).getGoldReserve();
                    ((TreasureChest) item).use(player);
                    ui.goldInChest(goldInChest);
                } else {
                    ui.pickUpItemFirst();
                }
            } else {
                ui.unknownParameter(command);
            }
        }
        return moveOn;
    }

    private void look(String parameter, String command, Player player) {
        if (parameter.equals("")) {
            ui.showRoom(player.getCurrentRoom());
        } else if (parameter.equals("gold")) {
            ui.lookAtGold(player.getGold() + player.getCurrentRoom().getGold());
        } else {
            Item item = player.checkForItem(parameter);
            if (item != null) {
                ui.lookAtItem(item);
            } else {
                item = player.getCurrentRoom().checkForItem(parameter);
                if (item != null) {
                    ui.lookAtItem(item);
                } else {
                    ui.unknownParameter(command);
                }
            }
        }
    }

    //Gets input from boundary and cheeck if it is valid input
    private String inputAndCheckCommand() {
        String input = null;
        boolean badInput = true;
        while (badInput) {
            input = ui.getCommand().toLowerCase();
            input = wordReplacer(input);
            String[] words = input.split(" ");
            switch (words[0]) {
                case "help":
                case "gold":
                case "quit":
                case "new":
                case "n":
                case "s":
                case "e":
                case "w":
                case "take":
                case "drop":
                case "use":
                case "look":
                case "stat":
                case "inv":
                case "hs":
                    badInput = false;
                    break;
                case "debug":
                    if (DEBUG == true) {
                        badInput = false;
                    } else {
                        badInput = true;
                        ui.unknownCommand();
                    }
                    break;
                default:
                    badInput = true;
                    ui.unknownCommand();
            }
        }
        return input;
    }

    //Replaces synonyms and trims input
    private String wordReplacer(String input) {

        while (input.contains("  ")) {
            input = input.replace("  ", " ");
        }
        input = input.replaceAll("highscore", "hs");
        input = input.replaceAll("inventory", "inv");
        input = input.replaceAll("status", "stat");
        input = input.replaceAll("get", "take");
        input = input.replaceAll("pick up", "take");
        input = input.replaceAll("pick", "take");
        input = input.replaceAll("search", "look");
        input = input.replaceAll("describe", "status");
        input = input.replaceAll("health", "stat");
        input = input.replaceAll("exit", "quit");
        input = input.replaceAll("^\\?", "help");
        input = input.replaceAll("south", "s");
        input = input.replaceAll("north", "n");
        input = input.replaceAll("east", "e");
        input = input.replaceAll("west", "w");

        return input;
    }

    private boolean restartRequest() {
        char requestChar = ' ';
            requestChar = ui.inputRestartRequest().toLowerCase().charAt(0);
        while (requestChar != 'y' && requestChar != 'n') {
            ui.unknownRequest();
            requestChar = ui.inputRestartRequest().toLowerCase().charAt(0);
        }
        return (requestChar == 'y');
    }

    private void writeToHighscoreDocument(Player player) {
        try {
            hs.writeToHighscoreDocument(player.getName(), player.getGold());
        } catch (FileNotFoundException ex) {
            System.out.println("Highscore.java: writeToHighscoreDocument: " + ex);
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Highscore.java: writeToHighscoreDocument: " + ex);
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void infoFromHighscore(Player player) {
        ArrayList<String> highscoreOutput = new ArrayList<String>();
        try {
            highscoreOutput = hs.endGameHighscore(player.getName(), player.getGold());
        } catch (IOException ex) {
            System.out.println("Highscore.java: quitGameHighscore: " + ex);
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        ui.printInfoFromHighscore(highscoreOutput);
    }

    private void pickupGold(Room r, Player player) {
        player.setGold(player.getGold() + r.getGold());
        r.setGold(0);
    }

    private void dropGold(Room r, int amount, Player player) {
        r.setGold(r.getGold() + amount);
        player.setGold(player.getGold() - amount);
    }
}
