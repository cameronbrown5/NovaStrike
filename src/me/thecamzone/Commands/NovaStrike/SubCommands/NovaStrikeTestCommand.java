package me.thecamzone.Commands.NovaStrike.SubCommands;

import me.thecamzone.Commands.NovaStrike.NovaStrikeCommand;
import me.thecamzone.Utils.Messager;
import me.thecamzone.server_connections.ConnectionManager;
import org.bukkit.command.CommandSender;

public class NovaStrikeTestCommand extends NovaStrikeCommand {
	public NovaStrikeTestCommand() {
		setName("test");
		setInfoMessage("");
		setPermission("novastrike.default");
		setArgumentLength(1);
		setUsageMessage("/ns help");
		setUniversalCommand(true);
	}

	public void execute(CommandSender sender, String[] args) {
		ConnectionManager.sendMessageToProxy("Test");
	}
}
