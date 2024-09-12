package jp.houlab.Mochidsuki.ultimateCard.respawn;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class RespawnMain {
    public void startRespawn(Player player) {
        if(player.getScoreboard().getPlayerTeam(player) != null){
            //死者のみをピックアップ
            List<Player> ghosts = new ArrayList<>();
            for(String name : player.getScoreboard().getPlayerTeam(player).getEntries()){
                if(Bukkit.getPlayer(name) != null && Bukkit.getPlayer(name).isOnline()){
                    Player player1 = Bukkit.getPlayer(name);
                    if((player1.getGameMode().equals(GameMode.CREATIVE) || player1.getGameMode().equals(GameMode.SPECTATOR)) && ) {
                        ghosts.add(player1);
                    }
                }
            }



        }
    }
}

class RespawnAnimation extends BukkitRunnable{

    @Override
    public void run() {

    }
}
