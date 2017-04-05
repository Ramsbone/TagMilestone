
package entities.furniture;

import entities.Furniture;
import entities.Player;



public class TreasureChest extends Furniture{
    
    private final int goldReserve;
    
    public TreasureChest(String name, String description, int gold){
        super(name, description);
        this.goldReserve = gold;
    }
    
    public boolean use(Player p){
        p.setGold(p.getGold()+this.goldReserve);
        return true;
    }

    public int getGoldReserve() {
        return goldReserve;
    }
    
    
    
}
