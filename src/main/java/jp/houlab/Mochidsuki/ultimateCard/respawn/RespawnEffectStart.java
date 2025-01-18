package jp.houlab.Mochidsuki.ultimateCard.respawn;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static jp.houlab.Mochidsuki.ultimateCard.Main.config;

public class RespawnEffectStart extends BukkitRunnable {
    private Player player;
    private Location location;
    private double pitch = Math.toRadians(90);
    private double yaw = 0;
    private int times;
    RespawnEffectStart(Player player, Location location, int times) {
        this.player = player;
        this.location = location;
        this.times = times;

        location.getWorld().playSound(location,Sound.ENTITY_WITHER_SPAWN,100,1);
    }
    @Override
    public void run() {
        spawnParticle();
        times++;
    }

    private void spawnParticle(){
        final int r = 5;
        double tan72 = Math.tan(Math.toRadians(72));
        double tan36 = Math.tan(Math.toRadians(36));
        double tan18 = Math.tan(Math.toRadians(18));
        double x = 2*r*Math.cos(Math.toRadians(18));


        //fA
        for(double a = -1*x/2; a<x/2; a++){
            double b;
            b =r*Math.sin(Math.toRadians(18));
            location.getWorld().spawnParticle(Particle.END_ROD,location.clone().add(new Vector(a/10,b/10,0).rotateAroundZ(Math.toRadians(times)).rotateAroundX(pitch).rotateAroundY(yaw)),3,0,0,0,0);
        }

        //fB
        for(double a = -1*x/2; a< (-1 * x) /2 + x*Math.cos(Math.toRadians(36)) ; a++){
            double b;
            b = -1*a*tan36 - (x/2*tan36 - x/2*tan18);
            location.getWorld().spawnParticle(Particle.END_ROD,location.clone().add(new Vector(a/10,b/10,0).rotateAroundZ(Math.toRadians(times)).rotateAroundX(pitch).rotateAroundY(yaw)),3,0,0,0,0);
        }

        //fC
        for(double a = 0; a< (-1 * x) /2 + x*Math.cos(Math.toRadians(36)) ; a++){
            double b;
            b = -1*a*tan72 + r;
            location.getWorld().spawnParticle(Particle.END_ROD,location.clone().add(new Vector(a/10,b/10,0).rotateAroundZ(Math.toRadians(times)).rotateAroundX(pitch).rotateAroundY(yaw)),3,0,0,0,0);
        }

        //fD
        for(double a = (int) (x/2 - x*Math.cos(Math.toRadians(36))); a< 0; a++){
            double b;
            b = a*tan72 + r;
            location.getWorld().spawnParticle(Particle.END_ROD,location.clone().add(new Vector(a/10,b/10,0).rotateAroundZ(Math.toRadians(times)).rotateAroundX(pitch).rotateAroundY(yaw)),3,0,0,0,0);
        }

        //fE
        for(double a = x/2 - x*Math.cos(Math.toRadians(36)); a< x/2; a++){
            double b;
            b = (int) (a*tan36 - (x/2*tan36 - x/2*tan18));
            location.getWorld().spawnParticle(Particle.END_ROD,location.clone().add(new Vector(a/10,b/10,0).rotateAroundZ(Math.toRadians(times)).rotateAroundX(pitch).rotateAroundY(yaw)),3,0,0,0,0);
        }

        //fF
        for(double a = 0; a<360;a = a + 2){
            double b;
            double c;
            c = r*Math.cos(Math.toRadians(a));
            b = r*Math.sin(Math.toRadians(a));
            Location locationTemp = location.clone();
            location.getWorld().spawnParticle(Particle.END_ROD,locationTemp.clone().add(new Vector(c/10,b/10,0).rotateAroundZ(Math.toRadians(times)).rotateAroundX(pitch).rotateAroundY(yaw)),3,0,0,0,0);
            location.getWorld().spawnParticle(Particle.END_ROD,locationTemp.clone().add(new Vector(c/8,b/8,0).rotateAroundZ(Math.toRadians(times)).rotateAroundX(pitch).rotateAroundY(yaw)),3,0,0,0,0);
            location.getWorld().spawnParticle(Particle.END_ROD,locationTemp.clone().add(new Vector(c/40,b/40,0).rotateAroundZ(Math.toRadians(times)).rotateAroundX(pitch).rotateAroundY(yaw)),3,0,0,0,0);

        }
        final int prepareTime = config.getInt("Ultimate.prepareTime");
        if(times < prepareTime){
            if(times <= prepareTime/5){
                spawnCircleParticle(Particle.DUST_COLOR_TRANSITION,times*10,r,9,new Particle.DustTransition(Color.BLUE,Color.AQUA,2));
            } else if (times <= prepareTime*2/5) {
                spawnCircleParticle(Particle.DUST_COLOR_TRANSITION,times*10,r,9,new Particle.DustTransition(Color.AQUA,Color.GREEN,2));
            } else if (times <= prepareTime*3/5) {
                spawnCircleParticle(Particle.DUST_COLOR_TRANSITION,times*10,r,9,new Particle.DustTransition(Color.GREEN,Color.YELLOW,2));
            } else if (times <= prepareTime*4/5) {
                spawnCircleParticle(Particle.DUST_COLOR_TRANSITION,times*10,r,9,new Particle.DustTransition(Color.YELLOW,Color.ORANGE,2));
            }else {
                spawnCircleParticle(Particle.DUST_COLOR_TRANSITION,times*10,r,9,new Particle.DustTransition(Color.ORANGE,Color.RED,2));
            }
        } else if (times>=prepareTime) {
            new RespawnEnd();
        }

    }

    private void spawnCircleParticle(Particle particle, int k, int r, int scale, Particle.DustOptions options){
        double b;
        double c;
        for(int a = 0; a < 10;a++) {
            c = r * Math.cos(Math.toRadians(k+a));
            b = r * Math.sin(Math.toRadians(k+a));
            Location locationTemp = location.clone();
            location.getWorld().spawnParticle(particle, locationTemp.clone().add(new Vector(c / scale, b / scale, 0).rotateAroundX(pitch).rotateAroundY(yaw)), 3, 0, 0, 0, 0, options);
        }
    }
}
