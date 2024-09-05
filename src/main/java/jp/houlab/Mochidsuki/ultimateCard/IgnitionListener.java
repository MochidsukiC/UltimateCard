package jp.houlab.Mochidsuki.ultimateCard;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class IgnitionListener implements org.bukkit.event.Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(item != null) {
            switch (item.getType()) {
                case SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE: {
                    if(!item.getEnchantments().containsKey(Enchantment.BINDING_CURSE)) {
                        TakeOffMain.holdTakeOff(player,event.getItem());
                    }
                }
            }
        }
    }
}
