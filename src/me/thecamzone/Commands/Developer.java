package me.thecamzone.Commands;

import me.thecamzone.Utils.jcommandbuilder.JArgument.JArg;
import me.thecamzone.Utils.jcommandbuilder.JArgument.JArgument_Int;
import me.thecamzone.Utils.jcommandbuilder.JCommandSender;
import me.thecamzone.Utils.jcommandbuilder.JTabProvider;
import me.thecamzone.Utils.jcommandbuilder.annotations.JCommand;
import me.thecamzone.Utils.jcommandbuilder.annotations.JCommandPlayerOnly;
import me.thecamzone.Utils.jcommandbuilder.annotations.JSubCommand;
import me.thecamzone.personalStash.PersonalStash;

import java.util.List;

public class Developer implements JTabProvider {

    @JCommand("Dev")
    @JSubCommand("OpenPersonalStash")
    @JCommandPlayerOnly
    public void openPersonalStash(JCommandSender sender) {
        PersonalStash.getInstance().openGui(sender.asGPlayer());
    }

    @JCommand("Dev")
    @JSubCommand("test")
    @JArg(name="name", type= JArgument_Int.class, defaultValue="2", tabProvider = "truefalse", tabProviderCase = "UPPER")
    public void test(JCommandSender sender, int name) {
        sender.sendMessage("Hi" + name);
    }


    @Override
    public List<String> getTabs() {
        return null;
    }
}
