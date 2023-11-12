package me.thecamzone.Commands.Party;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.thecamzone.NovaStrike;
import me.thecamzone.Utils.Messager;

public class PartyCommandRunner implements CommandExecutor {
	private NovaStrike plugin = NovaStrike.getInstance();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!cmd.getName().equalsIgnoreCase("party"))
			return false;
		if (args.length < 1) {
			Messager.sendPartyHelpMessage(sender);
			return true;
		}
		String inputCommand = args[0].toLowerCase();
		if (!this.plugin.getPartySubcommands().containsKey(inputCommand)) {
			Messager.sendErrorMessage(sender,
					"&cUnknown command. Type &l/party help &cto see the full command list.");
			return true;
		}
		PartyCommand subcommand = (PartyCommand) this.plugin.getPartySubcommands().get(inputCommand);
		if (subcommand.isPlayerCommand() && !(sender instanceof org.bukkit.entity.Player)) {
			Messager.sendErrorMessage(sender, "&cNot available for consoles.");
			return true;
		}
		if (subcommand.isConsoleCommand() && sender instanceof org.bukkit.entity.Player) {
			Messager.sendErrorMessage(sender, "&cNot available for players.");
			return true;
		}
		if (args.length < subcommand.getArgumentLength()) {
			Messager.sendErrorMessage(sender, "&cUsage: &l" + subcommand.getUsageMessage());
			return true;
		}
		String permission = subcommand.getPermission();
		if (!permission.isEmpty() && !sender.hasPermission(subcommand.getPermission())) {
			Messager.sendNoPermissionMessage(sender);
			return true;
		}
		subcommand.execute(sender, args);
		return true;
	}
}
