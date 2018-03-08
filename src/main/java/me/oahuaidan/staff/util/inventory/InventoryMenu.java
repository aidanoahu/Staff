package me.oahuaidan.staff.util.inventory;

import lombok.Getter;
import me.oahuaidan.staff.util.Basic;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryMenu {

    @Getter private Inventory inventory;
    @Getter private List<MenuButton> buttons = new ArrayList<>();

    public InventoryMenu(int size, String title) {
        this.inventory = Bukkit.createInventory(null, size, Basic.translate(title));
    }

    public InventoryMenu addButton(MenuButton button) {
        this.buttons.add(button);
        return this;
    }

    public InventoryMenu sort() {
        for (MenuButton b : getButtons()) {
            getInventory().setItem(b.getSlot(), b.getButton());
        }
        return this;
    }

    /**
     * Handles the menu in an event.
     * @param event InventoryClickEvent
     */
    public void handle(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player)) return;
        if (!event.getInventory().getTitle().equals(getInventory().getTitle())) return;

        Player player = (Player)event.getWhoClicked();
        ItemStack stack = event.getCurrentItem();

        if (stack == null) return;
        if (!stack.hasItemMeta()) return;

        for (MenuButton button : getButtons()) {

            if (button.getButton().isSimilar(stack)) {
                button.getEvent().run(this, button, event);
                button.getEvent().run(this, button, event, (Player)event.getWhoClicked());
                for (MenuAction action : button.getActions()) {
                    switch (action) {
                        case CLOSE:
                            player.closeInventory();
                            break;
                        case CANCEL:
                            event.setCancelled(true);
                            break;
                        case REDIRECT:
                            player.closeInventory();
                            player.openInventory(button.getRedirect().getInventory());
                            break;
                    }
                }
            }

        }

    }
}
