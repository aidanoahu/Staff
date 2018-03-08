package me.oahuaidan.staff.listener;

import me.oahuaidan.staff.util.Basic;
import me.oahuaidan.staff.command.FreezeCommand;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FreezeListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (FreezeCommand.getFrozen().contains(p.getUniqueId())) {
            Location from = e.getFrom();
            p.teleport(from);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (FreezeCommand.getFrozen().contains(p.getUniqueId())) {
            e.setCancelled(true);
            p.sendMessage(Basic.translate("&cYou may not break blocks while frozen!"));
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = (Player) e.getPlayer();
        if (FreezeCommand.getFrozen().contains(p.getUniqueId())) {
            e.setCancelled(true);
            p.sendMessage(Basic.translate("&cYou may not place blocks while frozen!"));
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Entity attacker = e.getDamager();
        Entity damaged = e.getEntity();

        if (attacker instanceof Player) {
            Player attackerp = (Player) e.getDamager();

            if (FreezeCommand.getFrozen().contains(attackerp.getUniqueId())) {
                attackerp.sendMessage(Basic.translate("&cYou may not damage entities while frozen!"));
                e.setCancelled(true);
            }
        }

        if ((damaged instanceof Player) && (attacker instanceof Player)) {
            Player damagedp = (Player) e.getEntity();
            Player attackerp = (Player) e.getDamager();

            if (FreezeCommand.getFrozen().contains(damagedp.getUniqueId())) {
                attackerp.sendMessage(Basic.translate("&cPlayer &f" + damagedp.getName() + " &cis currently frozen and cannot be damaged."));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (FreezeCommand.getFrozen().contains(p.getUniqueId())) {
            e.setCancelled(true);
            p.sendMessage(Basic.translate("&cYou may not drop items while frozen."));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (FreezeCommand.getFrozen().contains(p.getUniqueId())) {
            Basic.broadcast(Basic.translate("&7[&5STAFF&7] &d&l" + p.getName() + " &7has disconnected while frozen!"), "staff.notifs");
        }
    }

}
