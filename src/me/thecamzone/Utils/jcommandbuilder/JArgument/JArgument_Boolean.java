package me.thecamzone.Utils.jcommandbuilder.JArgument;

public class JArgument_Boolean extends JArgument<Boolean> {
    public JArgument_Boolean(String argName) {
        super(argName);
    }

    @Override
    protected Boolean convert(String rawGivenArg) {
        return Boolean.valueOf(rawGivenArg);
    }

    @Override
    public JArgumentValidateResponse validate(String rawGivenArg) {

        if (rawGivenArg.equalsIgnoreCase("True") || rawGivenArg.equalsIgnoreCase("False")){
            return VALID;
        }

        return new JArgumentValidateResponse(true, getNameWithBracketsAndColor() + " &cmust be True or False.");
    }
}
