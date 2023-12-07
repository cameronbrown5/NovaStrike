package me.thecamzone.personalStash;

import me.thecamzone.Utils.EquipmentType;
import me.thecamzone.Utils.ItemBuilder;
import me.thecamzone.Utils.guiBuilder.GUICreator;
import me.thecamzone.Utils.guiBuilder.GUIItem;
import me.thecamzone.gamePlayer.GPlayer;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class PersonalStash {


    private static PersonalStash personalStashInstance;

    private PersonalStash() {}

    public static PersonalStash getInstance() {
        if (personalStashInstance == null) {
            personalStashInstance = new PersonalStash();
        }
        return personalStashInstance;
    }

    public void openGui(GPlayer gPlayer, int loadoutNumber){
        GUICreator guiCreator = new GUICreator("Personal Stash", 54, UUID.randomUUID().toString());
        addBaseItemsToGui(guiCreator, loadoutNumber);
        guiCreator.build().open(gPlayer);
    }

    public void openGuiWithEquipmentSelect(GPlayer gPlayer, int page, int loadoutNumber, EquipmentType equipmentType){
        GUICreator guiCreator = new GUICreator("Personal Stash", 54, UUID.randomUUID().toString());
        addBaseItemsToGui(guiCreator, loadoutNumber);
        addEquipmentSelectItemsToGui(gPlayer, guiCreator, page, equipmentType, loadoutNumber);
        guiCreator.build().open(gPlayer);
    }

    public void addBaseItemsToGui(GUICreator guiCreator, int loadoutNumber){

        int loadOutSlotTracker = 0;
        for (int i = 1; i <= 5 ; i++) {
            if (i == loadoutNumber){
                guiCreator.addNonRemovableGuiItem(loadOutSlotTracker++, new ItemBuilder(Material.LIME_DYE).setDisplayName("Current Loadout: " + i).build());
            }else {
                int finalI = i;
                guiCreator.addClickableGuiItem(loadOutSlotTracker++, new GUIItem(new ItemBuilder(Material.GRAY_DYE).setDisplayName("Select Loadout " + finalI).build()) {
                    @Override
                    public boolean onClick(GPlayer gPlayer, InventoryClickEvent e) {
                        openGui(gPlayer, finalI);
                        return true;
                    }
                });
            }
        }

        guiCreator.addClickableGuiItem(10, new GUIItem(new ItemStack(Material.PAPER)) {
            @Override
            public boolean onClick(GPlayer gPlayer, InventoryClickEvent e) {
                openGuiWithEquipmentSelect(gPlayer, 1, loadoutNumber, EquipmentType.PRIMARY);
                return true;
            }
        }, 11);


        guiCreator.addClickableGuiItem(13, new GUIItem(new ItemStack(Material.PAPER)) {
            @Override
            public boolean onClick(GPlayer gPlayer, InventoryClickEvent e) {
                openGuiWithEquipmentSelect(gPlayer, 1, loadoutNumber, EquipmentType.SECONDARY);
                return true;
            }
        }, 14);


        guiCreator.addClickableGuiItem(15, new GUIItem(new ItemStack(Material.PAPER)) {
            @Override
            public boolean onClick(GPlayer gPlayer, InventoryClickEvent e) {
                openGuiWithEquipmentSelect(gPlayer, 1, loadoutNumber, EquipmentType.GRENADE);
                return true;
            }
        });
    }

    public void addEquipmentSelectItemsToGui(GPlayer gPlayer, GUICreator guiCreator, int page, EquipmentType equipmentType, int loadoutNumber){
        ArrayList<ItemStack> equipment = gPlayer.getAllEquipmentItems(equipmentType);

        if (equipment.size() > 27 * page){
            guiCreator.addClickableInventoryItem(35, new GUIItem(new ItemStack(Material.PAPER)) {
                @Override
                public boolean onClick(GPlayer gPlayer, InventoryClickEvent e) {
                    openGuiWithEquipmentSelect(gPlayer, page + 1, loadoutNumber, equipmentType);
                    return true;
                }
            });
        }else {
            guiCreator.addNonRemovableInventoryItem(35, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }

        if (page > 1){
            guiCreator.addClickableInventoryItem(34, new GUIItem(new ItemStack(Material.PAPER)) {
                @Override
                public boolean onClick(GPlayer gPlayer, InventoryClickEvent e) {
                    openGuiWithEquipmentSelect(gPlayer, page - 1, loadoutNumber, equipmentType);
                    return true;
                }
            });
        }else {
            guiCreator.addNonRemovableInventoryItem(34, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }

        guiCreator.addClickableInventoryItem(27, new GUIItem(
                new ItemBuilder(Material.TRIPWIRE_HOOK)
                        .setDisplayName("Back")
                        .build()) {
            @Override
            public boolean onClick(GPlayer gPlayer, InventoryClickEvent e) {
                openGui(gPlayer, loadoutNumber);
                return true;
            }
        });

        int slotTracker = 8;
        for (int i = (18 * (page - 1)); i <= Math.min(equipment.size() - 1, 18 * page) ; i++) {
            guiCreator.addClickableInventoryItem(++slotTracker, new GUIItem(
                    new ItemBuilder(equipment.get(i))
                            .setDisplayName("Select as " + equipmentType.getFriendlyName())
                            .build()) {
                @Override
                public boolean onClick(GPlayer gPlayer, InventoryClickEvent e) {

                    return true;
                }
            });
        }

    }



}
