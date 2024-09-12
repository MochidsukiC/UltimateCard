package jp.houlab.Mochidsuki.ultimateCard.takeoff;

import jp.houlab.mochidsuki.elytra_jetpacker.JumpEffect;
import jp.houlab.mochidsuki.elytra_jetpacker.Main;
import jp.houlab.mochidsuki.elytra_jetpacker.RemoveTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static jp.houlab.Mochidsuki.ultimateCard.Main.plugin;

/**
 * アルティメットアビリティ・テイクオフのメインクラス
 * @author Mochidsuki
 */
public class TakeOffMain {
    /**
     * プレイヤーを離陸待機に移行する
     * @param player 離陸待機に移行するプレイヤー
     * @param isMainPlayer プレイヤーがメイン飛行者(アルティメット使用者)か否か
     */
    static public void holdTakeOff(Player player,boolean isMainPlayer) {
        boolean flag = false;

        for (int i = 1; i < 120 && !flag; i++) {
            Location loc = player.getLocation().clone();
            flag = !loc.add(0, i, 0).getBlock().getType().equals(Material.AIR);
        }
        if (!flag) {
            player.addScoreboardTag("JetPack");
            Location loc = player.getLocation().clone();
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 999999, 254));

            loc.getWorld().playSound(loc, Sound.ITEM_FIRECHARGE_USE,0.5f,0);
            loc.getWorld().playSound(loc,Sound.ITEM_ARMOR_EQUIP_NETHERITE,30f,0);
            new BukkitRunnable() {
                @Override
                public void run() {
                    loc.getWorld().playSound(loc,Sound.BLOCK_PISTON_EXTEND,0.7f,0);
                }
            }.runTaskLater(plugin, 2);

            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(loc.clone().add(0, 1, 0));

                    loc.getWorld().playSound(loc,"minecraft:entity.breeze.charge" ,0.2f,0);
                    player.getWorld().spawnParticle(Particle.SPIT, player.getLocation(), 10, 0.1, 0.1, 0.1, 0.01);
                    player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 10, 0.2, 0.2, 0.2, 0);
                    player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, player.getLocation(), 10, 0.2, 0.2, 0.2, 0);
                    player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, player.getLocation(), 10, 0.2, 0.2, 0.2, 0);
                }
            }.runTaskTimer(plugin, 4L, 1L);
            holdingTask.put(player, task);

            if(isMainPlayer) {
                for (String teammate : player.getScoreboard().getPlayerTeam(player).getEntries()) {
                    if (Bukkit.getPlayer(teammate) != null && Bukkit.getPlayer(teammate).isOnline()) {
                        Player member = Bukkit.getPlayer(teammate);
                        if (player != member) {
                            Component component = Component.text(player.getName() + "が離陸しようとしている!");
                            Component component1 = (Component.text("[参加する]").color(NamedTextColor.GREEN).clickEvent(ClickEvent.runCommand("/takeoff jumpwithteammate " + player.getName())));
                            member.sendMessage(component);
                            member.sendMessage(component1);
                        }
                    }
                }
            }

        }else {
            player.sendMessage("上空に障害物あり");
        }
    }

    /**
     * プレイヤーをエンジンスピンアップ状態に移行する
     * @param player 対象となるプレイヤー
     */
    static public void spinUpEngine(Player player) {
        Shulker shulker = player.getWorld().spawn(player.getLocation().clone().add(0,1.6,0), Shulker.class);
        shulker.setAI(false);
        shulker.setInvisible(true);
        player.getWorld().playSound(player.getLocation(),Sound.ENTITY_BREEZE_INHALE,2, (float)0);

        int time = 40;
        player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, time, 3));
        new BukkitRunnable() {
            public int times = 0;
            public ArmorStand armor_stand;
            @Override
            public void run() {
                Location loc = player.getLocation().clone();
                player.setSneaking(true);
                player.teleport(player.getLocation().clone().add(0,0.2,0));

                if(armor_stand != null) {
                    armor_stand.remove();
                }
                armor_stand = player.getWorld().spawn(player.getLocation().clone().add(0,-0.2,0), ArmorStand.class);
                armor_stand.setVisible(false);
                armor_stand.setMarker(false);
                armor_stand.addPassenger(shulker);
                armor_stand.setGravity(false);

                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 40, 3, true, false, false));

                player.getWorld().spawnParticle(Particle.SPIT, player.getLocation(), 50, 0.1, 0.1, 0.1, 0.2);
                player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 50, 0.2, 0.2, 0.2, 0);
                player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, player.getLocation(), 50, 0.2, 0.2, 0.2, 0);
                player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, player.getLocation(), 50, 0.2, 0.2, 0.2, 0);
                times++;

                if(times == 26){
                    player.getWorld().playSound(player.getLocation(),Sound.ENTITY_BREEZE_INHALE,2, (float)1);
                }
                if(times == 45){
                    player.getWorld().playSound(player.getLocation(),Sound.ENTITY_BREEZE_INHALE,2, (float)2);
                }

                if(times>=time){
                    shulker.remove();
                    armor_stand.remove();

                    player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), 5);
                    player.getWorld().playSound(player.getLocation(),Sound.ENTITY_GENERIC_EXPLODE,1,0);

                    player.setSneaking(false);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, 40, true, false, false));

                    new RemoveTag(player).runTaskTimer(Main.plugin, 20L, 1);
                    new JumpEffect(60, player).runTaskTimer(Main.plugin, 1, 1);
                    cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 0L, 1L);
    }

    /**
     * 離陸準備中のプレイヤーとそれを司るBukkitRunnableタスク
     */
    static public HashMap<Player,BukkitTask> holdingTask = new HashMap<>();
    /**
     * メイン飛行者のリスト
     */
    static public List<Player> mainUser = new ArrayList<>();
}


