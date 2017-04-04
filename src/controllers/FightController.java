package controllers;

import entities.Player;
import entities.Character;
import entities.Monster;
import boundary.Boundary;
import entities.Armour;
import entities.Item;
import entities.Potion;
import entities.Room;
import entities.Spell;
import entities.Weapon;
import java.util.Random;

/**
 *
 * @author lene_
 */
public class FightController {

    Boundary ui = new Boundary();

    public boolean fight(Player player) {
        boolean flee = false;

        Monster monster = player.getCurrentRoom().getMonster();
        
        ui.showMonster(monster);

        while (monster.getHealth() > 0 && player.getHealth() > 0 && !flee) {
            ui.showCombatStats(monster, player);

            int monsterhit = calculateDamage(monster.getDamage(), player.getProtection());
            hit(monsterhit, player);
            
            ui.monsterAttack(monster, monsterhit);

            if (player.getHealth() > 0) {
                boolean validMove = false;
                while (!validMove) {
                    String userInput = isValidCommand();
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
                        case "use":
                            validMove = use(command, parameter, player);
                            break;
                        case "a":
                            int playerhit = calculateDamage(player.getDamage(), monster.getProtection());
                            hit(playerhit, monster);
                            break;
                        case "flee":
                            flee = flee(player);
                            break;
                        default:
                            ui.unknownCommand();
                    }
                }

            }

        }
        if (monster.getHealth() <= 0) {
            monster.monsterDies(player);
        }
        return flee;

    }

    private boolean use(String command, String parameter, Player player) {
        Item item = player.checkForItem(parameter);
        boolean itemused = true;
        if (item != null) {
            if (item instanceof Weapon) {
                int value = ((Weapon) item).getDamageIncrease();
                item.changeDamage(player, value);
                player.setWeapon((Weapon) item);
                ui.holdItem(item.getName(), player.getDamage());
            }
            if (item instanceof Armour) {
                int value = ((Armour) item).getProtectionIncrease();
                item.changeProtection(player, value);
                player.setArmour((Armour) item);
                ui.wearItem(item.getName(), value);
            }
            if (item instanceof Potion) {
                int value = ((Potion) item).getHealthChange();
                item.changeHealth(player, value);
                player.removeFromInventory(item);
                if (value < 0) {
                    ui.drinkPoison(item.getName(), player.getHealth());
                } else {
                    ui.drinkHealth(item.getName(), player.getHealth());
                }
            }
            if (item instanceof Spell) {
                ((Spell) item).activateSpell(player);
                ui.showSpellEffect(player, ((Spell) item));
            }

        } else {
            ui.unknownParameter(command);
            itemused = false;
        }
        return itemused;
    }

    public void hit(int damage, Character character) { //calculated damage and the character which is victim
        character.setHealth(character.getHealth() - damage);

    }

    public String isValidCommand() {
        String input = null;
        boolean badInput = true;

        while (badInput) {
            input = ui.getCommand().toLowerCase();
            input = wordReplacer(input);
            String[] words = input.split(" ");
            switch (words[0]) {
                case "use":
                case "a":
                case "flee":
                    badInput = false;
                    break;
                default:
                    badInput = true;
                    ui.unknownCommand();
            }
        }

        return input;
    }

    public String wordReplacer(String input) {

        while (input.contains("  ")) {
            input = input.replace("  ", " ");
        }
        input = input.replaceAll("attack", "a");

        return input;
    }

    public int calculateDamage(int damage, int protection) { //damage from attacker, protection from victim
        //not same hit everytime
        Random r = new Random();

        int n = r.nextInt(3); //int mellem 1 og 3
        double dmg = damage;

        dmg = dmg*(1-n/10.0);

        dmg = dmg - ((protection / 100) * dmg);

        damage = (int) dmg;

        //critical strike
        int x = r.nextInt(100);

        if (x <= 5) {
            damage = damage * 2;
        }

        return damage;
    }

    private boolean flee(Player player) {
        boolean succes = false;
        String direction = player.getMoveDirection();
        Room fleeRoom = null;

        switch (direction) {
            case "n":
                fleeRoom = player.getCurrentRoom().getSouth();
                break;
            case "s":
                fleeRoom = player.getCurrentRoom().getNorth();
                break;
            case "e":
                fleeRoom = player.getCurrentRoom().getWest();
                break;
            default:
                fleeRoom = player.getCurrentRoom().getEast();
        }

        if (fleeRoom != null) {
            succes = true;
            player.setCurrentRoom(fleeRoom);
        }

        return succes;
    }
}
