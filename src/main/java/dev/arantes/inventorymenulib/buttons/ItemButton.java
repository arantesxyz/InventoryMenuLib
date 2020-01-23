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
