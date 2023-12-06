package me.thecamzone.Utils.jcommandbuilder.JArgument;

import org.bukkit.Material;

public class JArgument_Material extends JArgument<Material> {
    public JArgument_Material(String argName) {
        super(argName);
    }

    @Override
    protected Material convert(String rawGivenArg) {
        return Material.valueOf(rawGivenArg);
    }

    @Override
    public JArgumentValidateResponse validate(String rawGivenArg) {
        try {
            Material.valueOf(rawGivenArg);
        }catch (Exception e){
            return new JArgumentValidateResponse(true, getNameWithBracketsAndColor() + " &cinvalid material.");
        }

        return VALID;
    }
}
