package me.thecamzone.Commands.TabProviders;

import org.bukkit.entity.Display;
import utils.jcommandbuilder.JTabProvider;

import java.util.ArrayList;
import java.util.List;

public class TabProvider_Billboard implements JTabProvider {

    private static final ArrayList<String> TABS = new ArrayList<>();
    @Override
    public List<String> getTabs() {
        if (TABS.isEmpty()){
            for (Display.Billboard billboardType : Display.Billboard.values())
                TABS.add(billboardType.name());
        }

        return TABS;
    }
}
