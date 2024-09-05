package jp.houlab.Mochidsuki.ultimateCard;

import jp.houlab.mochidsuki.elytra_jetpacker.JumpEffect;
import jp.houlab.mochidsuki.elytra_jetpacker.RemoveTag;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static jp.houlab.mochidsuki.elytra_jetpacker.Main.plugin;

public class TakeOffListener implements Listener {
    @EventHandler
    public void PlayerToggleSneakEvent(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        if(event.isSneaking()){//スニークした
            if(TakeOffMain.holdingTask.containsKey(player)){
                TakeOffMain.holdingTask.get(player).cancel();
                TakeOffMain.holdingTask.remove(player);
                player.removePotionEffect(PotionEffectType.LEVITATION);
            }
        }
    }
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(event.hasItem()){
            switch (event.getItem().getType()){
                case SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE:
                    if(item.getEnchantments().get(Enchantment.BINDING_CURSE) == 1){
                        player.removePotionEffect(PotionEffectType.LEVITATION);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,60, 40,true,false,false));
                        player.addScoreboardTag("JetPack");
                        TakeOffMain.holdingTask.get(player).cancel();
                        TakeOffMain.holdingTask.remove(player);
                        new RemoveTag(player).runTaskTimer(plugin,20L,1);
                        new JumpEffect(60,player).runTaskTimer(plugin,1,1);
                        item.removeEnchantment(Enchantment.BINDING_CURSE);
                        Main.setCoolDown(player,120);
                    }
            }
        }
    }
}
