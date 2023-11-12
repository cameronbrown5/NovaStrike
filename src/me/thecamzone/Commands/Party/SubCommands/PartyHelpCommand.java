package me.thecamzone.Commands.Party.SubCommands;

import org.bukkit.command.CommandSender;

import me.thecamzone.Commands.Party.PartyCommand;
import me.thecamzone.Utils.Messager;

public class PartyHelpCommand extends PartyCommand {
	public PartyHelpCommand() {
		setName("help");
		setInfoMessage("Displays this list.");
		setPermission("novastrike.party");
		setArgumentLength(1);
		setUsageMessage("/party help");
		setUniversalCommand(true);
	}

	public void execute(CommandSender sender, String[] args) {
		Messager.sendPartyHelpMessage(sender);
	}
}
