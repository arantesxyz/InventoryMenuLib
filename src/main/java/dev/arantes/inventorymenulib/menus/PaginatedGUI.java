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
