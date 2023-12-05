package me.thecamzone.Events;

import me.thecamzone.NovaStrike;
import me.thecamzone.gamePlayer.GPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;


public abstract class NovaListener implements Listener {

    private GPlayer getGPlayer(PlayerEvent e) {
        return getGplayer(e.getPlayer());
    }

    private GPlayer getGplayer(Player p) {
        return NovaStrike.getInstance().getgPlayerManager().getGPlayer(p);
    }

}
