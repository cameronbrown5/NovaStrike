package me.thecamzone.Commands.Party;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import me.thecamzone.Parties.Party;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.thecamzone.NovaStrike;
import me.thecamzone.Parties.PartyInvite;
import me.thecamzone.Parties.PartyManager;

public class PartyCommandCompleter implements TabCompleter {

	private NovaStrike plugin = NovaStrike.getInstance();
	private PartyManager partyManager = plugin.getPartyManager();
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return new ArrayList<String>();
		}

		Player player = (Player) sender;
		
		ArrayList<String> arguments = new ArrayList<>();
		Collection<PartyCommand> subcommands = this.plugin.getPartySubcommands().values();
		for (PartyCommand partyCommand : subcommands) {
			String permission = partyCommand.getPermission();
			if (!permission.isEmpty() && !sender.hasPermission(partyCommand.getPermission()))
				continue;
			arguments.add(partyCommand.getName());
		}
		PartyCommand subcommand = this.plugin.getPartySubcommands().get(args[0]);
		List<String> onlinePlayerNames = new ArrayList<>();

		for(Player p : Bukkit.getOnlinePlayers()) {
			onlinePlayerNames.add(p.getName());
		}
		
		if (args.length > 1 && (subcommand == null || !sender.hasPermission(subcommand.getPermission()))) {
			arguments.clear();
			return arguments;
		}

		if (args.length < 2) {
			return getCompletion(arguments, args, 0);
		}

		if (args.length == 2) {
			if(args[0].equalsIgnoreCase("invite")) {
				List<String> onlinePlayers = onlinePlayerNames;
				onlinePlayers.remove(sender.getName());
				
				return onlinePlayers;
			}
			
			if(args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("promote")) {
				if(!partyManager.getPlayerParty(player.getUniqueId()).getLeader().equals(player.getUniqueId())) {
					return new ArrayList<>();
				}
				
				List<String> partyPlayers = new ArrayList<>();


				for(UUID p : partyManager.getPlayerParty(player.getUniqueId()).getPlayers()) {
					partyPlayers.add(Bukkit.getOfflinePlayer(p).getName());
				}
				
				partyPlayers.remove(player.getName());
				return partyPlayers;
			}
			
			if(args[0].equalsIgnoreCase("accept")) {
				List<String> partyInvites = new ArrayList<>();
				
				for(PartyInvite partyInvite : partyManager.getPlayerInvites(player.getUniqueId())) {
					partyInvites.add(partyInvite.getInviterName());
				}
				
				return partyInvites;
			}
			
			return new ArrayList<>();
		}
		
//		if (args.length > 2) {
//			return new ArrayList<String>();
//		}

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
