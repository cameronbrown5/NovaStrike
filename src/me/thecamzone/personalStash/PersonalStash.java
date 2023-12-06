package me.thecamzone.personalStash;

import me.thecamzone.Utils.guiBuilder.GUICreator;
import me.thecamzone.Utils.guiBuilder.GUIItem;
import me.thecamzone.gamePlayer.GPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

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

    public void openGui(GPlayer gPlayer){
        GUICreator guiCreator = new GUICreator("Personal Stash", 54, UUID.randomUUID().toString()).setPersistent();

        guiCreator.addClickableItem(10, new GUIItem(new ItemStack(Material.PAPER)) {
            @Override
            public boolean onClick(Player p, InventoryClickEvent e) {
                Bukkit.broadcastMessage("Set primary weapon.");
                return true;
            }
        }, 11);


        guiCreator.addClickableItem(13, new GUIItem(new ItemStack(Material.PAPER)) {
            @Override
            public boolean onClick(Player p, InventoryClickEvent e) {
                Bukkit.broadcastMessage("Set secondary weapon.");
                return true;
            }
        }, 14);


        guiCreator.addClickableItem(15, new GUIItem(new ItemStack(Material.PAPER)) {
            @Override
            public boolean onClick(Player p, InventoryClickEvent e) {
                Bukkit.broadcastMessage("Set grenade.");
                return true;
            }
        });

        guiCreator.build().open(gPlayer);
    }

    public void openPrimaryWeaponSelectGui(GPlayer gPlayer, int page){
        GUICreator guiCreator = new GUICreator("Personal Stash", 54, UUID.randomUUID().toString()).setPersistent();

        guiCreator.addClickableItem(10, new GUIItem(new ItemStack(Material.PAPER)) {
            @Override
            public boolean onClick(Player p, InventoryClickEvent e) {
                Bukkit.broadcastMessage("Set primary weapon.");
                return true;
            }
        }, 11);


        guiCreator.addClickableItem(13, new GUIItem(new ItemStack(Material.PAPER)) {
            @Override
            public boolean onClick(Player p, InventoryClickEvent e) {
                Bukkit.broadcastMessage("Set secondary weapon.");
                return true;
            }
        }, 14);


        guiCreator.addClickableItem(15, new GUIItem(new ItemStack(Material.PAPER)) {
            @Override
            public boolean onClick(Player p, InventoryClickEvent e) {
                Bukkit.broadcastMessage("Set grenade.");
                return true;
            }
        });

        guiCreator.build().open(gPlayer);
    }




}
