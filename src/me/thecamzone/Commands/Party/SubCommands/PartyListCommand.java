package me.thecamzone.Commands.Party.SubCommands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thecamzone.NovaStrike;
import me.thecamzone.Commands.Party.PartyCommand;
import me.thecamzone.Parties.Party;
import me.thecamzone.Parties.PartyManager;
import me.thecamzone.Utils.Messager;
import net.md_5.bungee.api.ChatColor;

public class PartyListCommand extends PartyCommand {
	public PartyListCommand() {
		setName("list");
		setInfoMessage("Lists members in your party");
		setPermission("novastrike.party");
		setArgumentLength(1);
		setUsageMessage("/party list");
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
			Messager.sendErrorMessage(sender, ChatColor.RED + "You are not apart of a party.");
		}
		
		player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Party List");
		for(UUID p : partyManager.getPlayerParty(player.getUniqueId()).getPlayers()) {
			Player partyPlayer = Bukkit.getPlayer(p);
			
			if(partyPlayer == null) {
				continue;
			}
			
			player.sendMessage(ChatColor.WHITE + "- " + ChatColor.GRAY + partyPlayer.getName());
		}
		
	}
}
