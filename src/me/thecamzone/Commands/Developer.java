package me.thecamzone.Commands;

import me.thecamzone.Utils.jcommandbuilder.JCommandSender;
import me.thecamzone.Utils.jcommandbuilder.annotations.JCommand;
import me.thecamzone.Utils.jcommandbuilder.annotations.JCommandPlayerOnly;
import me.thecamzone.Utils.jcommandbuilder.annotations.JSubCommand;
import me.thecamzone.personalStash.PersonalStash;

public class Developer {

    @JCommand("Dev")
    @JSubCommand("OpenPersonalStash")
    @JCommandPlayerOnly
    public void openPersonalStash(JCommandSender sender) {
        PersonalStash.getInstance().openGui(sender.asGPlayer());
    }

}
