package me.thecamzone;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import me.thecamzone.Commands.NovaStrike.NovaStrikeCommand;
import me.thecamzone.Commands.NovaStrike.NovaStrikeCommandCompleter;
import me.thecamzone.Commands.NovaStrike.NovaStrikeCommandRunner;
import me.thecamzone.Commands.NovaStrike.SubCommands.NovaStrikeHelpCommand;
import me.thecamzone.Commands.Party.SubCommands.PartyAcceptCommand;
import me.thecamzone.Commands.Party.SubCommands.PartyHelpCommand;
import me.thecamzone.Commands.Party.SubCommands.PartyInviteCommand;
import me.thecamzone.Commands.Party.SubCommands.PartyListCommand;
import me.thecamzone.Parties.PartyManager;
import me.thecamzone.Commands.Party.PartyCommand;
import me.thecamzone.Commands.Party.PartyCommandCompleter;
import me.thecamzone.Commands.Party.PartyCommandRunner;
import me.thecamzone.Utils.DataFile;

public class NovaStrike extends JavaPlugin {

	private static NovaStrike plugin;
	
	private PartyManager partyManager;
	
	private final Map<String, NovaStrikeCommand> novaStrikeSubcommands = new LinkedHashMap<>();
	private final Map<String, PartyCommand> partySubcommands = new LinkedHashMap<>();
	
	@Override
	public void onEnable() {
		plugin = this;
		
		partyManager = new PartyManager();
		
		loadFiles();
		registerListeners();
		registerCommands();
	}
	
	public static NovaStrike getInstance() {
		return plugin;
	}
	
	private void loadFiles() {
		makeDir();
		
		DataFile.setup();
	}
	
	private void registerListeners() {
		//getServer().getPluginManager().registerEvents(new PlayerExpChangeEventHandler(), this);
	}
	
	private void registerCommands() {
		// NOVASTRIKE SUBCOMMANDS
		this.novaStrikeSubcommands.put("help", new NovaStrikeHelpCommand());
		
		// PARTY SUBCOMMANDS
		this.partySubcommands.put("help", new PartyHelpCommand());
		this.partySubcommands.put("invite", new PartyInviteCommand());
		this.partySubcommands.put("accept", new PartyAcceptCommand());
		this.partySubcommands.put("list", new PartyListCommand());
		
		getCommand("novastrike").setExecutor((CommandExecutor) new NovaStrikeCommandRunner());
		getCommand("novastrike").setTabCompleter((TabCompleter) new NovaStrikeCommandCompleter());
		
		getCommand("party").setExecutor((CommandExecutor) new PartyCommandRunner());
		getCommand("party").setTabCompleter((TabCompleter) new PartyCommandCompleter());
	}
	
	public Map<String, NovaStrikeCommand> getNovaStrikeSubcommands() {
		return novaStrikeSubcommands;
	}
	
	public Map<String, PartyCommand> getPartySubcommands() {
		return partySubcommands;
	}
	
	private Boolean makeDir() {
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
			return true;
		}
		return false;
	}

	public PartyManager getPartyManager() {
		return partyManager;
	}
	
}
