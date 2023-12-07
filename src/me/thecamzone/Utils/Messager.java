package me.thecamzone.Utils;

import me.thecamzone.Commands.Party.PartyCommand;
import me.thecamzone.NovaStrike;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class Messager {
	
	private static NovaStrike plugin = NovaStrike.getInstance();
	
	public static void sendMessage(CommandSender messageReceiver, String message) {
		messageReceiver.sendMessage(StringUtil.formatColor(message));
	}

	public static void sendSuccessMessage(CommandSender messageReceiver, String message) {
		sendMessage(messageReceiver, message);
		SFXManager.playSuccessSound(messageReceiver);
	}

	public static void sendErrorMessage(CommandSender messageReceiver, String message) {
		sendMessage(messageReceiver, message);
		SFXManager.playErrorSound(messageReceiver);
	}
	
	public static void sendPartyHelpMessage(CommandSender messageReceiver) {
		StringBuilder finalMessage = new StringBuilder("&b&lParty Commands\n");
		Iterator<PartyCommand> subcommandIterator = plugin.getPartySubcommands().values().iterator();
		while (subcommandIterator.hasNext()) {
			PartyCommand subcommand = subcommandIterator.next();
			String permission = subcommand.getPermission();
			if (!permission.isEmpty() && !messageReceiver.hasPermission(subcommand.getPermission()))
				continue;
			finalMessage.append("&7").append(subcommand.getUsageMessage()).append(" &b- &e")
					.append(subcommand.getInfoMessage());
			if (subcommandIterator.hasNext())
				finalMessage.append("\n");
		}
		sendSuccessMessage(messageReceiver, finalMessage.toString());
	}

	public static void sendNoPermissionMessage(CommandSender messageReceiver) {
		sendErrorMessage(messageReceiver, "&cYou do not have permissions to use this command!");
	}

	public static void sendActionBarMessage(Player messageReceiver, String message) {
		messageReceiver.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(StringUtil.formatColor(message)));
	}

	public static void sendTitleMessage(Player messageReceiver, String title, String subtitle, double fadeIn,
			double duration, double fadeOut) {
		if (title == null)
			return;
		title = StringUtil.formatColor(title);
		if (subtitle != null)
			subtitle = StringUtil.formatColor(subtitle);
		messageReceiver.sendTitle(title, subtitle, (int) fadeIn * 20, (int) duration * 20, (int) fadeOut * 20);
	}

	public static void sendGlobalMessage(String message) {
		for (Player online : Bukkit.getOnlinePlayers())
			sendMessage((CommandSender) online, message);
	}

	public static void sendGlobalActionbarMessage(String message) {
		for (Player online : Bukkit.getOnlinePlayers())
			sendActionBarMessage(online, message);
	}

	public static void sendGlobalTitle(String title, String subtitle, double fadeIn, double duration, double fadeOut) {
		for (Player online : Bukkit.getOnlinePlayers())
			sendTitleMessage(online, title, subtitle, fadeIn, duration, fadeOut);
	}
}
