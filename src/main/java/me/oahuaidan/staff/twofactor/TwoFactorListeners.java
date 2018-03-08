package me.oahuaidan.staff.twofactor;

import me.oahuaidan.staff.Staff;
import me.oahuaidan.staff.util.Basic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class TwoFactorListeners implements Listener {

    private Staff pl = Staff.getInstance();
    private String COMPLETE_REQUEST = Basic.translate("&cYou need to complete a Two Factor verification to do this. Type \"/2fa <code>\" to complete the request.");

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (!TwoFactorAPI.getMustAuth(p.getUniqueId())) return;

        String msg = e.getMessage().toLowerCase();

        if (msg.toLowerCase().startsWith("/2fa") || msg.toLowerCase().startsWith("/2fasetup")) return;

        e.setCancelled(true);
        p.sendMessage(COMPLETE_REQUEST);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (!TwoFactorAPI.getMustAuth(p.getUniqueId())) return;

        e.setCancelled(true);
        p.sendMessage(COMPLETE_REQUEST);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!TwoFactorAPI.getMustAuth(p.getUniqueId())) return;

        e.setCancelled(true);
        p.sendMessage(COMPLETE_REQUEST);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityInteract(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if (!TwoFactorAPI.getMustAuth(p.getUniqueId())) return;

        e.setCancelled(true);
        p.sendMessage(COMPLETE_REQUEST);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;

        Player p = (Player) e.getWhoClicked();
        if (!TwoFactorAPI.getMustAuth(p.getUniqueId())) return;

        e.setCancelled(true);
        p.sendMessage(COMPLETE_REQUEST);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlacement(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (!TwoFactorAPI.getMustAuth(p.getUniqueId())) return;

        e.setCancelled(true);
        p.sendMessage(COMPLETE_REQUEST);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!TwoFactorAPI.getMustAuth(p.getUniqueId())) return;

        e.setCancelled(true);
        p.sendMessage(COMPLETE_REQUEST);
    }
}
