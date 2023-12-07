package me.thecamzone.Events;

import me.thecamzone.NovaStrike;
import me.thecamzone.Parties.Party;
import me.thecamzone.Parties.PartyManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinHandler extends NovaListener {

	PartyManager partyManager = NovaStrike.getInstance().getPartyManager();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();

		NovaStrike.getInstance().getgPlayerManager().getGPlayer(player);
		handleParty(player);
	}
	
	public void handleParty(Player player) {
		if(partyManager.getPlayerParty(player.getUniqueId()) == null) {
			return;
		}
		
		Party party = partyManager.getPlayerParty(player.getUniqueId());
		
		Bukkit.getConsoleSender().sendMessage("This is running");
		party.refreshBar();
	}
	
}
