package me.thecamzone.Utils.jcommandbuilder.JArgument;

import me.thecamzone.NovaStrike;
import me.thecamzone.gamePlayer.GPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class JArgument_GPlayer extends JArgument<GPlayer> {

    /*
        Uses UUID instead of player's name.
     */

    public JArgument_GPlayer(String argName) {
        super(argName);
    }

    @Override
    public JArgumentValidateResponse validate(String rawGivenArg) {
        Player player = Bukkit.getPlayer(rawGivenArg);

        if (player != null) return VALID;

        return new JArgumentValidateResponse(true, "player not found.");
    }

    @Override
    protected GPlayer convert(String rawGivenArg) {
        return NovaStrike.getInstance().getgPlayerManager().getGPlayer(Bukkit.getPlayer(rawGivenArg));
    }

}
