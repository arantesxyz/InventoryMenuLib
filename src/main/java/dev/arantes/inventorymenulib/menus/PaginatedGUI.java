package dev.arantes.inventorymenulib.menus;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PaginatedGUI {
    private List<InventoryGUI> pages;
    private int currentPage;

    public PaginatedGUI() {
        this.pages = new ArrayList<>();
        this.currentPage = 0;
    }

    public PaginatedGUI(List<InventoryGUI> pages) {
        this.pages = pages;
        this.currentPage = 0;
    }

    public void addPage(InventoryGUI page) {
        pages.add(page);
    }

    public void setPage(int pos, InventoryGUI page) {
        pages.set(pos, page);
    }

    public void removePage(int pos){
        pages.remove(pos);
    }

    public void show(Player player) {
        if (pages.size() == 0) {
            return;
        }

        pages.get(0).show(player);
        currentPage = 0;
    }

    public void show(Player player, int page) {
        if (page >= pages.size()) {
            return;
        }

        pages.get(page).show(player);
        currentPage = page;
    }

    public void showPrevious(Player player) {
        final int page = currentPage - 1;
        if (page < 0) {
            return;
        }

        pages.get(page).show(player);
        currentPage = page;
    }

    public void showNext(Player player) {
        final int page = currentPage + 1;
        if (page >= pages.size()) {
            return;
        }

        pages.get(page).show(player);
        currentPage = page;
    }
}
