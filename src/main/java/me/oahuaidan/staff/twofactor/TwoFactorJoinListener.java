package me.oahuaidan.staff.twofactor;

import com.google.common.collect.Lists;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.Getter;
import me.oahuaidan.staff.util.Basic;
import me.oahuaidan.staff.util.ItemCreator;
import me.oahuaidan.staff.util.inventory.InventoryMenu;
import me.oahuaidan.staff.util.inventory.MenuAction;
import me.oahuaidan.staff.util.inventory.MenuButton;
import me.oahuaidan.staff.util.inventory.MenuEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TwoFactorJoinListener implements Listener {

    @Getter private static Map<UUID, List<Integer>> codes = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        TwoFactorAPI.createTableIfNotExists();

        if (!p.hasPermission("staff.command.mod")) return;

        if (!TwoFactorAPI.hasEntry(p.getUniqueId())) {
            TwoFactorAPI.setSecret(p.getUniqueId(), null, p.getAddress().getAddress().toString());
            p.sendMessage(Basic.translate("&cTwo Factor Authentication is needed on your account. Type /2fasetup to start."));
            TwoFactorAPI.setMustAuth(p.getUniqueId(), true);
        } else {
            String lastIP = TwoFactorAPI.getLastIP(p.getUniqueId());
            if (lastIP != null) {
                if (!p.getAddress().getAddress().toString().equals(lastIP)) {
                    p.sendMessage(Basic.translate("&cPlease provide your two factor code to continue. Type \"/2fa <code>\" to authenticate."));
                    TwoFactorAPI.setMustAuth(p.getUniqueId(), true);
                }
            }
        }
    }

    public static InventoryMenu twoFactorMenu = new InventoryMenu(18, "&6Enter 2fa code");

    static {

        for (int i = 0; i < 9; i++) {

            final int num = 1; // Used for run() void, as the int must be final

            twoFactorMenu.addButton(new MenuButton(new ItemCreator(Material.WOOL).setData(14).setAmount(i).build(), i).setAction(MenuAction.CANCEL).setEvent(new MenuEvent() {
                @Override
                public void run(InventoryMenu menu, MenuButton clicked, InventoryClickEvent event, Player player) {

                    codes.putIfAbsent(player.getUniqueId(), Lists.newArrayList());
                    List<Integer> numbers = codes.get(player.getUniqueId());

                    numbers.add(num);

                    if (numbers.size() >= 9) {
                        StringBuilder builder = new StringBuilder();

                        for (int number : numbers) {
                            builder.append(String.valueOf(number));
                        }

                        int code = Integer.valueOf(builder.toString());

                        GoogleAuthenticator g = new GoogleAuthenticator();

                        if (g.authorize(TwoFactorAPI.getSecret(player.getUniqueId()), code)) {
                            // completed 2fa
                        } else {
                            // failed 2fa
                        }
                    }
                }
            }));
        }
    }

}
