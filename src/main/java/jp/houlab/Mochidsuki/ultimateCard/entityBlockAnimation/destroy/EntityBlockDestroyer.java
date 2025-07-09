package jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.destroy;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityBlockDestroyer extends BukkitRunnable {

    BlockDisplay blockDisplay;
    ArmorStand armorStand;
    int time;
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
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

    int nowTime;

    public EntityBlockDestroyer(BlockDisplay blockDisplay, EntityBlockDestroyAnimationKeyType type){
        this.blockDisplay = blockDisplay;
        switch (type){
            case DROP_OUT:{
                speed = 0;
                acceleration = -0.048f;
                break;
            }
            case FADE_UP:{
                speed = 0.1f;
                break;
            }
            case FADE_DOWN:{
                speed = -0.1f;
                break;
            }
        }

    }

    public EntityBlockDestroyer(BlockDisplay blockDisplay, ArmorStand armorStand ,EntityBlockDestroyAnimationKeyType type){
        this(blockDisplay, type);
        this.armorStand = armorStand;

        if(armorStand!=null) {
            for (Entity entity : armorStand.getPassengers()) {
                entity.remove();
            }
            armorStand.remove();
        }
    }
    public EntityBlockDestroyer(BlockDisplay blockDisplay, ArmorStand armorStand ,EntityBlockDestroyAnimationKeyType type,int time){
        this(blockDisplay, armorStand ,type);
        this.time = time;
    }

    @Override
    public void run() {

        everyRun();

        Location location = blockDisplay.getLocation().clone().add(0,speed,0);

        blockDisplay.teleport(location);
        speed+=acceleration;

        if(nowTime>=time){
            blockDisplay.remove();
            finalRun();
            cancel();
        }

        nowTime++;
    }

    public void everyRun(){

    }

    public void finalRun(){

    }
}
