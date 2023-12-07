package me.thecamzone.Commands;

import me.thecamzone.Utils.StringUtil;
import me.thecamzone.Utils.jcommandbuilder.JArgument.JArg;
import me.thecamzone.Utils.jcommandbuilder.JArgument.JArgument_Int;
import me.thecamzone.Utils.jcommandbuilder.JArgument.JArgument_StringArray;
import me.thecamzone.Utils.jcommandbuilder.JCommandSender;
import me.thecamzone.Utils.jcommandbuilder.annotations.JCommand;
import me.thecamzone.Utils.jcommandbuilder.annotations.JCommandPlayerOnly;
import me.thecamzone.Utils.jcommandbuilder.annotations.JSubCommand;
import me.thecamzone.personalStash.PersonalStash;
import me.thecamzone.server_connections.ConnectionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class DeveloperCommand {

    @JCommand("Dev")
    @JSubCommand("OpenPersonalStash")
    @JCommandPlayerOnly
    public void openPersonalStash(JCommandSender sender) {
        PersonalStash.getInstance().openGui(sender.asGPlayer(), 1);
    }

    @JCommand("Dev")
    @JSubCommand("test")
    @JArg(name="name", type= JArgument_Int.class, defaultValue="2", tabProvider = "truefalse", tabProviderCase = "UPPER")
    public void test(JCommandSender sender, int name) {
        sender.sendMessage("Hi" + name);
    }

    @JCommand("Dev")
    @JSubCommand("connect")
    public void connect(JCommandSender sender) {

        ConnectionManager.connect();
    }

    @JCommand("Dev")
    @JSubCommand("messageproxy")
    @JArg(name = "message", type = JArgument_StringArray.class)
    public void novastrike(JCommandSender sender, String... args) {
        Bukkit.getConsoleSender().sendMessage(args);

        if(ConnectionManager.connection == null) {
            sender.sendMessage(ChatColor.RED + "[NovaStrike] Server is not connected to proxy.");
            return;
        }

        ConnectionManager.connection.sendMessageToProxy(StringUtil.displayArray(args));
    }

}
