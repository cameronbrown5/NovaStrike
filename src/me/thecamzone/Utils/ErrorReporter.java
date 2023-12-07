package me.thecamzone.Utils;

import me.thecamzone.gamePlayer.GPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class ErrorReporter {

    public static void reportToConsole(String... errorDescription) {
        reportToConsole(false, false, errorDescription);
    }

    public static void reportToConsole(boolean addStackTrace, boolean includeFullError, String... errorDescription) {
        ConsoleCommandSender sender = Bukkit.getServer().getConsoleSender();
        sender.sendMessage("---------- [NovaStrike Error] ----------");
        sender.sendMessage(errorDescription);
        if (addStackTrace) {
            for (String s : StringUtil.getCallTree(includeFullError)) {
                sender.sendMessage(s);
            }
        }
        sender.sendMessage("--------------------------------------");
    }

    public static void reportToPlayer(GPlayer gPlayer, String... errorDescription) {
        reportToPlayer(gPlayer, false, false, errorDescription);
    }

    public static void reportToPlayer(GPlayer gPlayer, boolean addStackTrace, boolean includeFullError, String... errorDescription) {
        gPlayer.sendMessage("---------- [NovaStrike Error] ----------");
        gPlayer.sendMessage(errorDescription);
        if (addStackTrace) {
            for (String s : StringUtil.getCallTree(includeFullError)) {
                gPlayer.sendMessage(s);
            }
        }
        gPlayer.sendMessage("------------------------------");
    }
}
