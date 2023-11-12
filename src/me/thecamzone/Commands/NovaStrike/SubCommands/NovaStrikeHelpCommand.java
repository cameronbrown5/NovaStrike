package me.thecamzone.Commands.NovaStrike.SubCommands;

import org.bukkit.command.CommandSender;

import me.thecamzone.Commands.NovaStrike.NovaStrikeCommand;
import me.thecamzone.Utils.Messager;

public class NovaStrikeHelpCommand extends NovaStrikeCommand {
	public NovaStrikeHelpCommand() {
		setName("help");
		setInfoMessage("Displays this list.");
		setPermission("novastrike.default");
		setArgumentLength(1);
		setUsageMessage("/ns help");
		setUniversalCommand(true);
	}

	public void execute(CommandSender sender, String[] args) {
		Messager.sendNovaStrikeHelpMessage(sender);
	}
}
