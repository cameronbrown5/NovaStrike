/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package me.thecamzone.Utils.guiBuilder;


import me.thecamzone.Utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class GUICreator {

    private final String name;
    private final int size;
    private final String uniqueIdentifier;
    private final HashMap<Integer, GUIItem> items = new HashMap<>();
    private boolean persistentGUI = false;

    public GUICreator(String name, int size, String uniqueIdentifier) {
        this.name = name;
        this.size = size;
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public GUICreator(String name, int size) {
        this.name = name;
        this.size = size;
        this.uniqueIdentifier = UUID.randomUUID().toString();
    }

    public void addClickableItem(int inventorySlot, GUIItem clickableItem) {
        items.put(inventorySlot, clickableItem);
    }

    public void addClickableItem(int inventorySlot, GUIItem clickableItem, int... additionalClickSlots) {
        items.put(inventorySlot, clickableItem);

        for (int slots : additionalClickSlots){
            addClickableItem(slots, new GUIItem(new ItemStack(Material.BARRIER)) {
                @Override
                public boolean onClick(Player p, InventoryClickEvent e) {
                    return clickableItem.onClick(p, e);
                }
            });
        }

    }

    public void addNonRemovableItem(int inventorySlot, ItemStack itemStack) {
        items.put(inventorySlot, new GUIItem(itemStack) {
            @Override
            public boolean onClick(Player p, InventoryClickEvent e) {
                return true;
            }
        });
    }

    public void addRemovableItem(int inventorySlot, ItemStack itemStack) {
        items.put(inventorySlot, new GUIItem(itemStack) {
            @Override
            public boolean onClick(Player p, InventoryClickEvent e) {
                return false;
            }
        });
    }

    public GUI build() {
        GUI activeGui = GuiBuilderManager.getInstance().getFromActive(uniqueIdentifier);

        if (activeGui != null)
            return activeGui;

        Inventory inventory = Bukkit.createInventory(new GUIHolder(uniqueIdentifier, persistentGUI), size, StringUtil.formatColor(name));

        items.forEach((slot, guiItem) -> {
            if (slot > size - 1) return;
            inventory.setItem(slot, guiItem.getItem());
        });

        GUI gui = new GUI(inventory, items);
        GuiBuilderManager.getInstance().registerGUI(uniqueIdentifier, gui);
        return gui;
    }

    // warning: gui will not be removed from the tracker. persistentGUI should not be consistently created.
    public GUICreator setPersistent() {
        persistentGUI = true;
        return this;
    }
}
