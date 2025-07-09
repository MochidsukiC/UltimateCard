package jp.houlab.Mochidsuki.ultimateCard.vaporblast;

import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.destroy.EntityBlockDestroyAnimationKeyType;
import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.destroy.EntityBlockDestroyer;
import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.plant.EntityBlockPlantAnimationKey;
import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.plant.EntityBlockPlanter;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

import static jp.houlab.Mochidsuki.ultimateCard.Main.plugin;

public class CloudPlantKey extends EntityBlockPlantAnimationKey {
    public CloudPlantKey(int time, int x, int y, int z, BlockData blockData, boolean isCollision, EntityBlockPlantAnimationKeyType type, int dist) {
        super(time, x, y, z, blockData, isCollision, type, dist);
    }

    @Override
    public void finalRun(BlockDisplay blockDisplay, ArmorStand armorStand){
        new BukkitRunnable(){

            @Override
            public void run() {
                new CloudDestroyer(blockDisplay,armorStand,EntityBlockDestroyAnimationKeyType.FADE_UP,20).runTaskTimer(plugin,1,1);
            }
        }.runTaskLater(plugin,30*20+new Random().nextInt(60));
    }

    @Override
    public void everyRun(BlockDisplay blockDisplay, ArmorStand armorStand) {
        blockDisplay.getLocation().getWorld().spawnParticle(Particle.CLOUD,blockDisplay.getLocation(),10,0.1,0.1,0.1,0.1);
    }
}

class CloudDestroyer extends EntityBlockDestroyer{

    public CloudDestroyer(BlockDisplay blockDisplay, ArmorStand armorStand, EntityBlockDestroyAnimationKeyType type, int dist) {
        super(blockDisplay, armorStand, type, dist);
    }

    @Override
    public void everyRun(){
        getBlockDisplay().getLocation().getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION,getBlockDisplay().getLocation(),10,0.2,0.2,0.2,0.2, new Particle.DustTransition(Color.WHITE,Color.WHITE,2));
    }
}
