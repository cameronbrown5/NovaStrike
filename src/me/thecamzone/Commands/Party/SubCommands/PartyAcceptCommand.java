package me.thecamzone.Commands.Party.SubCommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thecamzone.NovaStrike;
import me.thecamzone.Commands.Party.PartyCommand;
import me.thecamzone.Parties.Party;
import me.thecamzone.Parties.PartyInvite;
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
		
		if(partyManager.getPlayerInvites(player.getUniqueId()).size() == 0) {
			Messager.sendErrorMessage(player, ChatColor.RED + "You have no invites to accept.");
			return;
		}
		
		if(args.length == 1) {
			if(partyManager.getPlayerInvites(player.getUniqueId()).size() == 1) {
				PartyInvite party = partyManager.getPlayerInvites(player.getUniqueId()).get(0);
				acceptInvite(player, party);
				return;
			} else if(partyManager.getPlayerInvites(player.getUniqueId()).size() > 1) {
				Messager.sendErrorMessage(player, ChatColor.RED + "Please specify which party you would like to join.");
				return;
			}
		}
		
		if(args.length > 1) {
			String input = args[1];
			
			Boolean contains = false;
			PartyInvite foundPartyInvite = null;
			for(PartyInvite partyInvite : partyManager.getPlayerInvites(player.getUniqueId())) {
				if(partyInvite.getInviterName().equalsIgnoreCase(input)) {
					contains = true;
					foundPartyInvite = partyInvite;
					break;
				}
			}
			
			if(!contains || foundPartyInvite == null) {
				Messager.sendErrorMessage(player, ChatColor.RED + "You do not have a party invite from " + input + ".");
				return;
			}

			acceptInvite(player, foundPartyInvite);
		}
	}
	
	private void acceptInvite(Player player, PartyInvite partyInvite) {
		PartyManager partyManager = NovaStrike.getInstance().getPartyManager();
		
		if(!partyInvite.getParty().getPlayers().contains(partyInvite.getInviter())) {
			Messager.sendErrorMessage(player, ChatColor.RED + "This invite is no longer valid.");
			partyManager.removePlayerInvite(player.getUniqueId(), partyInvite);
			return;
		}
		
		if(partyManager.getPlayerInvites(player.getUniqueId()).size() == 1) {
			if(partyInvite.getParty().getPlayers().size() > partyInvite.getParty().maxPlayers) {
				Messager.sendErrorMessage(player, ChatColor.RED + "The party you tried to join is full.");
				return;
			}
			
			partyInvite.getParty().addPlayer(player);
			
			for(PartyInvite invite : partyManager.getPlayerInvites(player.getUniqueId())) {
				Party invitedParty = invite.getParty();
				invitedParty.removeInvite(player);
			}
			
			partyManager.removePlayerInvite(player.getUniqueId(), partyInvite);
			
			Messager.sendSuccessMessage(player, ChatColor.GREEN + "Successfully joined " + partyInvite.getParty().getLeaderName() +  "'s party.");
		}
	}
}
