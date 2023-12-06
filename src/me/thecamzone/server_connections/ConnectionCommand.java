package me.thecamzone.server_connections;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ConnectionCommand {

    public void handleIncomingMessage(String rawMessage) {
        String[] args = rawMessage.split(" ");
        String command = args[0].toUpperCase();

        switch(command) {
            case party:
        }
    }

    public void commandPartyInviteReceive(String[] args) {

    }

    public void commandPartyInviteSend(Player sender, Player receiver) {

    }

    public void updatePlayerCountSend() {
        ConnectionManager.connection.sendMessageToProxy("updatePlayerCount " + Bukkit.getOnlinePlayers().size());
    }


}
