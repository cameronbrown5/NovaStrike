package me.thecamzone.Utils.jcommandbuilder.JArgument;

public class JArgument_Float extends JArgument<Float> {
    public JArgument_Float(String argName) {
        super(argName);
    }

    @Override
    protected Float convert(String rawGivenArg) {
        return Float.valueOf(rawGivenArg);
    }

    @Override
    public JArgumentValidateResponse validate(String rawGivenArg) {
        try {
            Float.parseFloat(rawGivenArg);
            return VALID;
        } catch (final NumberFormatException nfe) {
            return new JArgumentValidateResponse(true, getNameWithBracketsAndColor() + " &cmust be a decimal number.");
        }
    }
}
