package me.thecamzone.Commands.Party.SubCommands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thecamzone.NovaStrike;
import me.thecamzone.Commands.Party.PartyCommand;
import me.thecamzone.Parties.Party;
import me.thecamzone.Parties.PartyManager;
import me.thecamzone.Utils.Messager;
import net.md_5.bungee.api.ChatColor;

public class PartyKickCommand extends PartyCommand {
	public PartyKickCommand() {
		setName("kick");
		setInfoMessage("Kicks another player from your party.");
		setPermission("novastrike.party");
		setArgumentLength(2);
		setUsageMessage("/party kick <PlayerName>");
		setUniversalCommand(true);
	}

	public void execute(CommandSender sender, String[] args) {
		PartyManager partyManager = NovaStrike.getInstance().getPartyManager();
		
		if(!(sender instanceof Player)) {
			Messager.sendErrorMessage(sender, ChatColor.RED + "You must be a player to execute this command.");
			return;
		}
		
		Player player = (Player) sender;
		@SuppressWarnings("deprecation")
		OfflinePlayer otherPlayer = Bukkit.getOfflinePlayer(args[1]);
		
		if(partyManager.getPlayerParty(player.getUniqueId()) == null) {
			Messager.sendErrorMessage(player, ChatColor.RED + "You are not in a party.");
			return;
		}
		
		Party party = partyManager.getPlayerParty(player.getUniqueId());
		
		if(!party.getPlayers().contains(otherPlayer.getUniqueId())) {
			Messager.sendErrorMessage(player, ChatColor.RED + otherPlayer.getName() + " is not in your party.");
		}
		
		if(!party.getLeader().equals(player.getUniqueId())) {
			Messager.sendErrorMessage(player, ChatColor.RED + "You are not the leader of your party.");
			return;
		}
		
		party.removePlayer(otherPlayer.getUniqueId());
		party.sendPartySuccessMessage(ChatColor.RED + otherPlayer.getName() + " was kicked out of the party.");
	}
}
