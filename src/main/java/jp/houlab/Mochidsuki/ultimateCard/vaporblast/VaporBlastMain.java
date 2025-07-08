package jp.houlab.Mochidsuki.ultimateCard.vaporblast;

import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.EntityBlockPlantAnimationKey;
import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.EntityBlockPlanter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

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
        List<EntityBlockPlantAnimationKey> keys = new ArrayList<>(List.of(
                new EntityBlockPlantAnimationKey(0,0,0,0,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(1,-1,0,1,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(2,0,0,1,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(3,1,0,1,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(4,1,0,0,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(5,1,0,-1,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(6,0,0,-1,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(7,-1,0,-1,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(8,-1,0,0,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(8,-2,0,0,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(9,-2,0,1,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(10,-1,0,2,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(11,0,0,2,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(12,1,0,2,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(13,2,0,1,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(14,2,0,0,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(15,2,1,-1,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(16,1,1,-2,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(17,0,1,-2,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(18,-1,1,-2,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(19,-2,1,-1,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(20,-3,1,0,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(21,-3,1,1,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(22,-2,1,2,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(23,-1,1,3,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(24,0,1,3,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(25,1,1,3,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(26,2,1,2,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(27,3,1,1,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD),
                new EntityBlockPlantAnimationKey(28,3,1,0,blockData,true, EntityBlockPlantAnimationKey.EntityBlockPlantAnimationKeyType.DROP_IN,1, Particle.CLOUD)));
        new EntityBlockPlanter(28,keys,location.add(0,-3,0),player.getYaw()).runTaskTimer(plugin,1,1);
    }
}
