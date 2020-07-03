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

package dev.arantes.inventorymenulib;

import com.google.common.collect.Maps;
import dev.arantes.inventorymenulib.buttons.ClickAction;
import dev.arantes.inventorymenulib.buttons.ItemButton;
import dev.arantes.inventorymenulib.menus.InventoryGUI;
import dev.arantes.inventorymenulib.menus.PaginatedGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class PaginatedGUIBuilder {
    private String name;
    private final char[] shape;
    private List<ItemButton> content;
    private ClickAction contentDefaultAction;
    private ItemButton[] hotbar = new ItemButton[9];
    private ItemButton borderButton;
    private ItemStack nextPageItem;
    private ItemStack previousPageItem;
    private Map<Integer, ItemButton> buttons = Maps.newHashMap();
    private boolean defaultCancell = false;
    private boolean defaultAllCancell = false;

    public PaginatedGUIBuilder(String name, String shape) {
        this.name = name;
        this.shape = shape.toCharArray();

        if (this.shape.length > 45 || (this.shape.length % 9) != 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    public PaginatedGUIBuilder setBorder(Material material, int amount, String name, String... lore) {
        return setBorder(new ItemButton(material, amount, name, lore));
    }

    public PaginatedGUIBuilder setBorder(ItemButton button) {
        this.borderButton = button;
        return this;
    }


    public PaginatedGUIBuilder setNextPageItem(Material material, int amount, String name, String... lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);

        return setNextPageItem(item);
    }

    public PaginatedGUIBuilder setNextPageItem(ItemStack button) {
        this.nextPageItem = button;
        return this;
    }

    public PaginatedGUIBuilder setPreviousPageItem(Material material, int amount, String name, String... lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);

        return setPreviousPageItem(item);
    }

    public PaginatedGUIBuilder setPreviousPageItem(ItemStack button) {
        this.previousPageItem = button;
        return this;
    }

    public PaginatedGUIBuilder setHotbarButton(byte pos, ItemButton button) {
        if (pos > 8) {
            throw new IndexOutOfBoundsException();
        }

        hotbar[pos] = button;
        return this;
    }

    public PaginatedGUIBuilder setContent(List<ItemButton> content) {
        this.content = content;
        return this;
    }

    public PaginatedGUIBuilder setContent(ItemButton... content) {
        this.content = Arrays.asList(content);
        return this;
    }

    public PaginatedGUIBuilder setContentDefaultAction(ClickAction action) {
        this.contentDefaultAction = action;
        return this;
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

    public PaginatedGUIBuilder setButton(int slot, ItemButton button) {
        buttons.put(slot, button);
        return this;
    }

    public PaginatedGUI build() {
        int contentSize = 0;
        for (char c : shape) {
            if (c == '#') {
                contentSize++;
            }
        }

        final int amountOfPages = (int) ((content.size() / (float) contentSize) + 0.99);

        PaginatedGUI paginatedGUI = new PaginatedGUI();

        int currentItem = 0;
        for (int pageI = 0; pageI < amountOfPages; pageI++) {
            InventoryGUI page = new InventoryGUI(
                    name.replace("{page}", pageI + ""),
                    (shape.length + 9)
            );

            buttons.forEach(page::setButton);

            page.setDefaultAllCancell(defaultAllCancell);
            page.setDefaultCancell(defaultCancell);

            for (int i = 0; i < shape.length; i++) {
                final char current = shape[i];

                if (current == '>' && pageI < (amountOfPages-1)) {
                    final ItemButton btn = new ItemButton(nextPageItem);
                    btn.addAction(ClickType.RIGHT, (InventoryClickEvent e) ->
                            paginatedGUI.showNext((Player) e.getWhoClicked()));

                    btn.addAction(ClickType.LEFT, (InventoryClickEvent e) ->
                            paginatedGUI.showNext((Player) e.getWhoClicked()));

                    page.setButton(i, btn);
                    continue;
                }

                if (current == '<' && pageI != 0) {
                    final ItemButton btn = new ItemButton(previousPageItem);
                    btn.addAction(ClickType.RIGHT, (InventoryClickEvent e) ->
                            paginatedGUI.showPrevious((Player) e.getWhoClicked()));

                    btn.addAction(ClickType.LEFT, (InventoryClickEvent e) ->
                            paginatedGUI.showPrevious((Player) e.getWhoClicked()));

                    page.setButton(i, btn);
                    continue;
                }

                if (current == '#') {
                    if (currentItem < content.size()) {
                        ItemButton cItem = content.get(currentItem++);
                        if (cItem.getDefaultAction() == null && contentDefaultAction != null) {
                            cItem.setDefaultAction(contentDefaultAction);
                        }

                        page.setButton(i, cItem);
                    }
                    continue;
                }
                if (borderButton != null) {
                    page.setButton(i, borderButton);
                }
            }

            for (int hotbarI = 0; hotbarI < hotbar.length; hotbarI++) {
                final ItemButton item = hotbar[hotbarI];
                if (item != null) {
                    page.setButton(shape.length + hotbarI, item);
                }
            }

            paginatedGUI.addPage(page);
        }

        return paginatedGUI;
    }
}
