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
    public Furniture(String name, String description){
        super(name, description);
        this.setTakeable(false);
        
    }
    
    
    public boolean use(Player p){
        //her må gerne være en boolean der returneres til commandControler.
        //i så fald skal commandcontroler få boundary til at sende besked til brugeren om at dette item ikke kan bruges
        
//        int increaseRoomGold = 50;
//        p.getCurrentRoom().setGold(p.getCurrentRoom().getGold() + increaseRoomGold);
//        return increaseRoomGold;
        return false;
    }
    
    
}
