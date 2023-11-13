package me.thecamzone.Parties;

import java.util.UUID;

import org.bukkit.entity.Player;

public class PartyInvite {

	private Party party;
	private String inviterName;
	private UUID inviter;
	
	public PartyInvite(Party party, Player inviter) {
		this.party = party;
		this.inviterName = inviter.getName();
		this.inviter = inviter.getUniqueId();
	}

	public Party getParty() {
		return party;
	}

	public String getInviterName() {
		return inviterName;
	}

	public UUID getInviter() {
		return inviter;
	}
	
}
