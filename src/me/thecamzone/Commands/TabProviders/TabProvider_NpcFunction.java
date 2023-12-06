package me.thecamzone.Commands.TabProviders;

import npc.NpcFunctions;
import utils.jcommandbuilder.JTabProvider;

import java.util.ArrayList;
import java.util.List;

public class TabProvider_NpcFunction implements JTabProvider {

    private static final ArrayList<String> TABS = new ArrayList<>();

    @Override
    public List<String> getTabs() {
        if (TABS.isEmpty()){
            for (NpcFunctions npcFunction : NpcFunctions.values())
                TABS.add(npcFunction.name());
        }

        return TABS;
    }
}
