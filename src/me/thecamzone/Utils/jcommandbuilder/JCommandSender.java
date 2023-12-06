package me.thecamzone.Utils.jcommandbuilder;

import jline.internal.Nullable;
import me.thecamzone.Utils.StringUtil;
import me.thecamzone.gamePlayer.GPlayer;
import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

public class JCommandSender {

    private final ConsoleCommandSender consoleCommandSender;
    private final GPlayer gPlayer;

    public JCommandSender(ConsoleCommandSender consoleCommandSender, GPlayer gPlayer) {
        this.consoleCommandSender = consoleCommandSender;
        this.gPlayer = gPlayer;
    }

    public boolean isGPlayer() {
        return gPlayer != null;
    }

    public boolean isConsole() {
        return consoleCommandSender != null;
    }

    @Nullable
    public GPlayer asGPlayer() {
        return gPlayer;
    }

    @Nullable
    public ConsoleCommandSender asConsole() {
        return consoleCommandSender;
    }

    public void sendMessage(String message) {
        if (isGPlayer()) asGPlayer().sendMessage(message);
        if (isConsole()) asConsole().sendMessage(StringUtil.formatColor(message));
    }

    public void sendMessage(List<String> messages) {
        for (String s : messages) sendMessage(s);
    }

    public void sendMessage(String... messages) {
        for (String s : messages) sendMessage(s);
    }

}
