package me.thecamzone.Commands.TabProviders;

import mines.MineCompositionSpreadType;
import utils.jcommandbuilder.JTabProvider;

import java.util.ArrayList;
import java.util.List;

public class TabProvider_MineCompositionSpreadType implements JTabProvider {

    private static final ArrayList<String> TABS = new ArrayList<>();

    @Override
    public List<String> getTabs() {
        if (TABS.isEmpty()){
            for (MineCompositionSpreadType spreadType : MineCompositionSpreadType.values())
                TABS.add(spreadType.name().toUpperCase());
        }

        return TABS;
    }
}
