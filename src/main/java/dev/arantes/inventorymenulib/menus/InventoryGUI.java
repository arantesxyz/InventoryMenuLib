package dev.arantes.inventorymenulib.menus;

import dev.arantes.inventorymenulib.buttons.ClickAction;
import dev.arantes.inventorymenulib.buttons.ItemButton;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import java.util.HashMap;
import java.util.Map;

public class InventoryGUI implements InventoryHolder {
    private final Inventory inv;
    private Map<Integer, ItemButton> callbacks;

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
    }

    public void show(Player player) {
        player.openInventory(inv);
    }

    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);

        if (!callbacks.containsKey(event.getRawSlot())) {
            return;
        }

        final ClickAction action = callbacks.get(event.getRawSlot()).getAction(event.getClick());
        if (action != null) {
            action.run(event);
        }
    }
}
