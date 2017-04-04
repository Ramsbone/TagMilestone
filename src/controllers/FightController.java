package controllers;

import entities.Player;
import entities.Character;
import entities.Monster;
import boundary.Boundary;
import entities.Armour;
import entities.Item;
import entities.Portal;
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

        Monster monster = player.getCurrentRoom().getMonster();

        while (monster.getHealth() > 0 && player.getHealth() > 0) {

            int monsterhit = calculateDamage(monster.getDamage(), player.getProtection());
            hit(monsterhit, player);

            if (player.getHealth() > 0) {
                boolean validMove = false;
                while(!validMove){
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
                    case "attack":
                        int playerhit = calculateDamage(player.getDamage(), monster.getProtection());
                        hit(playerhit, monster);
                        break;
                    case "flee":

                        break;
                    default:
                        ui.unknownCommand();
                }
                }
                
            }

        }

        return false;

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
                case "attack":
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
        return "";
    }

    public int calculateDamage(int damage, int protection) { //damage from attacker, protection from victim
        //not same hit everytime
        Random r = new Random();

        int n = r.nextInt(3); //int mellem 1 og 5
        double dmg = damage;

        switch (n) {
            case 1:
                dmg = dmg * 0.8;
                break;
            case 2:
                dmg = dmg * 0.9;
                break;
            default:
                dmg = dmg * 1.0;
                break;
        }

        protection = protection / 100;

        dmg = dmg - (protection * dmg);

        damage = (int) dmg;

        //critical strike later
        return damage;
    }
}
