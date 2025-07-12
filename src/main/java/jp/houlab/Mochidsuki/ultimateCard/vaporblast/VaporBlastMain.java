package jp.houlab.Mochidsuki.ultimateCard.vaporblast;

import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.plant.EntityBlockPlantAnimationKey;
import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.plant.EntityBlockPlanter;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static jp.houlab.Mochidsuki.ultimateCard.Main.config;
import static jp.houlab.Mochidsuki.ultimateCard.Main.plugin;

public class VaporBlastMain {

    Player player;
    Location location;
    final int RADIUS = 4;

    public VaporBlastMain(Player player) {
        this.player = player;
        location = player.getLocation().clone().add(0,5,0);
    }

    public void jump(){
        player.getWorld().playSound(player.getLocation(),Sound.AMBIENT_UNDERWATER_EXIT,1,0.7f);
        player.getWorld().playSound(player.getLocation(),Sound.ITEM_AXE_SCRAPE,2,0.7f);
        player.getWorld().playSound(player.getLocation(),Sound.ENTITY_PLAYER_SWIM,1,1.3f);


        new BukkitRunnable(){
            int time;
            @Override
            public void run() {
                player.getWorld().spawnParticle(Particle.WATER_SPLASH, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), time*5,2,0,2);
                player.getWorld().spawnParticle(Particle.WATER_DROP, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), time*5,2,0,2);
                player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), time*5,2,0,2,1,new Particle.DustTransition(Color.BLUE,Color.fromRGB(0,219,255),2));
                player.getWorld().spawnParticle(Particle.WATER_BUBBLE, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), time*5,2,0,2);

                if(time % 5 == 1) {
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_SWIM, 0.4f, 0.4f+(float) time/30);
                }

                for(Player other : Bukkit.getOnlinePlayers()) {
                    if (other.getLocation().distance(player.getLocation()) <= RADIUS) {
                        for (int i = 0; i < time; i++) {
                            location.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, other.getLocation().clone().add(new Vector(0.9, 0.3+(float)time/20, 0).rotateAroundY(Math.toRadians((float)i*360/time))), 1, 0, 0, 0, 1, new Particle.DustTransition(Color.BLUE,Color.fromRGB(166, 251, 255),1));
                            location.getWorld().spawnParticle(Particle.WATER_BUBBLE, other.getLocation().clone().add(new Vector(0.9, 0.3+(float)time/20, 0).rotateAroundY(Math.toRadians((float)i*360/time))), 5, 0.05, 0.05, 0.05, 0);

                        }
                    }
                }

                time++;
                if(time>20){
                    for(Player other : plugin.getServer().getOnlinePlayers()){
                        if(other.getLocation().distance(player.getLocation()) <= RADIUS){
                            other.setVelocity(new Vector(0,1.2,0));
                            player.getLocation().getWorld().spawnParticle(Particle.CLOUD, other.getLocation(), 100, 0.2, 0.2, 0.2, 0.5);

                        }
                    }
                    player.getWorld().playSound(player.getLocation(), "entity.breeze.wind_burst", 0.7f, 0f);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_SPLASH_HIGH_SPEED, 0.7f, 0.7f);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_SWIM, 0.6f, 2f);


                    location = player.getLocation().clone().add(0,5,0);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->plantCloud(),10L);
                    new BukkitRunnable() {
                        int time = 0;
                        public void run() {
                            for(Player other : Bukkit.getOnlinePlayers()) {
                                if (other.getLocation().distance(player.getLocation()) <= RADIUS) {
                                    player.getLocation().getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, other.getLocation(), 20, 0.2, 0.2, 0.2, 1, new Particle.DustTransition(Color.fromRGB(166, 251, 255), Color.WHITE, 2));
                                }
                            }
                            time++;
                            if(time >= 10) cancel();
                        }
                    }.runTaskTimer(plugin, 0L, 1L);
                    cancel();
                }
            }
        }.runTaskTimer(plugin,1,1);
    }

    final public static int PERSISTENCE_TIME = config.getInt("VaporBlast.PersistenceTime");

    public void plantCloud(){
        BlockData blockData = Bukkit.createBlockData(Material.WHITE_SHULKER_BOX);
        EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType type = EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.UP_FADE_IN;
        int dist = -1;
        boolean isFPSMode = true;

        List<EntityBlockPlantAnimationKey> keys = new ArrayList<>(List.of(
                new CloudPlantKey(0,0,0,0,blockData,true,isFPSMode ,type,dist),
                new CloudPlantKey(1,-1,0,1,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(2,0,0,1,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(3,1,0,1,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(4,1,0,0,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(5,1,0,-1,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(6,0,0,-1,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(7,-1,0,-1,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(8,-1,0,0,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(8,-2,0,0,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(9,-2,0,1,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(10,-1,0,2,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(11,0,0,2,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(12,1,0,2,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(13,2,0,1,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(14,2,0,0,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(15,2,1,-1,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(16,1,1,-2,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(17,0,1,-2,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(18,-1,1,-2,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(19,-2,1,-1,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(20,-3,1,0,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(21,-3,1,1,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(22,-2,1,2,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(23,-1,1,3,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(24,0,1,3,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(25,1,1,3,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(26,2,1,2,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(27,3,1,1,blockData,true,isFPSMode, type,dist),
                new CloudPlantKey(28,3,1,0,blockData,true,isFPSMode, type,dist)));
        new EntityBlockPlanter(28,keys,location.add(0,-3,0),player.getYaw(),Particle.CLOUD,null).runTaskTimer(plugin,1,1);


        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,() ->{
            player.getWorld().playSound(player.getLocation().clone(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 3, 0.4f);
            player.getWorld().playSound(player.getLocation().clone(), Sound.AMBIENT_NETHER_WASTES_MOOD, 0.7f, 0.5f);
        },PERSISTENCE_TIME*20);
    }
}
