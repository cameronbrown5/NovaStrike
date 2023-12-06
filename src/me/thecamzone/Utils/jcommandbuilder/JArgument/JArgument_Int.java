package me.thecamzone.Utils.jcommandbuilder.JArgument;

public class JArgument_Int extends JArgument<Integer> {
    public JArgument_Int(String argName) {
        super(argName);
    }

    @Override
    protected Integer convert(String rawGivenArg) {
        return Integer.parseInt(rawGivenArg);
    }

    @Override
    public JArgumentValidateResponse validate(String rawGivenArg) {
        try {
            Integer.parseInt(rawGivenArg);
            return VALID;
        } catch (final NumberFormatException nfe) {
            return new JArgumentValidateResponse(true, getNameWithBracketsAndColor() + " &cmust be a number.");
        }
    }

}
