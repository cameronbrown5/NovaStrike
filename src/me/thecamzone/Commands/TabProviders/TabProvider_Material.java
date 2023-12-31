package me.thecamzone.Commands.TabProviders;

import me.thecamzone.Utils.jcommandbuilder.JTabProvider;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class TabProvider_Material implements JTabProvider {

    private static final ArrayList<String> TABS = new ArrayList<>();

    @Override
    public List<String> getTabs() {
        if (TABS.isEmpty()){
            for (Material material : Material.values())
                TABS.add(material.name());
        }

        return TABS;
    }
}
