package me.thecamzone.Commands.TabProviders;

import net.minecraft.world.entity.EquipmentSlot;
import utils.jcommandbuilder.JTabProvider;

import java.util.ArrayList;
import java.util.List;

public class TabProvider_EquipmentSlot implements JTabProvider {

    private static final ArrayList<String> TABS = new ArrayList<>();

    @Override
    public List<String> getTabs() {
        if (TABS.isEmpty()){
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values())
                TABS.add(equipmentSlot.name());
        }

        return TABS;
    }
}
