package jp.houlab.Mochidsuki.ultimateCard.respawn;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class RespawnListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if(!event.isCancelled()){

        }
    }
}
