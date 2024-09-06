package jp.houlab.Mochidsuki.ultimateCard.TakeOff;

import jp.houlab.mochidsuki.elytra_jetpacker.CommandListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * アルティメットアビリティ・テイクオフのコマンドリスナー
 * @author Mochidsuki
 */
public class TakeOffCommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(command.getName().equalsIgnoreCase("takeoff")) {
            if(strings.length == 2) {
                if(strings[0].equalsIgnoreCase("jumpwithteammate")) {//チームメイトの離陸
                    Player player = (Player) commandSender;
                    if(player.getScoreboard().getPlayerTeam(player) != null) {//プレイヤーがチームに属していたら
                        //チームメイトが離陸準備中か確認
                        if(Bukkit.getPlayer(strings[1]) != null && Bukkit.getPlayer(strings[1]).isOnline()) {
                            Player executor = Bukkit.getPlayer(strings[1]);
                            if(TakeOffMain.holdingTask.containsKey(executor)) {
                                if(player.getLocation().distance(executor.getLocation()) <= 4){
                                    if(!TakeOffMain.holdingTask.containsKey(player)) {
                                        TakeOffMain.holdTakeOff(player,false);
                                    }
                                }else {
                                    player.sendMessage("アルティメット使用者から遠すぎます!!");
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
