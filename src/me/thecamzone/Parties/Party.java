package me.thecamzone.Parties;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import me.thecamzone.Utils.Messager;
import net.md_5.bungee.api.ChatColor;

public class Party {

	private List<UUID> players = new ArrayList<>();
	private List<UUID> invitedPlayers = new ArrayList<>();
	private UUID id;
	private UUID leader;
	public final int maxPlayers = 5;
	private BossBar bar;
	private String message = "Waiting for Party Leader...";
	
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
	
	public void setBossBar(BossBar bar) {
		this.bar = bar;
	}
	
	public BossBar getBossBar() {
		return bar;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void refreshBar() {
		String barTitle = Bukkit.getPlayer(getLeader()).getName() + "'s Party | " + getPlayers().size() + " / " + maxPlayers + " | " + message;
		
		if(bar == null) {
        	bar = Bukkit.getServer().createBossBar(
            	barTitle, 
            	BarColor.PURPLE, 
            	BarStyle.SOLID
            );
        }
		
		bar.setTitle(barTitle);
		
		bar.removeAll();
        
        for(UUID uuid : players) {
        	bar.addPlayer(Bukkit.getPlayer(uuid));
        	bar.setVisible(true);
        }
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
		
		refreshBar();
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
