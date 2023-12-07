package me.thecamzone.Utils.jcommandbuilder.JArgument;

public class JArgument_StringArray extends JArgument<String[]> {
    public JArgument_StringArray(String argName) {
        super(argName);

        setReadLastArg();
    }

    @Override
    protected String[] convert(String rawGivenArg) {
        return new String[]{rawGivenArg};
    }

    @Override
    public JArgumentValidateResponse validate(String rawGivenArg) {
        return VALID;
    }
}
