package me.thecamzone.gamePlayer;

import me.thecamzone.Utils.StringUtil;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PlayerWrapper extends CraftPlayer {
    public PlayerWrapper(Player p) {
        super((CraftServer) p.getServer(), ((CraftPlayer) p).getHandle());
    }

    @Override
    public void sendMessage(String message) {
        StringUtil.formatColor(message);
    }

    @Override
    public void sendMessage(String... messages) {
        for (String m : messages) sendMessage(m);

    }

}
