package me.thecamzone.server_connections;

import me.thecamzone.Utils.StringUtil;
import org.bukkit.Bukkit;

public class ConnectionCommand {

    public static void handleNetworkCommand(String command, String[] args) {
        if(command.equalsIgnoreCase("runcommand")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), StringUtil.formatArgs(args).replace("/", ""));
        }
    }



}
