package me.oahuaidan.staff.listener;

import me.oahuaidan.staff.util.Basic;
import me.oahuaidan.staff.command.ModCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ModModeListener implements Listener {

    private String preventAction = Basic.translate("&cYou cannot do this in mod mode!");
    private String noPermission = Basic.translate("&cNo permission.");

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (ModCommand.getModMode().contains(p.getUniqueId())) {

            // WORLDEDIT COMPASS TELEPORTER
            if (p.getItemInHand().getType() == Material.COMPASS) {
                if (!p.hasPermission("worldedit.navigation.thru.tool")) {
                    p.sendMessage(noPermission);
                }
            }

            // MINER TELEPORT
            if (p.getItemInHand().getType() == Material.DIAMOND) {
                if (!p.hasPermission("staff.minerteleport")) {
                    p.sendMessage(noPermission);
                } else {
                    // Adds all players to a list of players under y level 30, teleports staff member to a random player
                    List<UUID> miners = new ArrayList<>();
                    for (Player miner : Bukkit.getServer().getOnlinePlayers()) {
                        if (miner.getLocation().getY() < 30) {
                            miners.add(miner.getUniqueId());
                        }
                    }
                    if (miners.size() == 0) {
                        p.sendMessage(Basic.translate("&cCurrently no one is under Y 30."));
                    } else {
                        Player randomMiner = Bukkit.getPlayer(miners.get(new Random().nextInt(miners.size())));
                        p.teleport(randomMiner.getLocation());
                        p.sendMessage(Basic.translate("&dYou have been teleported to &5" + randomMiner.getName() + "&d."));
                    }
                }
            }

            // RANDOM TELEPORT
            if (p.getItemInHand().getType() == Material.EMERALD) {
                if (!p.hasPermission("staff.randomteleport")) {
                    p.sendMessage(noPermission);
                } else {
                    ArrayList<UUID> players = new ArrayList<>();
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        players.add(player.getUniqueId());
                    }
                    Player randomPlayer = Bukkit.getPlayer(players.get(new Random().nextInt(players.size())));
                    p.teleport(randomPlayer.getLocation());
                    p.sendMessage(Basic.translate("&dYou have been teleported to &5" + randomPlayer.getName() + "&d."));
                }
            }

            // TOGGLE VANISH
            if (p.getItemInHand().getType() == Material.SUGAR) {
                if (ModCommand.getVanished().contains(p.getUniqueId())) {
                    for (Player server : Bukkit.getServer().getOnlinePlayers()) {
                        server.showPlayer(p);
                    }
                    ModCommand.getVanished().remove(p.getUniqueId());
                    p.sendMessage(Basic.translate("&dVanish disabled."));
                } else {
                    for (Player server : Bukkit.getServer().getOnlinePlayers()) {
                        if (!server.hasPermission("staff.command.mod")) {
                            server.hidePlayer(p);
                        }
                    }
                    ModCommand.getVanished().add(p.getUniqueId());
                    p.sendMessage(Basic.translate("&dVanish enabled."));
                }
            }
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        Entity clickede = e.getRightClicked();
        if (clickede instanceof Player) {
            Player clicked = (Player) e.getRightClicked();
            if (ModCommand.getModMode().contains(p.getUniqueId())) {
                // Inspect Inventory
                if (p.getItemInHand().getType() == Material.BOOK) {
                    if (!p.hasPermission("staff.inspect")) {
                        p.sendMessage(noPermission);
                    } else {
                        Inventory view = clicked.getInventory();
                        p.openInventory(view);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        Entity damaged = e.getEntity();
        Entity attacker = e.getDamager();

        if (damaged instanceof Player) {
            Player damagedp = (Player) e.getEntity();

            if (ModCommand.getModMode().contains(damagedp.getUniqueId())) {
                e.setCancelled(true);
            }
        }

        if (attacker instanceof Player) {
            Player attackerp = (Player) e.getDamager();

            if (ModCommand.getModMode().contains(attackerp.getUniqueId())) {
                e.setCancelled(true);
                attackerp.sendMessage(preventAction);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (ModCommand.getModMode().contains(p.getUniqueId())) {
            e.setCancelled(true);
            p.sendMessage(preventAction);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (ModCommand.getModMode().contains(p.getUniqueId())) {
            e.setCancelled(true);
            p.sendMessage(preventAction);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        if (ModCommand.getModMode().contains(p.getUniqueId())) {
            e.setCancelled(true);
            p.sendMessage(preventAction);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (ModCommand.getModMode().contains(p.getUniqueId())) {
            e.setCancelled(true);
            p.sendMessage(preventAction);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (ModCommand.getModMode().contains(p.getUniqueId())) {
            e.setCancelled(true);
            p.sendMessage(preventAction);
        }
    }

    @EventHandler
    public void onSaturate(FoodLevelChangeEvent e) {
        Entity en = e.getEntity();
        if ((en instanceof Player)) {
            Player p = (Player) en;
            if (ModCommand.getModMode().contains(p.getUniqueId())) {
                p.setFoodLevel(20);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        Entity en = e.getEntity();
        if ((en instanceof Player)) {
            Player p = (Player) en;
            if (ModCommand.getModMode().contains(p.getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        for (UUID id : ModCommand.getModMode()) {
            if (!p.hasPermission("staff.command.mod")) {
                p.hidePlayer(Bukkit.getPlayer(id));
            }
        }
    }

}
