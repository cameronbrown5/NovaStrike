package me.thecamzone.Commands;

import me.thecamzone.Utils.jcommandbuilder.JCommandManager;

public class CommandRegister {

    {
        registerTabProviders();
        registerCommands();
    }

    private void registerTabProviders() {

    }

    private void registerCommands() {
        JCommandManager.getInstance().registerAnnotationCommand(new DeveloperCommand());
        JCommandManager.getInstance().registerAnnotationCommand(new NovaStrikeCommand());
    }


}
