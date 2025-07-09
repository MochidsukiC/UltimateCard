package jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.move;

import jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.plant.EntityBlockPlantAnimationKey;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;

import javax.annotation.Nullable;

public class EntityBlockMoveAnimationKey {



    private double x;
    private double y;


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    private double z;

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    private double size;

    public @Nullable Particle getParticle() {
        return particle;
    }

    public void setParticle(Particle particle) {
        this.particle = particle;
    }

    private Particle particle;


    public EntityBlockMoveAnimationKeyType getTypeX() {
        return typeX;
    }

    public void setTypeX(EntityBlockMoveAnimationKeyType typeX) {
        this.typeX = typeX;
    }

    private EntityBlockMoveAnimationKeyType typeX;

    public EntityBlockMoveAnimationKeyType getTypeY() {
        return typeY;
    }

    public void setTypeY(EntityBlockMoveAnimationKeyType typeY) {
        this.typeY = typeY;
    }

    public EntityBlockMoveAnimationKeyType getTypeZ() {
        return typeZ;
    }

    public void setTypeZ(EntityBlockMoveAnimationKeyType typeZ) {
        this.typeZ = typeZ;
    }

    public EntityBlockMoveAnimationKeyType getTypeSize() {
        return typeSize;
    }

    public void setTypeSize(EntityBlockMoveAnimationKeyType typeSize) {
        this.typeSize = typeSize;
    }

    private EntityBlockMoveAnimationKeyType typeY;
    private EntityBlockMoveAnimationKeyType typeZ;
    private EntityBlockMoveAnimationKeyType typeSize;



    public EntityBlockMoveAnimationKey( double x, double y, double z,double size ,EntityBlockMoveAnimationKeyType typeX,EntityBlockMoveAnimationKeyType typeY,EntityBlockMoveAnimationKeyType typeZ,EntityBlockMoveAnimationKeyType typeS){
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
        this.typeX = typeX;
        this.typeY = typeY;
        this.typeZ = typeZ;
        this.typeSize = typeS;
    }

    public EntityBlockMoveAnimationKey(double x, double y, double z,double size,EntityBlockMoveAnimationKeyType typeX,EntityBlockMoveAnimationKeyType typeY,EntityBlockMoveAnimationKeyType typeZ,EntityBlockMoveAnimationKeyType typeS,Particle particle){
        this(x, y, z, size, typeX,typeY,typeZ,typeS);
        this.particle = particle;
    }


}

