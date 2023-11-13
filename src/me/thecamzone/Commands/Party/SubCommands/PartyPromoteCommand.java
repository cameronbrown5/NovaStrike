package me.thecamzone.Commands.Party.SubCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thecamzone.NovaStrike;
import me.thecamzone.Commands.Party.PartyCommand;
import me.thecamzone.Parties.Party;
import me.thecamzone.Parties.PartyManager;
import me.thecamzone.Utils.Messager;
import net.md_5.bungee.api.ChatColor;

public class PartyPromoteCommand extends PartyCommand {
	public PartyPromoteCommand() {
		setName("promote");
		setInfoMessage("Promotes a player in the party to leader.");
		setPermission("novastrike.party");
		setArgumentLength(2);
		setUsageMessage("/party promote <PlayerName>");
		setUniversalCommand(true);
	}

	public void execute(CommandSender sender, String[] args) {
		PartyManager partyManager = NovaStrike.getInstance().getPartyManager();
		
		if(!(sender instanceof Player)) {
			Messager.sendErrorMessage(sender, ChatColor.RED + "You must be a player to execute this command.");
			return;
		}
		
		Player player = (Player) sender;
		Player otherPlayer = Bukkit.getPlayer(args[1]);
		
		if(otherPlayer == null) {
			Messager.sendErrorMessage(player, ChatColor.RED + args[1] + " is not a valid player.");
			return;
		}
		
		if(partyManager.getPlayerParty(player.getUniqueId()) == null) {
			Messager.sendErrorMessage(player, ChatColor.RED + "You are not in a party.");
			return;
		}
		
		Party party = partyManager.getPlayerParty(player.getUniqueId());
		
		if(party.getLeader() != player.getUniqueId()) {
			Messager.sendErrorMessage(player, ChatColor.RED + "You are not the leader of your party.");
			return;
		}
		
		party.setLeader(otherPlayer);
	}
}
