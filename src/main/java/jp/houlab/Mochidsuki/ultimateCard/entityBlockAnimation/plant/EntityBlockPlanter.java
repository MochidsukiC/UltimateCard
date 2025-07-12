package jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.plant;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Shulker;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;


import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static jp.houlab.Mochidsuki.ultimateCard.Main.plugin;

public class EntityBlockPlanter extends BukkitRunnable {
    int times;
    int maxTime;

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public List<EntityBlockPlantAnimationKey> getKeys() {
        return keys;
    }

    public void setKeys(List<EntityBlockPlantAnimationKey> keys) {
        this.keys = keys;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public Particle getParticle() {
        return particle;
    }

    public Sound getSound() {
        return sound;
    }

    List<EntityBlockPlantAnimationKey> keys;
    Location location;
    double yaw;
    final Particle particle;
    final Sound sound;

    public static final Map<UUID,UUID> ShulkerToBlockDisplay = new HashMap<>();


    public EntityBlockPlanter(int maxTime, List<EntityBlockPlantAnimationKey> keys, Location location, float yaw, @Nullable Particle defaultParticle, @Nullable Sound defaultSound) {
        this.maxTime = maxTime;
        this.keys = keys;
        this.location = location.clone();
        this.location.setPitch(0);
        this.location.setYaw(0);
        this.particle = defaultParticle;
        this.sound = defaultSound;

        if(yaw > -45){
            if(yaw < 45){
                this.yaw = 0;
            }else if(yaw < 135){
                this.yaw = Math.PI/2;
            }else {
                this.yaw = Math.PI;
            }
        }else {
            if(yaw > -135){
                this.yaw = -Math.PI/2;
            }else {
                this.yaw = Math.PI;
            }
        }
    }

    @Override
    public void run() {
        for(EntityBlockPlantAnimationKey key : keys){
            if(key.getTime() == times) {

                BlockDisplay blockDisplay = location.getWorld().spawn(location.clone().add(new Vector(key.getX()*-1,key.getY()+key.getDist(),key.getZ()).rotateAroundY(yaw)), BlockDisplay.class);
                blockDisplay.setBlock(key.getBlockData());


                Transformation transformation = blockDisplay.getTransformation();
                transformation.getTranslation().set(-0.5,0,-0.5);
                blockDisplay.setTransformation(transformation);

                ArmorStand armorStand;
                if(key.isCollision()){
                    double y = key.getDist();
                    if(key.isFPSMode()){
                        y = 0;
                    }
                    armorStand = location.getWorld().spawn(location.clone().add(new Vector(key.getX()*-1,key.getY()+y,key.getZ()).rotateAroundY(yaw)), ArmorStand.class);

                    armorStand.setMarker(true);
                    armorStand.setGravity(false);
                    armorStand.setInvisible(true);
                    armorStand.setCollidable(false);
                    armorStand.setInvulnerable(true);


                    Shulker shulker = location.getWorld().spawn(location.clone(), Shulker.class);

                    shulker.setAI(false);
                    shulker.setInvisible(true);
                    shulker.setSilent(true);

                    armorStand.addPassenger(shulker);

                    EntityBlockPlanter.ShulkerToBlockDisplay.put(shulker.getUniqueId(),blockDisplay.getUniqueId());


                }else {
                    armorStand = null;
                }

                switch (key.getType()){
                    case DROP_IN:{
                        new MoveBlock(0,-0.048f,armorStand,blockDisplay,key,location.getY()+ key.getY()).runTaskTimer(plugin,1,1);
                        break;
                    }
                    case UP_FADE_IN:{
                        new MoveBlock(0.2f,0,armorStand,blockDisplay,key,location.getY()+ key.getY()).runTaskTimer(plugin,1,1);
                        break;
                    }
                    case DOWN_FADE_IN:{
                        new MoveBlock(-0.2f,0,armorStand,blockDisplay,key,location.getY()+ key.getY()).runTaskTimer(plugin,1,1);
                        break;
                    }
                }

            }
        }

        times++;
        if(times>maxTime){
            cancel();
        }
    }
}

class MoveBlock extends BukkitRunnable{
    float speed;
    ArmorStand armorStand;
    BlockDisplay blockDisplay;
    float acceleration;
    EntityBlockPlantAnimationKey key;
    Shulker shulker;
    double y;

    public MoveBlock(float firstspeed, float acceleration, ArmorStand armorStand, BlockDisplay blockDisplay, EntityBlockPlantAnimationKey key,double y){
        this.armorStand = armorStand;
        this.blockDisplay = blockDisplay;
        this.speed = firstspeed;
        this.acceleration = acceleration;
        this.key = key;
        this.y = y;

        if(armorStand!=null){
            Location startLocation = blockDisplay.getLocation().clone().add(0,key.getDist(),0);
            armorStand.teleport(startLocation);
            if(!armorStand.getPassengers().isEmpty() && armorStand.getPassengers().get(0) instanceof Shulker){
                this.shulker = (Shulker) armorStand.getPassengers().get(0);
                armorStand.addPassenger(this.shulker);
            }
        }
        blockDisplay.teleport(blockDisplay.getLocation().clone().add(0,key.getDist(),0));

        key.plantRun(blockDisplay,armorStand);
    }
    @Override
    public void run() {


        Location location = blockDisplay.getLocation().clone().add(0,speed,0);
        if(!key.isFPSMode() && armorStand !=null){
            if(shulker!=null)armorStand.removePassenger(shulker);
            armorStand.teleport(location);
            if(shulker!=null)armorStand.addPassenger(shulker);
        }
        blockDisplay.teleport(location);

        key.everyRun(blockDisplay,armorStand);

        if(Math.abs(blockDisplay.getY() - y) <= speed){

            Location finalLocation = blockDisplay.getLocation().clone();
            finalLocation.setY(y);

            if(!key.isFPSMode() && armorStand !=null){
                if(shulker!=null)armorStand.removePassenger(shulker);
                armorStand.teleport(finalLocation);
                if(shulker!=null)armorStand.addPassenger(shulker);
            }
            blockDisplay.teleport(finalLocation);
            key.finalRun(blockDisplay,armorStand);
            cancel();
        }

        speed+=acceleration;

    }

}

