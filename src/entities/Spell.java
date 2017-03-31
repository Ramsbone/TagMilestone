/*
* Armour entity class, subclass of the Item class
*/
package entities;


public class Spell extends Item{
    
    private final int damageChange;
    private final int healthChange;
    private final int protectionChange;

    public Spell(String name, String description, int damageChange, int healthChange, int protectionChange) {
        
        super(name,description);
        this.damageChange = damageChange;
        this.healthChange = healthChange;
        this.protectionChange = protectionChange;
    }

    public int getDamageChange() {
        return damageChange;
    }

    public int getHealthChange() {
        return healthChange;
    }

    public int getProtectionChange() {
        return protectionChange;
    }
    
    public void activateSpell(Player player){
        int damage = player.getDamage();
        int protection = player.getProtection();
        
        if(damage < player.getDefaultDamage()+damageChange ){
            player.setDamage(player.getDefaultDamage()+damageChange);
        }
        if(protection < player.getDefaultProtection()+protectionChange){
            player.setProtection(player.getDefaultProtection()+protectionChange);
        }
        
        player.setDefaultProtection(player.getDefaultProtection()+protectionChange);
        player.setProtection(player.getProtection()+protectionChange);
        player.setDefaultDamage(player.getDefaultDamage()+damageChange);
        player.setDamage(player.getDamage()+damageChange);
        player.setHealthMaxSize(player.getHealthMaxSize() + healthChange);
    }
    
    
    
    
    
    
}
