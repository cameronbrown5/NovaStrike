package me.thecamzone.Events;

import me.thecamzone.NovaStrike;
import me.thecamzone.Utils.guiBuilder.GuiBuilderManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryDragHandler extends NovaListener {

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e){
        GuiBuilderManager.getInstance().manageInventoryDrag(e);
    }

}
