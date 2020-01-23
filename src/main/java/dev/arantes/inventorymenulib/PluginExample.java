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

import dev.arantes.inventorymenulib.buttons.ItemButton;
import dev.arantes.inventorymenulib.listeners.InventoryListener;
import dev.arantes.inventorymenulib.menus.PaginatedGUI;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginExample extends JavaPlugin implements CommandExecutor {

    public void onEnable() {
        new InventoryListener(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        if (command.getName().equalsIgnoreCase("menu")) {
            Player player = (Player) sender;


            PaginatedGUI gui = new PaginatedGUIBuilder(
                    "§8Menu | Página: {page}",
                    "xxxxxxxxx" +
                            "x#######x" +
                            "<#######>" +
                            "x#######x" +
                            "xxxxxxxxx"
            )
                    .setBorder(new ItemButton(Material.STAINED_GLASS_PANE, 1, "§eBorda", ""))
                    .setHotbarButton(
                            (byte) 4,
                            new ItemButton(Material.COMPASS, 1, "§cFechar")
                                    .addAction(ClickType.LEFT, (InventoryClickEvent e) -> e.getWhoClicked().closeInventory())
                    )
                    .setNextPageItem(Material.ARROW, 1, "§6Próxima página")
                    .setPreviousPageItem(Material.ARROW, 1, "§6Página anterior")
                    .setContent(
                            new ItemButton(Material.DIAMOND, 1, "Item"),
                            new ItemButton(Material.DIAMOND, 1, "Item"),
                            new ItemButton(Material.DIAMOND, 1, "Item"),
                            new ItemButton(Material.DIAMOND, 1, "Item")
                    )
                    .build();

            gui.show(player);

        }
        return true;
    }
}
