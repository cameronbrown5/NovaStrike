/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package me.thecamzone.Utils.guiBuilder;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public record GUIHolder(String uniqueIdentifier, boolean persistentGUI) implements InventoryHolder {
    @Override
    public Inventory getInventory() {
        return Bukkit.createInventory(this, 9);
    }
}
