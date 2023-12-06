package me.thecamzone.Utils.jcommandbuilder;

import me.thecamzone.NovaStrike;
import me.thecamzone.Utils.CaseInsensitiveString;
import me.thecamzone.Utils.ErrorReporter;
import me.thecamzone.Utils.jcommandbuilder.JArgument.JArgument;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class JCommandManager implements TabCompleter, CommandExecutor {

    private static JCommandManager jCommandRegistry;
    private final JCommandAnnotationProcessor jCommandAnnotationProcessor = new JCommandAnnotationProcessor(this);
    private final HashMap<CaseInsensitiveString, JCmd> commands = new HashMap<>();
    private final HashMap<CaseInsensitiveString, ArrayList<String>> commandsHelp = new HashMap<>();
    private final HashMap<CaseInsensitiveString, ArrayList<String>> coreCommandTabs = new HashMap<>();
    private final HashMap<String, JTabProvider> tabProviders = new HashMap<>();

    public static JCommandManager getInstance() {
        if (jCommandRegistry == null) {
            jCommandRegistry = new JCommandManager();
        }
        return jCommandRegistry;
    }

    public void registerAnnotationCommand(Object commandObject) {
        jCommandAnnotationProcessor.registerCommand(commandObject);
    }

    protected void registerCommand(CaseInsensitiveString coreCommandName, CaseInsensitiveString commandID, JCmd jCmd) {
        commands.put(commandID, jCmd);
        updateCommandsHelp(jCmd);
        updateCoreCommandTabs(jCmd);
        registerCoreCommandWithBukkit(coreCommandName);
    }

    public void registerTabProvider(String tabProviderID, JTabProvider tabProvider) {
        if (tabProvider == null) return;
        tabProviders.put(tabProviderID, tabProvider);
    }

    public boolean hasTabProvider(String tabProviderID) {
        return tabProviders.containsKey(tabProviderID);
    }

    public JTabProvider getTabProvider(String tabProviderID) {
        return tabProviders.get(tabProviderID);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String coreCommandName, String[] args) {
        CaseInsensitiveString commandID = new CaseInsensitiveString(coreCommandName);
        CaseInsensitiveString coreCommandNameInsensitive = new CaseInsensitiveString(coreCommandName);

        JCommandSender jCommandSender = null;

        if (commandSender instanceof Player playerSender)
            jCommandSender = new JCommandSender(null, NovaStrike.getInstance().getgPlayerManager().getGPlayer(playerSender));

        if (commandSender instanceof ConsoleCommandSender consoleCommandSender)
            jCommandSender = new JCommandSender(consoleCommandSender, null);

        if (jCommandSender == null) {
            ErrorReporter.reportToConsole("Unable to get command sender for command: " + coreCommandName);
            return true;
        }

        boolean hasCoreCommand = commands.containsKey(new CaseInsensitiveString(coreCommandName + "-*"));

        if (hasCoreCommand)
            commandID.add("-*");
        else {

            if (args.length > 0)
                commandID.add("-" + args[0]);

            if (!commands.containsKey(commandID))
                if (commandsHelp.containsKey(new CaseInsensitiveString(coreCommandName)))
                    sendCommandHelp(jCommandSender, coreCommandNameInsensitive);
        }

        JCmd jCmd = commands.get(commandID);

        if (jCmd != null)
            jCmd.callCommand(jCommandSender, args);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String commandName, String[] args) {
        ArrayList<String> correctTabs = new ArrayList<>();
        String currentTyped = args[args.length - 1].toUpperCase();

        JCmd corejCmd = commands.get(new CaseInsensitiveString(command.getName() + "-*"));
        JCmd subJCmd = commands.get(new CaseInsensitiveString(command.getName() + "-" + args[0]));

        if (corejCmd == null && args.length == 1 && coreCommandTabs.containsKey(new CaseInsensitiveString(command.getName()))) {
            for (String subCommandTabs : coreCommandTabs.get(new CaseInsensitiveString(command.getName())))
                if (subCommandTabs.toUpperCase().startsWith(currentTyped))
                    correctTabs.add(subCommandTabs);

        }else if (subJCmd != null && args.length >= 2)
            processArgumentTabs(subJCmd.getJArgument(args.length - 2), correctTabs, currentTyped);
         else if (corejCmd != null)
            processArgumentTabs(corejCmd.getJArgument(args.length - 1), correctTabs, currentTyped);


        return correctTabs;
    }

    private void processArgumentTabs(JArgument<?> jArgument, List<String> correctTabs, String currentTyped) {
        if (jArgument == null)
            return;

        JTabProviderCase jTabProviderCase = jArgument.getTabProviderCase();

        for (String s : jArgument.getTabProvider().getTabs())
            if (s.toUpperCase().startsWith(currentTyped)) {
                switch (jTabProviderCase) {
                    case LOWER -> s = s.toLowerCase();
                    case UPPER -> s = s.toUpperCase();
                }
                correctTabs.add(s);
            }

        if (correctTabs.isEmpty())
            correctTabs.add(jArgument.getNameWithBrackets());

    }

    public void registerCoreCommandWithBukkit(CaseInsensitiveString coreCommand) {
        try {
            final Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            final PluginCommand pluginCommand = constructor.newInstance(coreCommand.getValue(), NovaStrike.getInstance());
            pluginCommand.setUsage("-");
            pluginCommand.setAliases(Collections.singletonList("AL2"));

            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);

            ((CommandMap) bukkitCommandMap.get(Bukkit.getServer())).register("", pluginCommand);
            //Has to be set after the command is registered.
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    public void updateCommandsHelp(JCmd jCmd) {
        ArrayList<String> currentHelp = new ArrayList<>();
        CaseInsensitiveString coreCommandName = jCmd.getCoreCommandName();

        if (commandsHelp.containsKey(coreCommandName)) {
            currentHelp = commandsHelp.get(coreCommandName);
        } else {
            currentHelp.add("&7----- ^m/" + coreCommandName + " Commands &7-----");
        }

        currentHelp.add(jCmd.getUsageString());
        commandsHelp.put(coreCommandName, currentHelp);

    }

    public void updateCoreCommandTabs(JCmd jCmd) {
        ArrayList<String> currentTabs = new ArrayList<>();
        CaseInsensitiveString coreCommandName = jCmd.getCoreCommandName();


        if (coreCommandTabs.containsKey(coreCommandName))
            currentTabs = coreCommandTabs.get(coreCommandName);

        if (jCmd.getSubCommandName().isEqual("*")) return;

        currentTabs.add(jCmd.getSubCommandName().getValue());
        coreCommandTabs.put(coreCommandName, currentTabs);

    }

    public void sendCommandHelp(JCommandSender jCommandSender, CaseInsensitiveString coreCommandName) {
        commandsHelp.get(coreCommandName).forEach(jCommandSender::sendMessage);
    }

}
