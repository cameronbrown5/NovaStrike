package me.thecamzone.Commands.NovaStrike.SubCommands;
import me.thecamzone.Commands.NovaStrike.NovaStrikeCommand;
import me.thecamzone.Utils.StringUtil;
import me.thecamzone.server_connections.ConnectionManager;
import org.bukkit.command.CommandSender;
public class NovaStrikeMessageProxyCommand extends NovaStrikeCommand {
    public NovaStrikeMessageProxyCommand() {
        setName("messageproxy");
        setInfoMessage("");
        setPermission("novastrike.default");
        setArgumentLength(2);
        setUsageMessage("/ns messageproxy <Message>");
        setUniversalCommand(true);
    }
    public void execute(CommandSender sender, String[] args) {
        ConnectionManager.connection.sendMessageToProxy(StringUtil.displayArray(args));
    }
}