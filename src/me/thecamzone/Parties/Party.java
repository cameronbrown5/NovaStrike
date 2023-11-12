package me.thecamzone.Parties;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.thecamzone.Utils.Messager;
import net.md_5.bungee.api.ChatColor;

public class Party {

	private List<UUID> players = new ArrayList<>();
	private List<UUID> invitedPlayers = new ArrayList<>();
	private UUID id;
	private UUID leader;
	public final int maxPlayers = 5;
	
	public Party(UUID id, UUID leader) {
		this.id = id;
		this.leader = leader;
	}
	
	public List<UUID> getPlayers() {
		return players;
	}
	
	public List<UUID> getInvitedPlayers() {
		return invitedPlayers;
	}
	
	public void addPlayer(Player player) {
		if(players.contains(player.getUniqueId())) return;
		
		for(UUID uuid : players) {

			Player p = Bukkit.getPlayer(uuid);
			
			if(p == null) {
				continue;
			}
			
			Messager.sendSuccessMessage(p, ChatColor.GREEN + player.getName() + " has joined the party.");
			
		}
		
		players.add(player.getUniqueId());
	}
	
	public void removePlayer(Player player) {
		players.remove(player.getUniqueId());
	}
	
	public boolean addInvite(Player player) {
		if(invitedPlayers.contains(player.getUniqueId())) {
			return false;
		}
		
		invitedPlayers.add(player.getUniqueId());
		
		return true;
	}
	
	public boolean removeInvite(Player player) {
		if(!invitedPlayers.contains(player.getUniqueId())) {
			return false;
		}
		
		invitedPlayers.remove(player.getUniqueId());
		return true;
	}
	
	public UUID getID() {
		return id;
	}
	
	public UUID getLeader() {
		return leader;
	}
	
}
