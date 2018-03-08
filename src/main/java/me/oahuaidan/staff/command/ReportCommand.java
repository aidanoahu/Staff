package me.oahuaidan.staff.command;

import me.oahuaidan.staff.Staff;
import me.oahuaidan.staff.util.Basic;
import me.oahuaidan.staff.util.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportCommand extends CommandBase {

    public ReportCommand(Staff staff) {
        super(staff, "report", "/(command) <player>");
    }

    protected void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;

        if (args.length <= 1) {
            p.sendMessage(Basic.translate("&cUsage: " + getUsage()));
        }

        String msg = "";
        for (int i = 1; i < args.length; i++) {
            msg = msg + args[i] + " ";
        }

        if (args.length >= 2) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                p.sendMessage(Basic.translate("&cPlease specify a valid player."));
                return;
            }

            if (target == p) {
                p.sendMessage(Basic.translate("&cYou cannot report yourself."));
                return;
            }

            Basic.broadcast(Basic.translate(Staff.getInstance().getConfig().getString("messages.report")
                    .replace("%reporter", p.getName())
                    .replace("%offender", target.getName())
                    .replace("%reason", msg))
                    , "staff.command.mod");

            p.sendMessage(Basic.translate("&dPlayer has been reported."));
        }
    }
}
