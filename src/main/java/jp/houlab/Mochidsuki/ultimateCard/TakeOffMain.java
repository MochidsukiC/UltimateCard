package jp.houlab.Mochidsuki.ultimateCard;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

import static jp.houlab.Mochidsuki.ultimateCard.Main.plugin;

public class TakeOffMain {
    static public boolean holdTakeOff(Player player, ItemStack item) {
        boolean flag = false;

        for (int i = 1; i < 120 && !flag; i++) {
            Location loc = player.getLocation().clone();
            flag = !loc.add(0, i, 0).getBlock().getType().equals(Material.AIR);
        }
        if (!flag) {
            player.addScoreboardTag("JetPack");
            Location loc = player.getLocation().clone();
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 999999, 254));

            new BukkitRunnable() {
                @Override
                public void run() {
                    ItemMeta meta = item.getItemMeta();
                    meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
                    item.setItemMeta(meta);
                }
            }.runTaskLater(plugin, 20);

            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(loc.clone().add(0, 1, 0));
                    player.getWorld().spawnParticle(Particle.SPIT, player.getLocation(), 50, 0.1, 0.1, 0.1, 0.2);
                    player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 50, 0.2, 0.2, 0.2, 0);
                    player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, player.getLocation(), 50, 0.2, 0.2, 0.2, 0);
                    player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, player.getLocation(), 50, 0.2, 0.2, 0.2, 0);
                }
            }.runTaskTimer(plugin, 1L, 1L);
            holdingTask.put(player, task);



            return true;
        }else {
            player.sendMessage("上空に障害物あり");
            return false;
        }
    }

    static public HashMap<Player,BukkitTask> holdingTask = new HashMap<>();
}


