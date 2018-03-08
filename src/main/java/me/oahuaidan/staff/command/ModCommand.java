package me.oahuaidan.staff.command;

import lombok.Getter;
import me.oahuaidan.staff.Staff;
import me.oahuaidan.staff.util.Basic;
import me.oahuaidan.staff.util.CommandBase;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ModCommand extends CommandBase {

    public ModCommand(Staff staff) {
        super(staff, "mod", "/(command)");
    }

    @Getter private static List<UUID> modMode = new ArrayList<>();
    @Getter private static List<UUID> vanished = new ArrayList<>();

    private Map<UUID, ItemStack[]> inventories = new HashMap<>();
    private Map<UUID, ItemStack[]> armor = new HashMap<>();
    private Map<UUID, Location> locations = new HashMap<>();

    private void enable(Player p) {
        ItemStack compass = new ItemStack(Material.COMPASS, 1);
        Basic.setName(compass, "&bCompass");

        ItemStack inspect = new ItemStack(Material.BOOK, 1);
        Basic.setName(inspect, "&bInspect Inventory");

        ItemStack carpet = new ItemStack(Material.CARPET, 1, DyeColor.ORANGE.getData());
        Basic.setName(carpet, " ");

        ItemStack minerTeleport = new ItemStack(Material.DIAMOND, 1);
        Basic.setName(minerTeleport, "&bMiner Teleport");

        ItemStack randomTeleport = new ItemStack(Material.EMERALD, 1);
        Basic.setName(randomTeleport, "&dRandom Teleport");

        ItemStack vanishOn = new ItemStack(Material.SUGAR, 1);
        Basic.setName(vanishOn, "&bToggle Vanish");

        modMode.add(p.getUniqueId());
        vanished.add(p.getUniqueId());
        inventories.put(p.getUniqueId(), p.getInventory().getContents());
        armor.put(p.getUniqueId(), p.getInventory().getArmorContents());
        locations.put(p.getUniqueId(), p.getLocation());

        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.setGameMode(GameMode.CREATIVE);
        p.getInventory().setItem(0, compass);
        p.getInventory().setItem(1, inspect);
        p.getInventory().setItem(2, carpet);
        p.getInventory().setItem(3, randomTeleport);
        p.getInventory().setItem(4, minerTeleport);
        p.getInventory().setItem(8, vanishOn);

        p.sendMessage(Basic.translate(Staff.getInstance().getConfig().getString("messages.modmode-enable")));
    }

    private void disable(Player p) {
        p.getInventory().clear();
        p.setGameMode(GameMode.SURVIVAL);
        p.getInventory().setContents(inventories.get(p.getUniqueId()));
        p.getInventory().setArmorContents(armor.get(p.getUniqueId()));
        p.teleport(locations.get(p.getUniqueId()));

        if (vanished.contains(p.getUniqueId())) vanished.remove(p.getUniqueId());
        modMode.remove(p.getUniqueId());
        inventories.remove(p.getUniqueId());
        armor.remove(p.getUniqueId());
        locations.remove(p.getUniqueId());

        p.sendMessage(Basic.translate(Staff.getInstance().getConfig().getString("messages.modmode-disable")));
    }

    protected void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (modMode.contains(p.getUniqueId())) {
            disable(p);
        } else {
            enable(p);
        }
    }
}
