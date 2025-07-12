package jp.houlab.Mochidsuki.ultimateCard.vaporblast;

import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.destroy.EntityBlockDestroyAnimationKeyType;
import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.destroy.EntityBlockDestroyer;
import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.plant.EntityBlockPlantAnimationKey;
import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.plant.EntityBlockPlanter;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

import static jp.houlab.Mochidsuki.ultimateCard.Main.plugin;
import static jp.houlab.Mochidsuki.ultimateCard.vaporblast.VaporBlastMain.PERSISTENCE_TIME;

public class CloudPlantKey extends EntityBlockPlantAnimationKey {
    public CloudPlantKey(int time, int x, int y, int z, BlockData blockData, boolean isCollision, boolean isFPSMode, EntityBlockPlantAnimationKeyType type, int dist) {
        super(time, x, y, z, blockData, isCollision,isFPSMode, type, dist);
    }

    @Override
    public void finalRun(BlockDisplay blockDisplay, ArmorStand armorStand){
        new BukkitRunnable(){
            int time;
            final int maxTime;
            {
                maxTime = PERSISTENCE_TIME * 20 + new Random().nextInt(60);
            }

            @Override
            public void run() {

                if(time %4 == 1) {
                    if (time >= maxTime - 5 * 20) {
                        blockDisplay.getLocation().getWorld().spawnParticle(Particle.SNOWFLAKE, blockDisplay.getLocation(), 1, 0.3, 0.3, 0.3, 0);
                    } else {
                        blockDisplay.getLocation().getWorld().spawnParticle(Particle.SNOWBALL, blockDisplay.getLocation(), 1, 0.3, 0.3, 0.3, 0);
                    }
                }

                time++;
                if (time>= maxTime) {
                    new CloudDestroyer(blockDisplay, armorStand, EntityBlockDestroyAnimationKeyType.FADE_UP, 20).runTaskTimer(plugin, 1, 1);
                    cancel();
                }
            }
        }.runTaskTimer(plugin,1,1);
    }

    @Override
    public void everyRun(BlockDisplay blockDisplay, ArmorStand armorStand) {
        blockDisplay.getLocation().getWorld().spawnParticle(Particle.CLOUD,blockDisplay.getLocation(),10,0.1,0.1,0.1,0.1);
    }

    @Override
    public void plantRun(BlockDisplay blockDisplay, ArmorStand armorStand) {
        blockDisplay.getLocation().getWorld().playSound(blockDisplay.getLocation(), Sound.BLOCK_SNOW_BREAK, 0.7f, 0);
    }
}

class CloudDestroyer extends EntityBlockDestroyer{

    public CloudDestroyer(BlockDisplay blockDisplay, ArmorStand armorStand, EntityBlockDestroyAnimationKeyType type, int dist) {
        super(blockDisplay, armorStand, type, dist);
    }

    @Override
    public void everyRun(){
        getBlockDisplay().getLocation().getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION,getBlockDisplay().getLocation(),10,0.4,0.4,0.4,0.2, new Particle.DustTransition(Color.WHITE,Color.WHITE,2));
        switch (getNowTime()){
            case 10:{
                getBlockDisplay().setBlock(Bukkit.createBlockData(Material.WHITE_STAINED_GLASS));
                break;
            }
            case 15:{
                getBlockDisplay().setBlock(Bukkit.createBlockData(Material.GLASS));
                break;
            }
        }
    }
}
