/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Christian
 */
public class Monster extends Character{
    
    private final String description;
    
    public Monster(String name, 
            Weapon weapon, 
            Armour armour,
            String description) {
        super(name, weapon, armour, description);
        this.description = description;
    }
    
}
