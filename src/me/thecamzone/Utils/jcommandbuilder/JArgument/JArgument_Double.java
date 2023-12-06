package me.thecamzone.Utils.jcommandbuilder.JArgument;

public class JArgument_Double extends JArgument<Double> {
    public JArgument_Double(String argName) {
        super(argName);
    }

    @Override
    protected Double convert(String rawGivenArg) {
        return Double.valueOf(rawGivenArg);
    }

    @Override
    public JArgumentValidateResponse validate(String rawGivenArg) {
        try {
            Double.parseDouble(rawGivenArg);
            return VALID;
        } catch (final NumberFormatException nfe) {
            return new JArgumentValidateResponse(true, getNameWithBracketsAndColor() + " &cmust be a decimal number.");
        }
    }
}
