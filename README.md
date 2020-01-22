# HologramAPI

## About

This is a simple and lightweight library to help developers creating Single or Paginated GUI inventories for Spigot.

## Getting your version

You'll need maven to compile, or you can simple download a release [here](https://github.com/arantesxyz/InventoryMenuLib/releases).

```bash
mvn clean package
```

After you have the jar just put as a dependency in your project.

Maven repository coming soon.

## Usage

### Paginated Menu

The shape system:

-   # = the content
-   < = Previous page
-   > = Next page
-   x (or any other char) = the border

**The last roll should be ignored during the shape process, making the shape having a maximum of 5 rows.**

```java
// Create the menu with the name and the shape.
// {page} is replaced by the page number
PaginatedGUI gui = new PaginatedGUIBuilder(
                    "§8Menu | Página: {page}",
                    "xxxxxxxxx" +
                    "x#######x" +
                    "<#######>" +
                    "x#######x" +
                    "xxxxxxxxx"
            )

            // Define the material of the border and the name.
            // You can also define actions if you want the border to be clickable
            .setBorder(new ItemButton(Material.STAINED_GLASS_PANE, 1, "§eBorda", ""))

            // Set an item in the 4 position of the hotbar (lastrow)
            .setHotbarButton(
                    (byte) 4,
                    new ItemButton(Material.COMPASS, 1, "§cFechar")
                            .addAction(ClickType.LEFT, (InventoryClickEvent e) -> e.getWhoClicked().closeInventory())
            )

            // Set the item for the next page button.
            .setNextPageItem(Material.ARROW, 1, "§6Próxima página")

            // Set the item for the previous page buttom.
            .setPreviousPageItem(Material.ARROW, 1, "§6Página anterior")

            // Set the content
            .setContent(
                    new ItemButton(Material.DIAMOND, 1, "Item"),
                    new ItemButton(Material.DIAMOND, 1, "Item"),
                    new ItemButton(Material.DIAMOND, 1, "Item"),
                    new ItemButton(Material.DIAMOND, 1, "Item")
            )

            // Build and return the PaginatedGUI
            .build();

            // Show the first page of the wiki for a Player.
            gui.show(player);
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

Soon
