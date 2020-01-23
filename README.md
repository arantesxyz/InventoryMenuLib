# InvetoryMenuLib

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

First, you have to register the listener like so:

```java
public void onEnable() {
        // ...

        new InventoryListener(this);

        // ...
}
```

### Paginated Menu

The shape system:

-   '#' = Content
-   '<' = Previous page
-   '>' = Next page
-   'x' (or any other char) = Border

**The last row should be ignored during the shape process, making the shape having a maximum of 5 rows.**

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
                        .addAction(ClickType.LEFT, (InventoryClickEvent e) ->
                                e.getWhoClicked().closeInventory())
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

### Single menu

```java
// First we created the inventory passing the Title and the size (3 * 9 -> 3 row inventory).
final InventoryGUI gui = new InventoryGUI("Inventory Title", 3 * 9);

// Create a button
final ItemButton btn = new ItemButton(previousPageItem);
// Add action for an specific Click Type
btn.addAction(ClickType.RIGHT, (InventoryClickEvent e) -> {
        e.getWhoClicked().sendMessage("You RIGHT clicked the item!");
});

// Set the default action
// The default action will be triggered if you don't have a click type registered
// for the user action.
// In this example, we registered the RIGHT CLICK above, so if the user
// use any click other than the RIGHT one the default action will be triggered.
btn.setDefaultAction((InventoryClickEvent e) -> {
        e.getWhoClicked().sendMessage("You CLICKED the item.");
});

// Set the button in the inventory slot 5.
gui.setButton(5, btn);

// Show the inventory to the player
gui.show(player);
```

## TODO

-   Allow user to use it's own inventory while the CustomInventory is open.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

See [LICENSE](https://github.com/arantesxyz/InventoryMenuLib/blob/master/LICENSE) page for more info.
