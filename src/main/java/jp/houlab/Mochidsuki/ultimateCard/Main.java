package jp.houlab.Mochidsuki.ultimateCard;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    static public Plugin plugin;
    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new IgnitionListener(),this);
        getServer().getPluginManager().registerEvents(new TakeOffListener(),this);
        plugin = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    static public void setCoolDown(Player player, int seconds){
        player.setCooldown(Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE,seconds*20);
    }
}
