package me.thecamzone.Parties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

public class PartyManager {

	private final Map<UUID, Party> parties = new HashMap<>();
	private final Map<UUID, Party> playerPartyMap = new HashMap<>();
	private final Map<UUID, List<Party>> playerInvites = new HashMap<>();
	
	public Party createParty(UUID leader) {
        UUID partyId = generateUniquePartyId();
        Party party = new Party(partyId, leader);
        parties.put(partyId, party);
        addPlayerToParty(leader, party);
        return party;
    }
	
	public Party getParty(UUID partyId) {
        return parties.get(partyId);
    }
	
	public void addPlayerToParty(UUID player, Party party) {
		playerPartyMap.put(player, party);
	}

	public Party getPlayerParty(UUID player) {
	    return playerPartyMap.get(player);
	}

	public void removePlayerFromParty(UUID player) {
	    playerPartyMap.remove(player);
	}
	
	public void addPlayerInvite(UUID player, Party party) {
		if(!playerInvites.containsKey(player)) {
			ArrayList<Party> invites = new ArrayList<>();
			invites.add(party);
			
			playerInvites.put(player, invites);
		} else {
			List<Party> invites = new ArrayList<>();
			invites = playerInvites.get(player);
			
			invites.add(party);
			playerInvites.put(player, invites);
		}
	}
	
	public void removePlayerInvite(UUID player, Party party) {
		if(playerInvites.containsKey(player)) {
			playerInvites.get(player).remove(party);
		}
	}
	
	public List<Party> getPlayerInvites(UUID player) {
		return playerInvites.getOrDefault(player, new ArrayList<Party>());
	}
	
	public UUID generateUniquePartyId() {
        UUID partyId;
        boolean isUnique;
        do {
            partyId = UUID.randomUUID();
            isUnique = !parties.containsKey(partyId);
        } while (!isUnique);

        return partyId;
    }
	
}
