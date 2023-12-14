/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package me.thecamzone.Utils.guiBuilder;


import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.TexturedInventoryWrapper;
import me.thecamzone.gamePlayer.GPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUICreator {

    private final String name;
    private final int size;
    private final String uniqueIdentifier;
    private final HashMap<Integer, GUIItem> guiItems = new HashMap<>();
    private final HashMap<Integer, GUIItem> inventoryItems = new HashMap<>();
    private boolean persistentGUI = false;
    private FontImageWrapper[] textures;
    private int titleOffset = 16;
    private int textureOffset = -16;

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

    public void addClickableGuiItem(int guiSlot, GUIItem clickableItem) {
        guiItems.put(guiSlot, clickableItem);
    }

    public void addClickableGuiItem(int guiSlot, GUIItem clickableItem, int... additionalClickSlots) {
        guiItems.put(guiSlot, clickableItem);

        for (int slots : additionalClickSlots){
            addClickableGuiItem(slots, new GUIItem(new ItemStack(Material.AIR)) {
                @Override
                public boolean onClick(GPlayer gPlayer , InventoryClickEvent e) {
                    return clickableItem.onClick(gPlayer, e);
                }
            });
        }

    }

    /**
     * Warning: Will replace any item in the slot.
     */
    public void addClickableInventoryItem(int inventorySlot, GUIItem clickableItem) {
        inventoryItems.put(inventorySlot, clickableItem);
    }

    /**
     * Warning: Will replace any item in the slot.
     */
    public void addClickableInventoryItem(int inventorySlot, GUIItem clickableItem, int... additionalClickSlots) {
        inventoryItems.put(inventorySlot, clickableItem);

        for (int slots : additionalClickSlots){
            addClickableGuiItem(slots, new GUIItem(new ItemStack(Material.AIR)) {
                @Override
                public boolean onClick(GPlayer gPlayer, InventoryClickEvent e) {
                    return clickableItem.onClick(gPlayer, e);
                }
            });
        }

    }

    public void addNonRemovableGuiItem(int guiSlot, ItemStack itemStack) {
        guiItems.put(guiSlot, new GUIItem(itemStack) {
            @Override
            public boolean onClick(GPlayer gPlayer, InventoryClickEvent e) {
                return true;
            }
        });
    }

    public void addRemovableGuiItem(int guiSlot, ItemStack itemStack) {
        guiItems.put(guiSlot, new GUIItem(itemStack) {
            @Override
            public boolean onClick(GPlayer gPlayer, InventoryClickEvent e) {
                return false;
            }
        });
    }

    /**
     * Warning: Will replace any item in the slot.
     */
    public void addNonRemovableInventoryItem(int inventorySlot, ItemStack itemStack) {
        inventoryItems.put(inventorySlot, new GUIItem(itemStack) {
            @Override
            public boolean onClick(GPlayer gPlayer, InventoryClickEvent e) {
                return true;
            }
        });
    }

    /**
     * Warning: Will replace any item in the slot.
     */
    public void addRemovableInventoryItem(int inventorySlot, ItemStack itemStack) {
        inventoryItems.put(inventorySlot, new GUIItem(itemStack) {
            @Override
            public boolean onClick(GPlayer gPlayer, InventoryClickEvent e) {
                return false;
            }
        });
    }

    public void setTextures(FontImageWrapper... images) {
        if(images == null) {
            Bukkit.getConsoleSender().sendMessage("Null");
        }

        this.textures = images;
    }

    public void setTextureOffset(int offset) {
        textureOffset = offset;
    }

    public void setTitleOffset(int offset) {
        titleOffset = offset;
    }

    public GUI<?> build() {
        GUI<?> activeGui = GuiBuilderManager.getInstance().getFromActive(uniqueIdentifier);

        if (activeGui != null)
            return activeGui;

        Inventory inventory = Bukkit.createInventory(new GUIHolder(uniqueIdentifier, persistentGUI), size, name);

        TexturedInventoryWrapper inventoryWrapper = null;
        if(textures != null) {
            inventoryWrapper = new TexturedInventoryWrapper(
                new GUIHolder(uniqueIdentifier, persistentGUI),
                size,
                name,
                titleOffset,
                textureOffset,
                textures
            );

            inventory = inventoryWrapper.getInternal();
        }

        for (Map.Entry<Integer, GUIItem> entry : guiItems.entrySet()) {
            Integer slot = entry.getKey();
            GUIItem guiItem = entry.getValue();
            if (slot > size - 1) continue;
            inventory.setItem(slot, guiItem.getItem());
        }

        if(textures == null) {
            GUI<Inventory> gui = new GUI<Inventory>(inventory, guiItems, inventoryItems);
            GuiBuilderManager.getInstance().registerGUI(uniqueIdentifier, gui);
            return gui;
        } else {
            GUI<TexturedInventoryWrapper> gui = new GUI<TexturedInventoryWrapper>(inventoryWrapper, guiItems, inventoryItems);
            GuiBuilderManager.getInstance().registerGUI(uniqueIdentifier, gui);
            return gui;
        }
    }

    // warning: gui will not be removed from the tracker. persistentGUI should not be consistently created.
    public GUICreator setPersistent() {
        persistentGUI = true;
        return this;
    }
}
