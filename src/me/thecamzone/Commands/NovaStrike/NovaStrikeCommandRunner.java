package me.thecamzone.Commands.NovaStrike;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.thecamzone.NovaStrike;
import me.thecamzone.Utils.Messager;

public class NovaStrikeCommandRunner implements CommandExecutor {
	private NovaStrike plugin = NovaStrike.getInstance();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!cmd.getName().equalsIgnoreCase("novastrike"))
			return false;
		if (args.length < 1) {
			Messager.sendNovaStrikeHelpMessage(sender);
			return true;
		}
		String inputCommand = args[0].toLowerCase();
		if (!this.plugin.getNovaStrikeSubcommands().containsKey(inputCommand)) {
			Messager.sendErrorMessage(sender,
					"&cUnknown command. Type &l/ns help &cto see the full command list.");
			return true;
		}
		NovaStrikeCommand subcommand = (NovaStrikeCommand) this.plugin.getNovaStrikeSubcommands().get(inputCommand);
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
