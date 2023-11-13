package me.thecamzone.Commands.Party.SubCommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thecamzone.NovaStrike;
import me.thecamzone.Commands.Party.PartyCommand;
import me.thecamzone.Parties.Party;
import me.thecamzone.Parties.PartyManager;
import me.thecamzone.Utils.Messager;
import net.md_5.bungee.api.ChatColor;

public class PartyLeaveCommand extends PartyCommand {
	public PartyLeaveCommand() {
		setName("leave");
		setInfoMessage("Leaves your party.");
		setPermission("novastrike.party");
		setArgumentLength(1);
		setUsageMessage("/party leave");
		setUniversalCommand(true);
	}

	public void execute(CommandSender sender, String[] args) {
		PartyManager partyManager = NovaStrike.getInstance().getPartyManager();
		
		if(!(sender instanceof Player)) {
			Messager.sendErrorMessage(sender, ChatColor.RED + "You must be a player to execute this command.");
			return;
		}
		
		Player player = (Player) sender;
		
		if(partyManager.getPlayerParty(player.getUniqueId()) == null) {
			Messager.sendErrorMessage(sender, ChatColor.RED + "You are not in a party.");
			return;
		}
		
		Party party = partyManager.getPlayerParty(player.getUniqueId());
		
		party.removePlayer(player);
		
		Messager.sendSuccessMessage(player, ChatColor.GREEN + "You have successfully left the party.");
		
		
	}
}
