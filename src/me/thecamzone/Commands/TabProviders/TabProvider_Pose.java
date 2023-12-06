package me.thecamzone.Commands.TabProviders;

import me.thecamzone.Utils.jcommandbuilder.JTabProvider;
import org.bukkit.entity.Pose;

import java.util.ArrayList;
import java.util.List;

public class TabProvider_Pose implements JTabProvider {

    private static final ArrayList<String> TABS = new ArrayList<>();

    @Override
    public List<String> getTabs() {
        if (TABS.isEmpty()){
            for (Pose pose : Pose.values())
                TABS.add(pose.name());
        }

        return TABS;
    }
}
