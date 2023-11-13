package me.thecamzone.Commands.Party.SubCommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thecamzone.NovaStrike;
import me.thecamzone.Commands.Party.PartyCommand;
import me.thecamzone.Parties.PartyInvite;
import me.thecamzone.Parties.PartyManager;
import me.thecamzone.Utils.Messager;
import net.md_5.bungee.api.ChatColor;

public class PartyInvitesCommand extends PartyCommand {
	public PartyInvitesCommand() {
		setName("invites");
		setInfoMessage("Lists all your invites.");
		setPermission("novastrike.party");
		setArgumentLength(1);
		setUsageMessage("/party invites");
		setUniversalCommand(true);
	}

	public void execute(CommandSender sender, String[] args) {
		PartyManager partyManager = NovaStrike.getInstance().getPartyManager();
		
		if(!(sender instanceof Player)) {
			Messager.sendErrorMessage(sender, ChatColor.RED + "You must be a player to execute this command.");
			return;
		}
		
		Player player = (Player) sender;
		
		player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Party Invite List");
		
		int count = 0;
		
		for(PartyInvite partyInvite : partyManager.getPlayerInvites(player.getUniqueId())) {
			if(!partyInvite.getParty().getPlayers().contains(partyInvite.getInviter())) {
				partyManager.removePlayerInvite(player.getUniqueId(), partyInvite);
				continue;
			}
			
			count++;
			player.sendMessage(ChatColor.WHITE + "- " + ChatColor.GRAY + partyInvite.getInviterName());
		}
		
		if(count == 0) {
			player.sendMessage(ChatColor.GRAY + "You currently do not have any party invites.");
		}
		
	}
}
