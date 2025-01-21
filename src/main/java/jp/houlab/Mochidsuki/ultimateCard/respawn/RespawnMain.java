package jp.houlab.Mochidsuki.ultimateCard.respawn;

import jp.houlab.mochidsuki.gamemap.GiveMap;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import static jp.houlab.Mochidsuki.ultimateCard.Main.config;

public class RespawnMain extends BukkitRunnable {
    private Player player;
    private Location location;
    private double pitch = Math.toRadians(90);
    private double yaw = 0;
    private int times;
    public RespawnMain(Player player, Location location, int times) {
        this.player = player;
        this.location = location;
        this.times = times;

        location.getWorld().playSound(location,Sound.ENTITY_WITHER_SPAWN,100,1);
    }
    @Override
    public void run() {
        spawnParticle();
        float p = (float) (times / config.getDouble("Respawn.prepareTime"));
        if(times%10 == 1) {
            location.getWorld().playSound(location, Sound.ENTITY_WITHER_SPAWN, 0.3f, p);
        }
        times++;
    }

    private void spawnParticle(){
        final int r = 30;
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
        final int prepareTime = config.getInt("Respawn.prepareTime");
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


            if(times == prepareTime - 40){
                location.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE,location,10000,0,0,0,20);
            }
        } else if (times>=prepareTime) {
            if(!player.getGameMode().equals(GameMode.SPECTATOR)) {
                respawnEnd();
            }
            cancel();
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

    private void respawnEnd(){
        player.getWorld().strikeLightningEffect(location);
        player.getWorld().playSound(location,Sound.ENTITY_LIGHTNING_BOLT_THUNDER,1,1);


        Team team = player.getScoreboard().getPlayerTeam(player);
        for(String name : team.getEntries()) {
            if(player.getServer().getPlayer(name) != null && player.getServer().getPlayer(name).isOnline() && !player.getName().equals(name)) {
                Player player1 = player.getServer().getPlayer(name);
                player1.teleport(player1);

                Color c = Color.fromRGB(player1.getScoreboard().getPlayerTeam(player1).getColor().asBungee().getColor().getRed(),player1.getScoreboard().getPlayerTeam(player1).getColor().asBungee().getColor().getGreen(),player1.getScoreboard().getPlayerTeam(player1).getColor().asBungee().getColor().getBlue());
                ItemStack i = new ItemStack(Material.LEATHER_LEGGINGS);
                LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
                meta.setColor(c);
                i.setItemMeta(meta);
                player1.getInventory().setItem(35,i);
                player1.getInventory().setItem(22,new ItemStack(Material.LEATHER_HELMET));
                player1.getInventory().setItem(23,new ItemStack(Material.LEATHER_CHESTPLATE));
                player1.getInventory().setItem(24,new ItemStack(Material.LEATHER_BOOTS));


                GiveMap.giveBig(player1);
                GiveMap.giveMini(player1);
                player1.setGameMode(GameMode.SURVIVAL);
            }
        }
    }
}
