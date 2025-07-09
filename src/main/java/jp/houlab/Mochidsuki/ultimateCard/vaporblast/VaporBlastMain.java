package jp.houlab.Mochidsuki.ultimateCard.vaporblast;

import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.plant.EntityBlockPlantAnimationKey;
import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.plant.EntityBlockPlanter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
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
        for(Player other : plugin.getServer().getOnlinePlayers()){
            if(other.getLocation().distance(player.getLocation()) <= 4){
                other.setVelocity(new Vector(0,1,0));
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this::plantCloud,10L);
            }
        }
    }

    public void plantCloud(){
        BlockData blockData = Bukkit.createBlockData(Material.WHITE_WOOL);
        EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType type = EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.UP_FADE_IN;

        List<EntityBlockPlantAnimationKey> keys = new ArrayList<>(List.of(
                new CloudPlantKey(0,0,0,0,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(1,-1,0,1,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(2,0,0,1,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(3,1,0,1,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(4,1,0,0,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(5,1,0,-1,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(6,0,0,-1,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(7,-1,0,-1,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(8,-1,0,0,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(8,-2,0,0,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(9,-2,0,1,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(10,-1,0,2,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(11,0,0,2,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(12,1,0,2,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(13,2,0,1,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(14,2,0,0,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(15,2,1,-1,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(16,1,1,-2,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(17,0,1,-2,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(18,-1,1,-2,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(19,-2,1,-1,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(20,-3,1,0,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(21,-3,1,1,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(22,-2,1,2,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(23,-1,1,3,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(24,0,1,3,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(25,1,1,3,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(26,2,1,2,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(27,3,1,1,blockData,true, type,1, Particle.CLOUD),
                new CloudPlantKey(28,3,1,0,blockData,true, type,1, Particle.CLOUD)));
        new EntityBlockPlanter(28,keys,location.add(0,-3,0),player.getYaw(),Particle.CLOUD,null).runTaskTimer(plugin,1,1);
    }
}
