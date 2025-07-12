package jp.houlab.Mochidsuki.ultimateCard.takeoff;

import jp.houlab.Mochidsuki.ultimateCard.IgnitionListener;
import jp.houlab.Mochidsuki.ultimateCard.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import static jp.houlab.Mochidsuki.ultimateCard.Main.config;

/**
 *アルティメットアビリティ・テイクオフに関するイベントリスナー
 * @author Mochidsuki
 */
public class TakeOffListener implements Listener {
    /**
     * プレイヤーが離陸をキャンセルした際に呼び出される
     * @param event PlayerToggleSneakEventからの引数
     */
    @EventHandler
    public void PlayerToggleSneakEvent(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        if(event.isSneaking()){//スニークした
            if(TakeOffMain.holdingTask.containsKey(player)){
                TakeOffMain.holdingTask.get(player).cancel();
                TakeOffMain.holdingTask.remove(player);
                player.removePotionEffect(PotionEffectType.LEVITATION);
                player.removeScoreboardTag("JetPack");
                IgnitionListener.LoadingItems.remove(event.getPlayer().getUniqueId());

                if(TakeOffMain.mainUser.contains(player.getUniqueId())) {
                    Main.setCoolDown(player, 20);
                    TakeOffMain.mainUser.remove(player.getUniqueId());
                }
                for(ItemStack item : player.getInventory().getContents()){
                    if(item != null && item.getType().equals(Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE)){
                        item.removeEnchantment(Enchantment.BINDING_CURSE);
                    }
                }
            }
        }
    }

    /**
     * プレイヤーが離陸を開始しようとした時に呼び出される
     * @param event PlayerInteractEventからの引数
     */
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(event.hasItem()){
            switch (event.getItem().getType()){
                case SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE: {
                    if (item.getEnchantments().containsKey(Enchantment.BINDING_CURSE) &&item.getEnchantments().get(Enchantment.BINDING_CURSE) == 1) {
                        item.removeEnchantment(Enchantment.BINDING_CURSE);
                        Main.setCoolDown(player, config.getInt("TakeOff.CT"));

                        if(player.getScoreboard().getPlayerTeam(player) != null){
                            for(String name : player.getScoreboard().getPlayerTeam(player).getEntries()){
                                if(Bukkit.getOfflinePlayer(name).isOnline()){
                                    Player p = Bukkit.getPlayer(name);
                                    if(TakeOffMain.holdingTask.containsKey(p)){
                                        p.removePotionEffect(PotionEffectType.LEVITATION);
                                        p.addScoreboardTag("JetPack");
                                        TakeOffMain.holdingTask.get(p).cancel();
                                        TakeOffMain.holdingTask.remove(p);
                                        TakeOffMain.mainUser.remove(p.getUniqueId());
                                        TakeOffMain.spinUpEngine(p);
                                    }
                                }
                            }
                        }else {
                            player.removePotionEffect(PotionEffectType.LEVITATION);
                            player.addScoreboardTag("JetPack");
                            TakeOffMain.holdingTask.get(player).cancel();
                            TakeOffMain.holdingTask.remove(player);
                            TakeOffMain.mainUser.remove(player.getUniqueId());
                            TakeOffMain.spinUpEngine(player);
                        }

                    }
                }
            }
        }
    }
}
