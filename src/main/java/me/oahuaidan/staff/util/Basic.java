package me.oahuaidan.staff.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Basic {

    public static String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void broadcast(String message, String permission) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.hasPermission(permission)) {
                p.sendMessage(translate(message));
            }
        }
    }

    public static ItemStack setName(ItemStack stack, String name) {
        ItemMeta m = stack.getItemMeta();
        m.setDisplayName(Basic.translate(name));
        stack.setItemMeta(m);
        return stack;
    }

    public static void registerListeners(JavaPlugin pl, Listener... listeners) {
        Arrays.asList(listeners).forEach(l -> pl.getServer().getPluginManager().registerEvents(l, pl));
    }

    public static void registerCommands(JavaPlugin pl, CommandBase... commandBases) {
        Arrays.asList(commandBases).forEach(c -> pl.getCommand(c.getCommand()).setExecutor(c));
    }
}
