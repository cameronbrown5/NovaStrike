/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package me.thecamzone.Utils.guiBuilder;

import dev.lone.itemsadder.api.FontImages.TexturedInventoryWrapper;
import me.thecamzone.gamePlayer.GPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class GUI<T> {

    private final T guiInventory;
    private final HashMap<Integer, GUIItem> guiClickableItemsBySlot;
    private final HashMap<Integer, GUIItem> inventoryClickableItemsBySlot;

    public GUI(T guiInventory, HashMap<Integer, GUIItem> guiClickableItemsBySlot, HashMap<Integer, GUIItem> inventoryClickableItemsBySlot) {
        this.guiInventory = guiInventory;
        this.guiClickableItemsBySlot = guiClickableItemsBySlot;
        this.inventoryClickableItemsBySlot = inventoryClickableItemsBySlot;
    }

    /**
     * @return If the event should be canceled.
     */
    boolean processClick(GPlayer gPlayer, InventoryClickEvent e, boolean clickingInGui) {
        GUIItem clickedItem = clickingInGui ? guiClickableItemsBySlot.get(e.getSlot()) : inventoryClickableItemsBySlot.get(e.getSlot());

        if (clickedItem == null)
            return true;

        return clickedItem.onClick(gPlayer, e);
    }

    public void removeInventoryItemsOnClose(Player p){
        inventoryClickableItemsBySlot.forEach((slot, item) -> p.getInventory().setItem(slot, new ItemStack(Material.AIR)));

    }

    public void open(Player p) {
        if(guiInventory instanceof Inventory i)
            p.openInventory(i);

        if(guiInventory instanceof TexturedInventoryWrapper i)
            i.showInventory(p);

        inventoryClickableItemsBySlot.forEach((slot, item) -> p.getInventory().setItem(slot, item.getItem()));
    }

    //------------------------------------------------------------------------------------------------------------------------------
    // ##############################################################################################################################
    // Default - Getters and Setters
    //##############################################################################################################################
    //------------------------------------------------------------------------------------------------------------------------------

}
