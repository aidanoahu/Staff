package me.oahuaidan.staff.command;

import me.oahuaidan.staff.Staff;
import me.oahuaidan.staff.util.Basic;
import me.oahuaidan.staff.util.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RequestCommand extends CommandBase {

    public RequestCommand(Staff staff) {
        super(staff, "request", "/(command) <message>");
    }

    protected void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;

        if (args.length == 0) {
            p.sendMessage(Basic.translate("&cUsage: " + getUsage()));
        }

        String msg = "";
        for (int i = 0; i < args.length; i++) {
            msg = msg + args[i] + " ";
        }

        if (args.length >= 1) {
            p.sendMessage(Basic.translate("&dHelp has been requested!"));
            Basic.broadcast(Basic.translate(Staff.getInstance().getConfig().getString("messages.request")
                    .replace("%player", p.getName())
                    .replace("%msg", msg))
                    , "staff.command.mod");
        }
    }
}
