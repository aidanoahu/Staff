package me.oahuaidan.staff.listener.runnable;

import me.oahuaidan.staff.Staff;
import me.oahuaidan.staff.util.Basic;
import me.oahuaidan.staff.command.FreezeCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FreezeRunnable extends BukkitRunnable {

    @Override
    public void run() {

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (FreezeCommand.getFrozen().contains(p.getUniqueId())) {
                p.sendMessage(" ");
                p.sendMessage(" ");
                p.sendMessage(Basic.translate("&d&lYOU ARE FROZEN!"));
                p.sendMessage(" ");
                p.sendMessage(Basic.translate("&7You have 5 minutes to join &d&o" + Staff.getInstance().getConfig().getString("freeze-teamspeak")));
                p.sendMessage(" ");
                p.sendMessage(" ");
            }
        }
    }
}
