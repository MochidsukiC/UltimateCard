package jp.houlab.Mochidsuki.ultimateCard;

import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.plant.EntityBlockPlanter;
import jp.houlab.Mochidsuki.ultimateCard.hospital.HospitalMain;
import jp.houlab.Mochidsuki.ultimateCard.respawn.RespawnMain;
import jp.houlab.Mochidsuki.ultimateCard.takeoff.TakeOffMain;
import jp.houlab.Mochidsuki.ultimateCard.vaporblast.VaporBlastMain;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jp.houlab.Mochidsuki.ultimateCard.Main.config;
import static jp.houlab.Mochidsuki.ultimateCard.Main.plugin;

/**
 * アルティメット発動を検知するリスナー
 * @author Mochidsuki
 * 共有部分。アルティメットが一番最初にトリガーされるイベントのみを書くこと。各自のイベントリスナーは各パッケージの下に作成すること
 */
public class IgnitionListener implements org.bukkit.event.Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(item != null && event.getAction().isRightClick() && !LoadingItems.contains(event.getPlayer().getUniqueId())) {
            switch (item.getType()) {
                case SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE: {//TakeOff
                    if(!item.getEnchantments().containsKey(Enchantment.BINDING_CURSE) && player.getCooldown(Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE) == 0 && !TakeOffMain.holdingTask.containsKey(player)) {
                        TakeOffMain.mainUser.add(player.getUniqueId());
                        TakeOffMain.holdTakeOff(player,true,item);
                    }
                    break;
                }
                case VEX_ARMOR_TRIM_SMITHING_TEMPLATE:{//Respawn
                    if(player.getCooldown(Material.VEX_ARMOR_TRIM_SMITHING_TEMPLATE) == 0) {
                        new RespawnMain(player, player.getLocation().clone(), 0).runTaskTimer(plugin, 1, 1);
                        Main.setCoolDown(player,config.getInt("Respawn.CT"));
                    }
                    break;
                }
                case SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE:{//Hospital
                    if(player.getCooldown(Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE) == 0) {
                        new HospitalMain(player.getLocation().clone()).Main();
                        Main.setCoolDown(player, config.getInt("Hospital.CT"));
                    }
                    break;
                }
                case TIDE_ARMOR_TRIM_SMITHING_TEMPLATE:{//VaporBlast
                    if(player.getCooldown(Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE) == 0) {
                        new VaporBlastMain(player).jump();
                        Main.setCoolDown(player, config.getInt("VaporBlast.CT"));
                    }
                    break;
                }
            }
        }
    }

    /**
     * アルティメット展開中であり、クールダウンはまだ開始していないが、アイテムを使用させたくない時に使用。
     *　他の人も使っていいぜ by Mochidsuki
     */
    static public List<UUID> LoadingItems = new ArrayList<>();


    @EventHandler
    public void EntityDeathEvent(EntityDeathEvent event){
        if(EntityBlockPlanter.ShulkerToBlockDisplay.containsKey(event.getEntity().getUniqueId())){
            Entity blockDisplay = Bukkit.getEntity(EntityBlockPlanter.ShulkerToBlockDisplay.get(event.getEntity().getUniqueId()));
            if(blockDisplay != null){
                blockDisplay.remove();
            }
            if(event.getEntity().getVehicle() != null){
                event.getEntity().getVehicle().remove();
            }
        }
    }
}
