package me.thecamzone.Commands.Party;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import me.thecamzone.NovaStrike;

public class PartyCommandCompleter implements TabCompleter {

	private NovaStrike plugin = NovaStrike.getInstance();

	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		ArrayList<String> arguments = new ArrayList<>();
		Collection<PartyCommand> subcommands = this.plugin.getPartySubcommands().values();
		for (PartyCommand partyCommand : subcommands) {
			String permission = partyCommand.getPermission();
			if (!permission.isEmpty() && !sender.hasPermission(partyCommand.getPermission()))
				continue;
			arguments.add(partyCommand.getName());
		}
		PartyCommand subcommand = (PartyCommand) this.plugin.getPartySubcommands().get(args[0]);
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
