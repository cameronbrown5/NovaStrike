/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package me.thecamzone.Utils.guiBuilder;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class GUI {

    private final Inventory guiInventory;
    private final HashMap<Integer, GUIItem> clickableItemsBySlot;

    public GUI(Inventory guiInventory, HashMap<Integer, GUIItem> clickableItemsBySlot) {
        this.guiInventory = guiInventory;
        this.clickableItemsBySlot = clickableItemsBySlot;
    }

    /**
     * @return If the event should be canceled.
     */
    boolean processClick(Player p, InventoryClickEvent e) {
        GUIItem clickedItem = clickableItemsBySlot.get(e.getSlot());

        if (clickedItem == null)
            return true;

        return clickedItem.onClick(p, e);
    }

    public void open(Player p) {
        p.openInventory(guiInventory);
    }

    //------------------------------------------------------------------------------------------------------------------------------
    // ##############################################################################################################################
    // Default - Getters and Setters
    //##############################################################################################################################
    //------------------------------------------------------------------------------------------------------------------------------

    public Inventory getGuiInventory() {
        return guiInventory;
    }

}
