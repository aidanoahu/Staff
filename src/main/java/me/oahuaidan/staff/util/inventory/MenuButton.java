package me.oahuaidan.staff.util.inventory;

import lombok.Getter;
import me.oahuaidan.staff.util.ItemCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MenuButton {

    @Getter private ItemStack button;
    @Getter private MenuAction[] actions;
    @Getter private InventoryMenu redirect;
    @Getter private MenuEvent event = new MenuEvent();
    @Getter private int slot;

    public MenuButton(ItemStack button) {
        this.button = button;
        this.actions = new MenuAction[] {MenuAction.NONE};
    }

    public MenuButton(Material material) {
        this.button = new ItemCreator(material).build();
        this.actions = new MenuAction[] {MenuAction.NONE};
    }

    public MenuButton(ItemStack button, int slot) {
        this.button = button;
        this.actions = new MenuAction[] {MenuAction.NONE};
        this.slot = slot;
    }

    public MenuButton(Material material, int slot) {
        this.button = new ItemCreator(material).build();
        this.actions = new MenuAction[] {MenuAction.NONE};
        this.slot = slot;
    }

    public MenuButton setButton(ItemStack button) {
        this.button = button;
        return this;
    }

    public MenuButton setAction(MenuAction... action) {
        this.actions = action;
        return this;
    }

    public MenuButton setRedirect(InventoryMenu redirect) {
        this.redirect = redirect;
        return this;
    }

    public MenuButton setEvent(MenuEvent event) {
        this.event = event;
        return this;
    }

}
