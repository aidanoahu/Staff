package me.oahuaidan.staff.command;

import me.oahuaidan.staff.Staff;
import me.oahuaidan.staff.util.Basic;
import me.oahuaidan.staff.util.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PanicCommand extends CommandBase {

    public PanicCommand(Staff staff) {
        super(staff, "panic", "/(command)");
    }

    protected void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (!FreezeCommand.getFrozen().contains(p.getUniqueId())) {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "freeze " + p.getName());
            p.sendMessage(Basic.translate("&dPanic mode has been enabled. Staff have been notified."));
            Basic.broadcast(Basic.translate("&7[&5STAFF&7] &d" + p.getName() + " &7has &aentered &d&lPANIC MODE&7."), "staff.command.mod");
        } else {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "freeze " + p.getName());
            p.sendMessage(Basic.translate("&dPanic mode has been disabled."));
            Basic.broadcast(Basic.translate("&7[&5STAFF&7] &d" + p.getName() + " &7has &cleft &d&lPANIC MODE&7."), "staff.command.mod");
        }
    }
}
