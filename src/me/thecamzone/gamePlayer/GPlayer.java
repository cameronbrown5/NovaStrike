package me.thecamzone.gamePlayer;

import dev.lone.itemsadder.api.CustomStack;
import me.thecamzone.Utils.task.TaskRepeat;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class GPlayer extends PlayerWrapper {

    private final static int DATA_SAVE_INTERVAL = 900;
    private final GPlayerDataController dataController = new GPlayerDataController(this);
    private int nextDataSave = 0;
    private TaskRepeat gPlayerTickTask;

    private final HashMap<Integer, String> loadoutPrimaryWeapon = new HashMap<>();
    private final HashMap<Integer, String> loadoutSecondaryWeapon = new HashMap<>();
    private final HashMap<Integer, String> loadoutGrenade = new HashMap<>();

    private final Set<String> primaryWeapons = new HashSet<>();
    private final Set<String> secondaryWeapons = new HashSet<>();
    private final Set<String> grenades = new HashSet<>();

    {
        dataController.load();
        startGPlayerTick();
    }

    public void setPrimaryWeapon(String weaponName){

    }

    public void giveLoadoutItems(int loadoutNumber){
        getInventory().setItem(0, getCustomItem(loadoutPrimaryWeapon.get(loadoutNumber)));
        getInventory().setItem(1, getCustomItem(loadoutSecondaryWeapon.get(loadoutNumber)));
        getInventory().setItem(2, getCustomItem(loadoutGrenade.get(loadoutNumber)));
    }

    public void giveSpawnItems(){

    }

    public ItemStack getCustomItem(String itemName){
        CustomStack customItem = CustomStack.getInstance(itemName);

        if (customItem == null) return new ItemStack(Material.PAPER);

        return customItem.getItemStack();
    }

    
    public GPlayer(Player p) {
        super(p);
    }

    private void startGPlayerTick() {
        gPlayerTickTask = new TaskRepeat(20, () -> {
            if (!isOnline()) gPlayerTickTask.stop();

            if (nextDataSave-- < DATA_SAVE_INTERVAL) {
                nextDataSave = DATA_SAVE_INTERVAL;
                dataController.save();
            }

        });
    }

    protected GPlayerDataController getDataController() {
        return dataController;
    }
}
