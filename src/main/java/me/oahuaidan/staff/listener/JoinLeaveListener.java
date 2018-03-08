package me.oahuaidan.staff.listener;

import me.oahuaidan.staff.Staff;
import me.oahuaidan.staff.util.Basic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("staff.command.mod")) {
            Basic.broadcast(Basic.translate(Staff.getInstance().getConfig().getString("messages.staff-join").replace("%staff", p.getName())), "staff.command.mod");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("staff.command.mod")) {
            Basic.broadcast(Basic.translate(Staff.getInstance().getConfig().getString("messages.staff-quit").replace("%staff", p.getName())), "staff.command.mod");
        }
    }

}
