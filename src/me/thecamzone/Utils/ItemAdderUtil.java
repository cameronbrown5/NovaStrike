package me.thecamzone.Utils;

import dev.lone.itemsadder.api.CustomStack;
import me.thecamzone.NovaStrike;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemAdderUtil {

    public static String getItemID(ItemStack itemStack){
        return CustomStack.byItemStack(itemStack).getId();
    }

    public static ItemStack fromItemID(String itemID){
        if (!NovaStrike.IS_ITEM_ADDER_LOADED){
            return new ItemBuilder(Material.POISONOUS_POTATO).setDisplayName(itemID).build();
        }

        CustomStack customItem = CustomStack.getInstance(itemID);

        if (customItem == null) return new ItemStack(Material.POISONOUS_POTATO);

        return customItem.getItemStack();
    }

}
