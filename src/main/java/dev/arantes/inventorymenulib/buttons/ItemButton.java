/*
 * This file is part of InventoryMenuLib, licensed under the MIT License
 *
 * Copyright (c) Gustavo Arantes (me@arantes.dev)
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.arantes.inventorymenulib.buttons;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.*;

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

    public ItemButton(Material material, String name, String... lore) {
        this(material, 1, name, lore);
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
        final ItemStack item = new ItemStack(material, amount);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);

        this.item = item;
        return this;
    }

    public ItemButton setDamage(short damage) {
        item.setDurability(damage);
        return this;
    }

    public ItemButton setData(MaterialData data) {
        item.setData(data);
        return this;
    }

    public ItemButton setName(String name) {
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public ItemButton setLore(String... lines) {
        final ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(lines));
        item.setItemMeta(meta);
        return this;
    }

    public ItemButton setLore(List<String> lines) {
        final ItemMeta meta = item.getItemMeta();
        meta.setLore(lines);
        item.setItemMeta(meta);
        return this;
    }

    public ItemButton setLore(int pos, String line) {
        final ItemMeta meta = item.getItemMeta();
        List<String> lores = new ArrayList<>();

        if (meta.hasLore()) {
            lores = meta.getLore();
        }

        if (lores.get(pos) != null) {
            lores.set(pos, line);

            meta.setLore(lores);
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemButton addLore(String... lines) {
        final ItemMeta meta = item.getItemMeta();
        final List<String> lore = meta.getLore();

        lore.addAll(Arrays.asList(lines));

        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemButton glow(boolean v) {
        final ItemMeta meta = item.getItemMeta();

        if (v) {
            item.addUnsafeEnchantment(
                    item.getType()!= Material.BOW ? Enchantment.ARROW_INFINITE : Enchantment.LUCK,
                    10
            );
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }else {
            item.removeEnchantment(item.getType()!= Material.BOW ? Enchantment.ARROW_INFINITE : Enchantment.LUCK);
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(meta);
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
