
package entities.furniture;

import entities.Item;
import entities.Player;

public class TreasureChest extends Item{
    
    private int goldReserve;
    
    public TreasureChest(String name, String description, int gold){
        super(name, description);
        this.goldReserve = gold;
        this.setTakeable(false);
    }
    
    public boolean use(Player p){
        p.setGold(p.getGold()+this.goldReserve);
        goldReserve = 0;
        return true;
    }

    public int getGoldReserve() {
        return goldReserve;
    }
    
    
    
}
