package me.thecamzone;

import me.thecamzone.Commands.CommandRegister;
import me.thecamzone.Commands.NovaStrikeCommand;
import me.thecamzone.Commands.Party.PartyCommand;
import me.thecamzone.Commands.Party.PartyCommandCompleter;
import me.thecamzone.Commands.Party.PartyCommandRunner;
import me.thecamzone.Commands.Party.SubCommands.*;
import me.thecamzone.Events.InventoryClickHandler;
import me.thecamzone.Events.InventoryCloseHandler;
import me.thecamzone.Events.InventoryDragHandler;
import me.thecamzone.Events.PlayerJoinHandler;
import me.thecamzone.Parties.PartyManager;
import me.thecamzone.Utils.DataFile;
import me.thecamzone.gamePlayer.GPlayerManager;
import me.thecamzone.server_connections.ConnectionManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.Map;

public class NovaStrike extends JavaPlugin {

	private static NovaStrike plugin;

	private GPlayerManager gPlayerManager;
	private PartyManager partyManager;

	private final Map<String, NovaStrikeCommand> novaStrikeSubcommands = new LinkedHashMap<>();
	private final Map<String, PartyCommand> partySubcommands = new LinkedHashMap<>();
	
	@Override
	public void onEnable() {
		plugin = this;

		gPlayerManager = new GPlayerManager();
		partyManager = new PartyManager();
		
		loadFiles();
		registerListeners();

		new CommandRegister();
		registerCommands();

		ConnectionManager.connect();
	}
	
	public static NovaStrike getInstance() {
		return plugin;
	}
	
	private void loadFiles() {
		makeDir();
		
		DataFile.setup();
	}
	
	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new PlayerJoinHandler(), this);
		getServer().getPluginManager().registerEvents(new InventoryClickHandler(), this);
		getServer().getPluginManager().registerEvents(new InventoryCloseHandler(), this);
		getServer().getPluginManager().registerEvents(new InventoryDragHandler(), this);
	}
	
	private void registerCommands() {
		
		// PARTY SUBCOMMANDS
		this.partySubcommands.put("help", new PartyHelpCommand());
		this.partySubcommands.put("invite", new PartyInviteCommand());
		this.partySubcommands.put("invites", new PartyInvitesCommand());
		this.partySubcommands.put("accept", new PartyAcceptCommand());
		this.partySubcommands.put("list", new PartyListCommand());
		this.partySubcommands.put("leave", new PartyLeaveCommand());
		this.partySubcommands.put("promote", new PartyPromoteCommand());
		this.partySubcommands.put("kick", new PartyKickCommand());
		
		getCommand("party").setExecutor((CommandExecutor) new PartyCommandRunner());
		getCommand("party").setTabCompleter((TabCompleter) new PartyCommandCompleter());
	}
	
	public Map<String, NovaStrikeCommand> getNovaStrikeSubcommands() {
		return novaStrikeSubcommands;
	}
	
	public Map<String, PartyCommand> getPartySubcommands() {
		return partySubcommands;
	}
	
	private void makeDir() {
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
	}

	public PartyManager getPartyManager() {
		return partyManager;
	}

	public GPlayerManager getgPlayerManager() {
		return gPlayerManager;
	}
}
