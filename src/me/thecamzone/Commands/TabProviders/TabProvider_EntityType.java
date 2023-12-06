package me.thecamzone.Commands.TabProviders;

import org.bukkit.entity.EntityType;
import utils.jcommandbuilder.JTabProvider;

import java.util.ArrayList;
import java.util.List;

public class TabProvider_EntityType implements JTabProvider {

    private static final ArrayList<String> TABS = new ArrayList<>();
    @Override
    public List<String> getTabs() {
        if (TABS.isEmpty()){
            for (EntityType entityType : EntityType.values())
                TABS.add(entityType.name());
        }

        return TABS;
    }
}
