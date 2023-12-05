package me.thecamzone.Events;

import me.thecamzone.Utils.guiBuilder.GuiBuilderManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseHandler extends NovaListener {

    @EventHandler
    public void onInventoryClick(InventoryCloseEvent e){
        GuiBuilderManager.getInstance().manageInventoryClose(e);

    }

}
