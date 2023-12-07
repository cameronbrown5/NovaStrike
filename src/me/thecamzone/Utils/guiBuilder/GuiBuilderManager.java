/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package me.thecamzone.Utils.guiBuilder;

import me.thecamzone.NovaStrike;
import me.thecamzone.gamePlayer.GPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nullable;
import java.util.HashMap;

public class GuiBuilderManager {
    private static GuiBuilderManager guiBuilderManagerInstance;

    private final HashMap<String, GUI> activeGuis = new HashMap<>();

    private GuiBuilderManager() {}

    public static GuiBuilderManager getInstance() {
        if (guiBuilderManagerInstance == null) guiBuilderManagerInstance = new GuiBuilderManager();
        return guiBuilderManagerInstance;
    }

    void registerGUI(String uniqueIdentifier, GUI gui) {
        activeGuis.put(uniqueIdentifier, gui);
    }

    public void manageInventoryClose(InventoryCloseEvent e) {
        Inventory inventory = e.getInventory();
        if (inventory.getHolder() instanceof GUIHolder gui_holder) {
            GUI gui = activeGuis.get(gui_holder.uniqueIdentifier());
            gui.removeInventoryItemsOnClose((Player) e.getPlayer());

            if (gui_holder.persistentGUI()) return;

            activeGuis.remove(gui_holder.uniqueIdentifier());
        }
    }

    public void manageInventoryDrag(InventoryDragEvent e) {
        Inventory inventory = e.getInventory();

        if (inventory.getHolder() instanceof GUIHolder gui_holder) {
            GUI gui = activeGuis.get(gui_holder.uniqueIdentifier());

            if (gui != null)
                e.setCancelled(true);
        }
    }

    public void manageInventoryClick(InventoryClickEvent e) {
        Inventory inventory = e.getClickedInventory();
        GPlayer gPlayer = NovaStrike.getInstance().getgPlayerManager().getGPlayer((Player) e.getWhoClicked());
        boolean clickingInGui = true;

        if (gPlayer == null){
            e.setCancelled(true);
            return;
        }

        if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)
            e.setCancelled(true);

        if (inventory == null) return;

        if(inventory.getHolder() instanceof Player) {
            clickingInGui = false;
            inventory = gPlayer.getOpenInventory().getTopInventory();
        }

        if (inventory.getHolder() instanceof GUIHolder gui_holder) {
            GUI gui = activeGuis.get(gui_holder.uniqueIdentifier());

            if (gui != null)
                if (gui.processClick( gPlayer,  e, clickingInGui))
                    e.setCancelled(true);
        }
    }

    @Nullable
    public GUI getFromActive(String uniqueIdentifier) {
        return activeGuis.get(uniqueIdentifier);
    }

}
