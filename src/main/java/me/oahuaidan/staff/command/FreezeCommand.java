package me.oahuaidan.staff.command;

import lombok.Getter;
import me.oahuaidan.staff.Staff;
import me.oahuaidan.staff.util.Basic;
import me.oahuaidan.staff.util.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FreezeCommand extends CommandBase {

    public FreezeCommand(Staff staff) {
        super(staff, "freeze", "/(command) <player>");
    }

    @Getter private static List<UUID> frozen = new ArrayList<>();

    protected void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Basic.translate("&cUsage: " + getUsage()));
        }

        if (args.length >= 1) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage(Basic.translate("&cPlease specify a valid player!"));
            }

            if (!frozen.contains(target.getUniqueId())) {
                frozen.add(target.getUniqueId());
                sender.sendMessage(Basic.translate("&5" + target.getName() + " &dhas been frozen."));
            } else {
                frozen.remove(target.getUniqueId());
                sender.sendMessage(Basic.translate("&5" + target.getName() + " &dhas been unfrozen."));
            }
        }
    }
}
