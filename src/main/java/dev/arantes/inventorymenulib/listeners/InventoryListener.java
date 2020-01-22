package dev.arantes.inventorymenulib.listeners;

import dev.arantes.inventorymenulib.menus.InventoryGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryListener implements Listener {

    public InventoryListener(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getCurrentItem() == null) return;

        if (event.getClickedInventory().getHolder() != null &&
                event.getClickedInventory().getHolder() instanceof InventoryGUI) {
            final InventoryGUI inventoryGUI = (InventoryGUI) event.getClickedInventory().getHolder();
            inventoryGUI.onClick(event);
        }
    }
}
