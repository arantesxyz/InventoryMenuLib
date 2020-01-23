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
        if (!(event.getWhoClicked() instanceof Player) || event.getCurrentItem() == null) {
            return;
        }

        if (event.getInventory().getHolder() != null &&
                event.getInventory().getHolder() instanceof InventoryGUI) {
            event.setCancelled(true);

            final InventoryGUI inventoryGUI = (InventoryGUI) event.getInventory().getHolder();
            inventoryGUI.onClick(event);
        }
    }
}
