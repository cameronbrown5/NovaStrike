package me.thecamzone.Commands.TabProviders;

import me.thecamzone.Utils.jcommandbuilder.JTabProvider;
import org.bukkit.entity.EntityType;

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
