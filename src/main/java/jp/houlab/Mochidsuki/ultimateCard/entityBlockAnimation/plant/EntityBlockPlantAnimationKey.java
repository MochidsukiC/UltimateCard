package jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.plant;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.BlockDisplay;

import javax.annotation.Nullable;

public class EntityBlockPlantAnimationKey {
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    private int time;

    private int x;
    private int y;


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public BlockData getBlockData() {
        return blockData;
    }

    public void setBlockData(BlockData blockData) {
        this.blockData = blockData;
    }

    public boolean isCollision() {
        return isCollision;
    }

    public void setCollision(boolean collision) {
        isCollision = collision;
    }

    private int z;
    private BlockData blockData;
    private boolean isCollision;

    public EntityBlockPlantAnimationKeyType getType() {
        return type;
    }

    public void setType(EntityBlockPlantAnimationKeyType type) {
        this.type = type;
    }

    private EntityBlockPlantAnimationKeyType type;

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    private int dist;

    public @Nullable Particle getParticle() {
        return particle;
    }

    public void setParticle(Particle particle) {
        this.particle = particle;
    }

    Particle particle;




    public EntityBlockPlantAnimationKey(int time,int x, int y, int z, BlockData blockData, boolean isCollision,EntityBlockPlantAnimationKeyType type){
        this.time = time;
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockData = blockData;
        this.isCollision = isCollision;
        this.type = type;
    }

    public EntityBlockPlantAnimationKey(int time,int x, int y, int z, BlockData blockData, boolean isCollision,EntityBlockPlantAnimationKeyType type, int dist) {
        this(time,x,y,z,blockData,isCollision,type);

        this.dist = dist;
    }

    public EntityBlockPlantAnimationKey(int time,int x, int y, int z, BlockData blockData, boolean isCollision,EntityBlockPlantAnimationKeyType type, int dist,Particle particle) {
        this(time,x,y,z,blockData,isCollision,type);

        this.dist = dist;
        this.particle = particle;
     }

    public EntityBlockPlantAnimationKey(int time,int x, int y, int z, BlockData blockData, boolean isCollision,EntityBlockPlantAnimationKeyType type, int dist,Particle particle,Sound sound) {
        this(time,x,y,z,blockData,isCollision,type);

        this.dist = dist;
        this.particle = particle;

    }

        public enum EntityBlockPlantAnimationKeyType{
        NORMAL,
        DOWN_FADE_IN,
        UP_FADE_IN,
        DROP_IN,
    }

    public void everyRun(BlockDisplay blockDisplay, ArmorStand armorStand){

    }

    public void finalRun(BlockDisplay blockDisplay, ArmorStand armorStand){

    }



}
