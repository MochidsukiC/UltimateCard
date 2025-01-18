package jp.houlab.Mochidsuki.ultimateCard;

import jp.houlab.Mochidsuki.ultimateCard.takeoff.TakeOffCommandListener;
import jp.houlab.Mochidsuki.ultimateCard.takeoff.TakeOffListener;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    static public Plugin plugin;
    static public FileConfiguration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new IgnitionListener(),this);
        getServer().getPluginManager().registerEvents(new TakeOffListener(),this);
        plugin = this;
        saveDefaultConfig();
        config = getConfig();

        getCommand("takeoff").setExecutor(new TakeOffCommandListener());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    static public void setCoolDown(Player player, int seconds){
        player.setCooldown(Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE,seconds*20);
        player.setCooldown(Material.VEX_ARMOR_TRIM_SMITHING_TEMPLATE,seconds*20);
    }
}
