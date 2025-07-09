package jp.houlab.Mochidsuki.ultimateCard.vaporblast;

import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.destroy.EntityBlockDestroyAnimationKeyType;
import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.destroy.EntityBlockDestroyer;
import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.plant.EntityBlockPlantAnimationKey;
import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.plant.EntityBlockPlanter;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.scheduler.BukkitRunnable;

import static jp.houlab.Mochidsuki.ultimateCard.Main.plugin;

public class CloudPlantKey extends EntityBlockPlantAnimationKey {
    public CloudPlantKey(int time, int x, int y, int z, BlockData blockData, boolean isCollision, EntityBlockPlantAnimationKeyType type, int dist, Particle particle) {
        super(time, x, y, z, blockData, isCollision, type, dist, particle);
    }

    @Override
    public void finalRun(BlockDisplay blockDisplay, ArmorStand armorStand){
        new BukkitRunnable(){

            @Override
            public void run() {
                CloudDestroyer destroyer = new CloudDestroyer(blockDisplay,armorStand,EntityBlockDestroyAnimationKeyType.DROP_OUT,2);
            }
        }.runTaskLater(plugin,30*20);
    }
}

class CloudDestroyer extends EntityBlockDestroyer{

    public CloudDestroyer(BlockDisplay blockDisplay, ArmorStand armorStand, EntityBlockDestroyAnimationKeyType type, int dist) {
        super(blockDisplay, armorStand, type, dist);
    }

    public void everyRun(){
        getBlockDisplay().getLocation().getWorld().spawnParticle(Particle.CLOUD,getBlockDisplay().getLocation(),10,0.1,0.1,0.1);
    }
}
