package jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.destroy;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static jp.houlab.Mochidsuki.ultimateCard.Main.plugin;

public class EntityBlockDestroyer extends BukkitRunnable {

    BlockDisplay blockDisplay;
    ArmorStand armorStand;
    int dist;
    float speed;

    public BlockDisplay getBlockDisplay() {
        return blockDisplay;
    }

    public void setBlockDisplay(BlockDisplay blockDisplay) {
        this.blockDisplay = blockDisplay;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public void setArmorStand(ArmorStand armorStand) {
        this.armorStand = armorStand;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    float acceleration;
    double y;


    public EntityBlockDestroyer(BlockDisplay blockDisplay, EntityBlockDestroyAnimationKeyType type){
        this.blockDisplay = blockDisplay;
        switch (type){
            case DROP_OUT:{
                speed = 0;
                acceleration = -0.048f;
                break;
            }
            case FADE_UP:{
                speed = 0.02f;
                break;
            }
            case FADE_DOWN:{
                speed = -0.02f;
                break;
            }
        }
    }

    public EntityBlockDestroyer(BlockDisplay blockDisplay, ArmorStand armorStand ,EntityBlockDestroyAnimationKeyType type){
        this(blockDisplay, type);
        this.armorStand = armorStand;
    }
    public EntityBlockDestroyer(BlockDisplay blockDisplay, ArmorStand armorStand ,EntityBlockDestroyAnimationKeyType type,int dist){
        this(blockDisplay, armorStand ,type);
        this.dist = dist;
    }

    @Override
    public void run() {

        everyRun();

        Location location = blockDisplay.getLocation().clone().add(0,speed,0);
        if(armorStand !=null){
            armorStand.teleport(location);
        }
        blockDisplay.teleport(location);
        speed+=acceleration;

        if(speed < 0){
            if(blockDisplay.getY() < y){
                blockDisplay.remove();
                if(armorStand!=null) {
                    for (Entity entity : armorStand.getPassengers()) {
                        entity.remove();
                    }
                    armorStand.remove();
                }
                finalRun();
                cancel();
            }
        }else {
            if(blockDisplay.getY() > y){
                blockDisplay.remove();
                if(armorStand!=null) {
                    for (Entity entity : armorStand.getPassengers()) {
                        entity.remove();
                    }
                    armorStand.remove();
                }
                finalRun();
                cancel();
            }
        }
    }

    public void everyRun(){

    }

    public void finalRun(){

    }
}
