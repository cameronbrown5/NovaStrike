package me.thecamzone.Events;

import me.thecamzone.Utils.guiBuilder.GuiBuilderManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickHandler extends NovaListener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        GuiBuilderManager.getInstance().manageInventoryClick(e);
    }

}
