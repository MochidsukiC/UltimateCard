package jp.houlab.Mochidsuki.ultimateCard.hospital;

import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import static jp.houlab.Mochidsuki.ultimateCard.Main.plugin;

public class HospitalMain {
    private final Location location;
    private int times;
    public HospitalMain(Location location) {
        this.location = location;
    }

    public void Main(){
        new BukkitRunnable() {
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){

                        for(int i = times*10; i < (times+1)*10; i++){
                            double rad = Math.toRadians(i);
                            player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, location.clone().add(new Vector(Math.cos(rad)*5,0,Math.sin(rad)*5)),3,0,0,0,1,new Particle.DustTransition(Color.RED,Color.fromRGB(234,145,152),2));
                        }

                        if(location.distance(player.getLocation()) <= 5){
                            if(times%40 == 1) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 3));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 40, 0));

                            }
                            player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY,player.getLocation().add(new Vector(0,1,0)),1,0.5,1,0.5);
                        }

                }
                if(times >= 300){
                    cancel();
                }
                times++;
            }
        }.runTaskTimer(plugin,1,1);
    }
}
