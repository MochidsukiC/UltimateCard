package jp.houlab.Mochidsuki.ultimateCard;

import jp.houlab.Mochidsuki.ultimateCard.TakeOff.TakeOffMain;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static jp.houlab.Mochidsuki.ultimateCard.Main.plugin;

public class IgnitionListener implements org.bukkit.event.Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(item != null) {
            switch (item.getType()) {
                case SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE: {
                    if(!item.getEnchantments().containsKey(Enchantment.BINDING_CURSE) && !LoadingItems.contains(item) && player.getCooldown(Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE) == 0 && !TakeOffMain.holdingTask.containsKey(player)) {
                        TakeOffMain.holdTakeOff(player,true);
                        TakeOffMain.mainUser.add(player);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                ItemMeta meta = item.getItemMeta();
                                meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
                                item.setItemMeta(meta);
                            }
                        }.runTaskLater(plugin, 20);
                        LoadingItems.add(item);
                    }
                    break;
                }
            }
        }
    }
    static public List<ItemStack> LoadingItems = new ArrayList<ItemStack>();
}
