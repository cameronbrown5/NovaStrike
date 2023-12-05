/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package me.thecamzone.Utils.guiBuilder;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class GUIItem {
    private final ItemStack item;

    public GUIItem(ItemStack item) {
        this.item = item;
    }

    public abstract boolean onClick(Player p, InventoryClickEvent e);

    ItemStack getItem() {
        return item;
    }

    //------------------------------------------------------------------------------------------------------------------------------
    // ##############################################################################################################################
    // Default - Getters and Setters
    //##############################################################################################################################
    //------------------------------------------------------------------------------------------------------------------------------



}
