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
public class Furniture extends Item{
    public Furniture(String name, String description, boolean takeable){
        super(name, description);
        this.setTakeable(takeable);
        
    }
    
    
    public int use(Player p){
        int increaseRoomGold = 50;
        p.getCurrentRoom().setGold(p.getCurrentRoom().getGold() + increaseRoomGold);
        return increaseRoomGold;
    }
    
    
}
