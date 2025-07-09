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

import static jp.houlab.Mochidsuki.ultimateCard.Main.plugin;

public class VaporBlastMain {

    Player player;
    Location location;

    public VaporBlastMain(Player player) {
        this.player = player;
        location = player.getLocation().clone().add(0,5,0);
    }

    public void jump(){

        player.getWorld().spawnParticle(Particle.WATER_SPLASH, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 1000,4,0,4);

        for(Player other : plugin.getServer().getOnlinePlayers()){
            if(other.getLocation().distance(player.getLocation()) <= 4){
                other.setVelocity(new Vector(0,1.2,0));
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this::plantCloud,10L);
                new BukkitRunnable() {
                    int time = 0;
                    public void run() {
                        player.getLocation().getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION,player.getLocation(),20,0.2,0.2,0.2,1,new Particle.DustTransition(Color.BLUE,Color.fromRGB(0,219,255),2));
                        time++;
                        if(time >= 10) cancel();
                    }
                }.runTaskTimer(plugin, 0L, 1L);
            }
        }
    }

    public void plantCloud(){
        BlockData blockData = Bukkit.createBlockData(Material.WHITE_SHULKER_BOX);
        EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType type = EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.UP_FADE_IN;
        int dist = -1;

        List<EntityBlockPlantAnimationKey> keys = new ArrayList<>(List.of(
                new CloudPlantKey(0,0,0,0,blockData,true, type,dist),
                new CloudPlantKey(1,-1,0,1,blockData,true, type,dist),
                new CloudPlantKey(2,0,0,1,blockData,true, type,dist),
                new CloudPlantKey(3,1,0,1,blockData,true, type,dist),
                new CloudPlantKey(4,1,0,0,blockData,true, type,dist),
                new CloudPlantKey(5,1,0,-1,blockData,true, type,dist),
                new CloudPlantKey(6,0,0,-1,blockData,true, type,dist),
                new CloudPlantKey(7,-1,0,-1,blockData,true, type,dist),
                new CloudPlantKey(8,-1,0,0,blockData,true, type,dist),
                new CloudPlantKey(8,-2,0,0,blockData,true, type,dist),
                new CloudPlantKey(9,-2,0,1,blockData,true, type,dist),
                new CloudPlantKey(10,-1,0,2,blockData,true, type,dist),
                new CloudPlantKey(11,0,0,2,blockData,true, type,dist),
                new CloudPlantKey(12,1,0,2,blockData,true, type,dist),
                new CloudPlantKey(13,2,0,1,blockData,true, type,dist),
                new CloudPlantKey(14,2,0,0,blockData,true, type,dist),
                new CloudPlantKey(15,2,1,-1,blockData,true, type,dist),
                new CloudPlantKey(16,1,1,-2,blockData,true, type,dist),
                new CloudPlantKey(17,0,1,-2,blockData,true, type,dist),
                new CloudPlantKey(18,-1,1,-2,blockData,true, type,dist),
                new CloudPlantKey(19,-2,1,-1,blockData,true, type,dist),
                new CloudPlantKey(20,-3,1,0,blockData,true, type,dist),
                new CloudPlantKey(21,-3,1,1,blockData,true, type,dist),
                new CloudPlantKey(22,-2,1,2,blockData,true, type,dist),
                new CloudPlantKey(23,-1,1,3,blockData,true, type,dist),
                new CloudPlantKey(24,0,1,3,blockData,true, type,dist),
                new CloudPlantKey(25,1,1,3,blockData,true, type,dist),
                new CloudPlantKey(26,2,1,2,blockData,true, type,dist),
                new CloudPlantKey(27,3,1,1,blockData,true, type,dist),
                new CloudPlantKey(28,3,1,0,blockData,true, type,dist)));
        new EntityBlockPlanter(28,keys,location.add(0,-3,0),player.getYaw(),Particle.CLOUD,null).runTaskTimer(plugin,1,1);
    }
}
