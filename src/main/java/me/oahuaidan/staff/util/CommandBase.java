package me.oahuaidan.staff.util;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandBase implements CommandExecutor {

    @Getter private JavaPlugin p;
    @Getter private String command;
    @Getter private String permission;
    @Getter private String usage;

    public CommandBase(JavaPlugin p, String command, String usage) {
        this.p = p;
        this.command = command;
        this.usage = usage.replace("(command)", command);
        this.permission = (p.getName() + ".command." + command);
    }

    private void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission(getPermission())) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return;
        }
        execute(sender, args);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase(getCommand())) {
            run(commandSender, strings);
            return true;
        }
        return false;
    }

    protected void execute(CommandSender sender, String[] args) {}
}
