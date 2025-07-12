package jp.houlab.Mochidsuki.ultimateCard.entityBlockAnimation.move;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Shulker;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EntityBlockMover extends BukkitRunnable {

    int time;
    float yaw;

    BlockDisplay blockDisplay;
    ArmorStand armorStand;
    Map<Integer,EntityBlockMoveAnimationKey> keys;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<EntityBlockMoveAnimationKey> getInterpolatedKeys() {
        return interpolatedKeys;
    }

    public void setInterpolatedKeys(List<EntityBlockMoveAnimationKey> interpolatedKeys) {
        this.interpolatedKeys = interpolatedKeys;
    }

    public Map<Integer, EntityBlockMoveAnimationKey> getKeys() {
        return keys;
    }

    public void setKeys(Map<Integer, EntityBlockMoveAnimationKey> keys) {
        this.keys = keys;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public void setArmorStand(ArmorStand armorStand) {
        this.armorStand = armorStand;
    }

    public BlockDisplay getBlockDisplay() {
        return blockDisplay;
    }

    public void setBlockDisplay(BlockDisplay blockDisplay) {
        this.blockDisplay = blockDisplay;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    List<EntityBlockMoveAnimationKey> interpolatedKeys;
    Location location;


    public EntityBlockMover(Location location, float yaw , BlockDisplay block, TreeMap<Integer,EntityBlockMoveAnimationKey> keys) {
        this.location = location;
        blockDisplay = block;
        this.keys = keys;
        this.yaw = yaw;

        if (keys == null || keys.size() < 2) {
            throw new IllegalArgumentException("Keys must be over 2");
        }
        TreeMap<Integer, EntityBlockMoveAnimationKey> interpolatedKeys = new TreeMap<>(keys);
        List<Integer> ticks = new ArrayList<>(keys.keySet());

        for (int i = 0; i < ticks.size() - 1; i++) {
            int startTick = ticks.get(i);
            int endTick = ticks.get(i + 1);

            EntityBlockMoveAnimationKey startKey = keys.get(startTick);
            EntityBlockMoveAnimationKey endKey = keys.get(endTick);

            int duration = endTick - startTick;
            if (duration <= 1) continue;

            for (int currentTick = startTick + 1; currentTick < endTick; currentTick++) {
                double progress = (double) (currentTick - startTick) / duration;

                // 各パラメータに対応する補間モードを使って、個別に進捗率を計算
                double easedProgressX = applyEasing(progress, endKey.getTypeX());
                double easedProgressY = applyEasing(progress, endKey.getTypeY());
                double easedProgressZ = applyEasing(progress, endKey.getTypeZ());
                double easedProgressS = applyEasing(progress, endKey.getTypeSize());

                // 各値を計算
                double newX = startKey.getX() + (endKey.getX() - startKey.getX()) * easedProgressX;
                double newY = startKey.getY() + (endKey.getY() - startKey.getY()) * easedProgressY;
                double newZ = startKey.getZ() + (endKey.getZ() - startKey.getZ()) * easedProgressZ;
                double newSize = startKey.getSize() + (endKey.getSize() - startKey.getSize()) * easedProgressS;

                // 新しいキーを生成。パーティクルは前のキー(startKey)から引き継ぐ
                interpolatedKeys.put(currentTick, new EntityBlockMoveAnimationKey(
                        newX, newY, newZ, newSize,
                        endKey.getTypeX(), endKey.getTypeY(), endKey.getTypeZ(), endKey.getTypeSize(),
                        startKey.getParticle() // ★前のキーのパーティクルを反映
                ));
            }
        }
    }
    public EntityBlockMover(Location location, float yaw , BlockDisplay block, ArmorStand armorStand, TreeMap<Integer,EntityBlockMoveAnimationKey> keys) {
        this(location, yaw ,block, keys);
        this.armorStand = armorStand;
    }

    /**
     * イージング関数を適用するヘルパーメソッド
     */
    private static double applyEasing(double t, EntityBlockMoveAnimationKeyType type) {
        switch (type) {
            case FADE_IN:
                return t * t; // Quadratic Ease In
            case FADE_OUT:
                return 1 - (1 - t) * (1 - t); // Quadratic Ease Out
            case FADE_IN_OUT:
                // Sigmoid-based Smoothstep
                return t < 0.5 ? 2 * t * t : 1 - Math.pow(-2 * t + 2, 2) / 2;
            case LINEAR:
            default:
                return t;
        }
    }

    @Override
    public void run() {
        EntityBlockMoveAnimationKey key = interpolatedKeys.get(time);
        Location location = this.location.clone().add(new Vector(key.getX()*-1,key.getY(),key.getZ()).rotateAroundY(yaw));
        blockDisplay.teleport(location);
        if(armorStand != null){
            if(armorStand.getPassengers().get(0) instanceof Shulker)armorStand.removePassenger(armorStand.getPassengers().get(0));
            armorStand.teleport(location);
            if(armorStand.getPassengers().get(0) instanceof Shulker)armorStand.addPassenger(armorStand.getPassengers().get(0));
        }

        if(key.getParticle() != null){
            location.getWorld().spawnParticle(key.getParticle(),location,10);
        }

        everyRun();

        time++;
        if(interpolatedKeys.size()>=time){
            finalRun();
            cancel();
        }
    }

    public void everyRun(){

    }

    public void finalRun(){

    }
}
