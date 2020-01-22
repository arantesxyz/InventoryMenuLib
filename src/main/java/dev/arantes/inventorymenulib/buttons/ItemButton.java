package dev.arantes.inventorymenulib.buttons;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ItemButton {
    private ItemStack item;
    private Map<ClickType, ClickAction> actions;
    private ClickAction defaultAction;

    public ItemButton() {
        this.actions = new HashMap<>();
    }

    public ItemButton(ItemStack item) {
        this.item = item;
        this.actions = new HashMap<>();
    }

    public ItemButton(Material material, int amount, String name, String... lore) {
        this.actions = new HashMap<>();
        setItem(material, amount, name, lore);
    }

    public ItemButton setItem(Material material, String name, String... lore) {
        setItem(material, 1, name, lore);
        return this;
    }

    public ItemButton setItem(Material material, int amount, String name, String... lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);

        this.item = item;
        return this;
    }

    public ItemButton addAction(ClickType type, ClickAction action) {
        actions.put(type, action);
        return this;
    }

    public ClickAction getDefaultAction() {
        return defaultAction;
    }

    public ItemButton setDefaultAction(ClickAction action) {
        this.defaultAction = action;
        return this;
    }

    public ItemStack getItem() {
        return item;
    }

    public ClickAction getAction(ClickType type) {
        if (!actions.containsKey(type)) {
            if (defaultAction != null) {
                return defaultAction;
            }
            return null;
        }

        return actions.get(type);
    }
}
