package me.thecamzone.Commands.NovaStrike;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import me.thecamzone.NovaStrike;

public class NovaStrikeCommandCompleter implements TabCompleter {

	private NovaStrike plugin = NovaStrike.getInstance();

	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		ArrayList<String> arguments = new ArrayList<>();
		Collection<NovaStrikeCommand> subcommands = this.plugin.getNovaStrikeSubcommands().values();
		for (NovaStrikeCommand novaStrikeCommand : subcommands) {
			String permission = novaStrikeCommand.getPermission();
			if (!permission.isEmpty() && !sender.hasPermission(novaStrikeCommand.getPermission()))
				continue;
			arguments.add(novaStrikeCommand.getName());
		}
		NovaStrikeCommand subcommand = (NovaStrikeCommand) this.plugin.getNovaStrikeSubcommands().get(args[0]);
		if (args.length > 1 && (subcommand == null || !sender.hasPermission(subcommand.getPermission()))) {
			arguments.clear();
			return arguments;
		}
		if (args.length < 2) {
			return getCompletion(arguments, args, 0);
		}

		arguments.clear();
		return arguments;
	}

	private ArrayList<String> getCompletion(ArrayList<String> arguments, String[] args, int index) {
		ArrayList<String> results = new ArrayList<>();
		for (String argument : arguments) {
			if (!argument.toLowerCase().startsWith(args[index].toLowerCase()))
				continue;
			results.add(argument);
		}
		return results;
	}
}
