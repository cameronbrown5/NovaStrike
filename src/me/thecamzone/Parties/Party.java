package me.thecamzone.Parties;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import me.thecamzone.NovaStrike;
import me.thecamzone.Utils.Messager;
import net.md_5.bungee.api.ChatColor;

public class Party {

	private List<UUID> players = new ArrayList<>();
	private List<UUID> invitedPlayers = new ArrayList<>();
	private UUID id;
	private String leaderName;
	private UUID leader;
	public final int maxPlayers = 5;
	private BossBar bar;
	private String message = "Waiting for Party Leader...";
	
	PartyManager partyManager = NovaStrike.getInstance().getPartyManager();
	
	public Party(UUID id, Player leader) {
		this.id = id;
		this.leaderName = leader.getName();
		this.leader = leader.getUniqueId();
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
		String barTitle = getLeaderName() + "'s Party | " + getPlayers().size() + " / " + maxPlayers + " | " + message;
		
		if(bar == null) {
        	bar = Bukkit.getServer().createBossBar(
            	barTitle, 
            	BarColor.PURPLE, 
            	BarStyle.SOLID
            );
        }
		
		bar.setTitle(barTitle);
		
		bar.setVisible(false);
		bar.removeAll();
        
        for(UUID uuid : players) {
        	if(uuid == null || Bukkit.getPlayer(uuid) == null) {
        		continue;
        	}
        	
        	bar.addPlayer(Bukkit.getPlayer(uuid));
        }
        
        bar.setVisible(true);
	}
	
	public void addPlayer(Player player) {
		if(players.contains(player.getUniqueId())) return;
		
		sendPartySuccessMessage(ChatColor.GREEN + player.getName() + " has joined the party.");
		
		players.add(player.getUniqueId());
		partyManager.addPlayerToParty(player.getUniqueId(), this);
		
		refreshBar();
	}
	
	public void removePlayer(Player player) {
		if(!players.contains(player.getUniqueId())) return;
		
		players.remove(player.getUniqueId());
		partyManager.removePlayerFromParty(player.getUniqueId());
		
		sendPartySuccessMessage(ChatColor.RED + player.getName() + " has left the party.");
		
		if(leader == player.getUniqueId()) {
			if(players.size() > 0) {
				Random random = new Random();
				
				int randomNumber = random.nextInt(players.size());
				
				setLeader(Bukkit.getPlayer(players.get(randomNumber)));
			}

			partyManager.removeParty(this);
		}
		
		refreshBar();
	}
	
	public void removePlayer(UUID player) {
		if(!players.contains(player)) return;
		
		players.remove(player);
		partyManager.removePlayerFromParty(player);
		
		if(leader == player) {
			if(players.size() > 0) {
				Random random = new Random();
				
				int randomNumber = random.nextInt(players.size());
				
				setLeader(Bukkit.getPlayer(players.get(randomNumber)));
			}

			partyManager.removeParty(this);
		}
		
		refreshBar();
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
	
	public String getLeaderName() {
		return leaderName;
	}
	
	public UUID getLeader() {
		return leader;
	}
	

	public void setLeader(Player player) {
		leader = player.getUniqueId();
		leaderName = player.getName();
		
		sendPartySuccessMessage(ChatColor.GREEN + leaderName + " is now the party leader.");
		
		refreshBar();
	}
	
	public void sendPartySuccessMessage(String message) {
		for(UUID uuid : players) {
			Player p = Bukkit.getPlayer(uuid);
			
			if(p == null) {
				continue;
			}
			
			Messager.sendSuccessMessage(p, message);
		}
	}
	
	public void sendPartyErrorMessage(String message) {
		for(UUID uuid : players) {
			Player p = Bukkit.getPlayer(uuid);
			
			if(p == null) {
				continue;
			}
			
			Messager.sendErrorMessage(p, message);
		}
	}
	
	public void sendPartyMessage(String message) {
		for(UUID uuid : players) {
			Player p = Bukkit.getPlayer(uuid);
			
			if(p == null) {
				continue;
			}
			
			p.sendMessage(message);
		}
	}
	
}
