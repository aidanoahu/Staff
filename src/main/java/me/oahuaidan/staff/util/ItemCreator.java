package me.oahuaidan.staff.util;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemCreator {

    @Getter private Material material;
    @Getter private int data;
    @Getter private String name = null;
    @Getter private String[] lore = null;
    @Getter private boolean hasData = false;
    @Getter private int amount = 1;
    @Getter private Map<Enchantment, Integer> enchantments = Maps.newHashMap();

    public ItemCreator(Material m) {
        this.material = m;
    }

    public ItemCreator setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemCreator setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemCreator setData(int data) {
        this.data = data;
        this.hasData = true;
        return this;
    }

    public ItemCreator setName(String name) {
        this.name = name;
        return this;
    }

    public ItemCreator setLore(String... lore) {
        this.lore = lore;
        return this;
    }

    public ItemCreator addEnchantment(Enchantment enchantment, int level) {
        getEnchantments().put(enchantment, level);
        return this;
    }

    public ItemCreator addEnchantment(Enchantment enchantment) {
        getEnchantments().put(enchantment, 1);
        return this;
    }

    public ItemCreator removeEnchantment(Enchantment enchantment) {
        getEnchantments().remove(enchantment);
        return this;
    }

    public boolean hasEnchantment(Enchantment enchantment) {
        return getEnchantments().containsKey(enchantment);
    }

    public ItemStack build() {

        Validate.isTrue(getMaterial() != null && Integer.valueOf(getAmount()) != null, "Invalid data for ItemCreator");

        ItemStack stack;
        if (hasData) {
            stack = new ItemStack(getMaterial(), getAmount(), (short)getData());
        } else stack = new ItemStack(getMaterial(), getAmount());

        if (getName() != null) {
            Basic.setName(stack, Basic.translate(getName()));
        }

        if (getLore() != null) {
            List<String> trans = new ArrayList<>();
            for (String l : getLore()) {
                trans.add(Basic.translate(l));
            }

            ItemMeta meta = stack.getItemMeta();
            meta.setLore(trans);
            stack.setItemMeta(meta);
        }

        stack.addEnchantments(getEnchantments());

        return stack;
    }

    //UHC
    public static ItemStack getGoldenHead() {
        return new ItemCreator(Material.GOLDEN_APPLE).setName("&6&lGolden Head").build();
    }

    public static ItemStack fromMaterial(Material material) {
        return new ItemCreator(material).build();
    }
}
