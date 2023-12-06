package me.thecamzone.Commands;

import me.thecamzone.Utils.jcommandbuilder.JCommandManager;
import me.thecamzone.Utils.jcommandbuilder.StaticJTabProvider;

public class CommandRegister {

    {
        registerTabProviders();
        registerCommands();
    }

    private void registerTabProviders() {
        JCommandManager.getInstance().registerTabProvider("test", new Developer());
        JCommandManager.getInstance().registerTabProvider("truefalse", new StaticJTabProvider("true", "false"));
    }

    private void registerCommands() {
        JCommandManager.getInstance().registerAnnotationCommand(new Developer());
    }


}
