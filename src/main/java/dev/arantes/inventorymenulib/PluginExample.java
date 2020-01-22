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
