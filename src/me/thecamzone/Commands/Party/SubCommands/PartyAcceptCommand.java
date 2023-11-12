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

public class PartyAcceptCommand extends PartyCommand {
	public PartyAcceptCommand() {
		setName("accept");
		setInfoMessage("Accepts an invite.");
		setPermission("novastrike.party");
		setArgumentLength(1);
		setUsageMessage("/party accept [PlayerName]");
		setUniversalCommand(true);
	}

	public void execute(CommandSender sender, String[] args) {
		PartyManager partyManager = NovaStrike.getInstance().getPartyManager();
		
		if(!(sender instanceof Player)) {
			Messager.sendErrorMessage(sender, ChatColor.RED + "You must be a player to execute this command.");
			return;
		}
		
		Player player = (Player) sender;
		
		if(args.length == 1) {
			if(partyManager.getPlayerInvites(player.getUniqueId()).size() == 0) {
				Messager.sendErrorMessage(player, ChatColor.RED + "You have no invites to accept.");
				return;
			}
			
			if(partyManager.getPlayerInvites(player.getUniqueId()).size() == 1) {
				Party party = partyManager.getPlayerInvites(player.getUniqueId()).get(0);
				
				if(party.getPlayers().size() > party.maxPlayers) {
					Messager.sendErrorMessage(player, ChatColor.RED + "The party you tried to join is full.");
					return;
				}
				
				party.addPlayer(player);
				
				for(Party invite : partyManager.getPlayerInvites(player.getUniqueId())) {
					invite.removeInvite(player);
				}
				
				partyManager.removePlayerInvite(player.getUniqueId(), party);
				
				Messager.sendSuccessMessage(player, ChatColor.GREEN + "Successfully joined " + Bukkit.getPlayer(party.getLeader()).getName() +  "'s party.");
			}
		}
	}
}
