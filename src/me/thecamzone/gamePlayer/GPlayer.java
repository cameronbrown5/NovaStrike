package me.thecamzone.gamePlayer;

import me.thecamzone.Utils.EquipmentType;
import me.thecamzone.Utils.ItemAdderUtil;
import me.thecamzone.Utils.task.TaskRepeat;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GPlayer extends PlayerWrapper {

    private final static int DATA_SAVE_INTERVAL = 900;
    private final GPlayerDataController dataController = new GPlayerDataController(this);
    private int nextDataSave = 0;
    private TaskRepeat gPlayerTickTask;

    private final HashMap<Integer, String> loadoutPrimaryWeapon = new HashMap<>();
    private final HashMap<Integer, String> loadoutSecondaryWeapon = new HashMap<>();
    private final HashMap<Integer, String> loadoutGrenade = new HashMap<>();

    private final HashMap<EquipmentType, HashSet<String>> equipment = new HashMap<>();


    {
        dataController.load();
        startGPlayerTick();

        for (int i = 0; i <  10; i++) {
            grantEquipment(i + "", EquipmentType.PRIMARY);
        }


    }

    public void setPrimaryWeapon(String weaponName){

    }

    public void grantEquipment(String equipmentName, EquipmentType equipmentType){
      HashSet<String> set = equipment.getOrDefault(equipmentType, new HashSet<>());

      set.add(equipmentName);
      equipment.put(equipmentType, set);
    }

    public void giveLoadoutItems(int loadoutNumber){
  //      getInventory().setItem(0, getCustomItemAdderItem(loadoutPrimaryWeapon.get(loadoutNumber)));
     //   getInventory().setItem(1, getCustomItemAdderItem(loadoutSecondaryWeapon.get(loadoutNumber)));
     //   getInventory().setItem(2, getCustomItemAdderItem(loadoutGrenade.get(loadoutNumber)));
    }

    public void giveSpawnItems(){

    }

    public ArrayList<ItemStack> getAllEquipmentItems(EquipmentType equipmentType){
        ArrayList<ItemStack> items = new ArrayList<>();
        equipment.getOrDefault(equipmentType, new HashSet<>()).forEach(itemName -> items.add(ItemAdderUtil.fromItemID(itemName)));
        return items;
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
