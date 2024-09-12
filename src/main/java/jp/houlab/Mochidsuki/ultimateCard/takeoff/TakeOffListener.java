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

                if(TakeOffMain.mainUser.contains(player)) {
                    Main.setCoolDown(player, 20);
                    TakeOffMain.mainUser.remove(player);
                }
                for(ItemStack item : player.getInventory().getContents()){
                    if(item != null && item.getType().equals(Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE)){
                        item.removeEnchantment(Enchantment.BINDING_CURSE);
                        IgnitionListener.LoadingItems.remove(item);
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
                        IgnitionListener.LoadingItems.remove(item);
                        item.removeEnchantment(Enchantment.BINDING_CURSE);
                        Main.setCoolDown(player, 120);

                        if(player.getScoreboard().getPlayerTeam(player) != null){
                            for(String name : player.getScoreboard().getPlayerTeam(player).getEntries()){
                                if(Bukkit.getPlayer(name) != null && Bukkit.getPlayer(name).isOnline()){
                                    Player p = Bukkit.getPlayer(name);
                                    if(TakeOffMain.holdingTask.containsKey(p)){
                                        p.removePotionEffect(PotionEffectType.LEVITATION);
                                        p.addScoreboardTag("JetPack");
                                        TakeOffMain.holdingTask.get(p).cancel();
                                        TakeOffMain.holdingTask.remove(p);
                                        TakeOffMain.mainUser.remove(p);
                                        TakeOffMain.spinUpEngine(p);
                                    }
                                }
                            }
                        }else {
                            player.removePotionEffect(PotionEffectType.LEVITATION);
                            player.addScoreboardTag("JetPack");
                            TakeOffMain.holdingTask.get(player).cancel();
                            TakeOffMain.holdingTask.remove(player);
                            TakeOffMain.mainUser.remove(player);
                            Main.setCoolDown(player, 120);
                            TakeOffMain.spinUpEngine(player);
                        }

                    }
                }
            }
        }
    }
}
