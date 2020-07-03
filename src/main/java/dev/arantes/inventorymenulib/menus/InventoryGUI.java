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

package dev.arantes.inventorymenulib.menus;

import dev.arantes.inventorymenulib.buttons.ClickAction;
import dev.arantes.inventorymenulib.buttons.ItemButton;
import dev.arantes.inventorymenulib.utils.InventorySize;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.Map;

public class InventoryGUI implements InventoryHolder {
    private final Inventory inv;
    private Map<Integer, ItemButton> callbacks;
    private boolean defaultCancell = false;
    private boolean defaultAllCancell = false;

    public InventoryGUI(final String title, final InventorySize size) {
        this(title, size.getSlotsAmount());
    }

    public InventoryGUI(final String title, final int size) {
        inv = Bukkit.createInventory(this, size, title);
        callbacks = new HashMap<>();
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public void setButton(int pos, ItemButton button) {
        inv.setItem(pos, button.getItem());
        callbacks.put(pos, button);
    }

    public void removeButton(int slot) {
        inv.clear(slot);
        callbacks.remove(slot);
    }

    public void show(Player player) {
        player.openInventory(inv);
    }

    public boolean isDefaultCancell() {
        return defaultCancell;
    }

    public void setDefaultCancell(boolean defaultCancell) {
        this.defaultCancell = defaultCancell;
    }

    public boolean isDefaultAllCancell() {
        return defaultAllCancell;
    }

    public void setDefaultAllCancell(boolean defaultAllCancell) {
        this.defaultAllCancell = defaultAllCancell;
    }

    public void onClick(InventoryClickEvent event) {
        if (!callbacks.containsKey(event.getRawSlot())) {
            return;
        }
        if (defaultCancell || defaultAllCancell)
            event.setCancelled(true);

        final ClickAction action = callbacks.get(event.getRawSlot()).getAction(event.getClick());
        if (action != null) {
            action.run(event);
        }
    }
}
