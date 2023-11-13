package me.thecamzone.Commands.Party.SubCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thecamzone.NovaStrike;
import me.thecamzone.Commands.Party.PartyCommand;
import me.thecamzone.Parties.Party;
import me.thecamzone.Parties.PartyInvite;
import me.thecamzone.Parties.PartyManager;
import me.thecamzone.Utils.Messager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class PartyInviteCommand extends PartyCommand {
	public PartyInviteCommand() {
		setName("invite");
		setInfoMessage("Invites another player to the party.");
		setPermission("novastrike.party");
		setArgumentLength(2);
		setUsageMessage("/party invite <PlayerName>");
		setUniversalCommand(true);
	}

	@SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args) {
		PartyManager partyManager = NovaStrike.getInstance().getPartyManager();
		
		if(!(sender instanceof Player)) {
			Messager.sendErrorMessage(sender, ChatColor.RED + "You must be a player to execute this command.");
			return;
		}
		
		Player player = (Player) sender;
		Player invited = Bukkit.getPlayer(args[1]);
		
		if(invited == null) {
			Messager.sendErrorMessage(player, ChatColor.RED + args[1] + " is not a valid player.");
			return;
		}
		
		if(invited.getUniqueId().equals(player.getUniqueId())) {
			Messager.sendErrorMessage(player, ChatColor.RED + "You cannot invite yourself.");
			return;
		}
		
		if(partyManager.getPlayerParty(invited.getUniqueId()) != null) {
			Messager.sendErrorMessage(player, ChatColor.RED + invited.getName() + " is already in a party.");
			return;
		}
		
		Party party;
		if(partyManager.getPlayerParty(player.getUniqueId()) == null) {
			party = partyManager.createParty(player);
			party.addPlayer(player);
		} else {
			party = partyManager.getPlayerParty(player.getUniqueId());
		}
		
		if(party.addInvite(invited)) {
			PartyInvite partyInvite = new PartyInvite(party, player);
			partyManager.addPlayerInvite(invited.getUniqueId(), partyInvite);
			
			TextComponent component = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&aClick &lHERE &ato join.")));
			component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + partyInvite.getInviterName()));
			component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Accept party request from " + partyInvite.getInviterName() ).create()));
			
			invited.sendMessage(ChatColor.YELLOW + "====================================");
			Messager.sendSuccessMessage(invited, ChatColor.GREEN + player.getDisplayName() + " has invited you to their party.");
			invited.spigot().sendMessage(component);
			invited.sendMessage(ChatColor.YELLOW + "====================================");
			
			Messager.sendSuccessMessage(player, ChatColor.GREEN + "Successfully invited " + invited.getName() + " to the party.");
		} else {
			Messager.sendErrorMessage(player, ChatColor.RED + invited.getName() + " has already been invited to this party.");
			return;
		}
	}
}
