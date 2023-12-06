package me.thecamzone.Utils.jcommandbuilder.JArgument;

import jline.internal.Nullable;
import me.thecamzone.Utils.jcommandbuilder.JTabProvider;
import me.thecamzone.Utils.jcommandbuilder.JTabProviderCase;

import java.util.ArrayList;

public abstract class JArgument<T> {

    protected static final JArgumentValidateResponse VALID = new JArgumentValidateResponse(false, "");
    private final String argName;
    private JTabProvider tabProvider = ArrayList::new;
    private T userSetValue;
    private T defaultValue;
    private JTabProviderCase jTabProviderCase;

    public JArgument(String argName) {
        this.argName = argName;
    }

    /**
     * Sets the default value of the arg that will be used is not value is set.
     *
     * @param rawGivenArg The raw string that was given for the arg in the command.
     * @return this as a builder object.
     */
    public JArgument<T> setDefault(String rawGivenArg) {
        defaultValue = convert(rawGivenArg);
        return this;
    }

    /**
     * Checks that the given string can be converted in to type T
     * If not the reason why will be sent to the command sender.
     *
     * @param rawGivenArg The raw string that was given for the arg in the command.
     * @return JArgumentValidateResponse
     */
    public abstract JArgumentValidateResponse validate(String rawGivenArg);

    /**
     * Will convert the String in to an object of type T
     *
     * @param rawGivenArg The raw string that was given for the arg in the command.
     * @return The converted object to be of type T
     */
    protected abstract T convert(String rawGivenArg);

    public void reset() {
        userSetValue = null;
    }

    public boolean hasDefaultValue() {
        return defaultValue != null;
    }

    public T getValue() {
        if (userSetValue == null)
            return defaultValue;


        return userSetValue;
    }

    /**
     * Sets the value of the arg with the correct type T
     *
     * @param rawGivenArg The raw string that was given for the arg in the command.
     * @return this as a builder object.
     */
    public JArgument<T> setValue(String rawGivenArg) {
        userSetValue = convert(rawGivenArg);
        return this;
    }

    public void setTabProviderCase (JTabProviderCase jTabProviderCase) {
        this.jTabProviderCase = jTabProviderCase;
    }

    public JTabProviderCase getTabProviderCase() {
        return jTabProviderCase;
    }

    public String getNameWithBracketsAndColor() {
        return (hasDefaultValue() ? "&8[" : "&7<") + argName + (hasDefaultValue() ? "]" : ">");
    }
    public String getNameWithBrackets() {
        return (hasDefaultValue() ? "[" : "<") + argName + (hasDefaultValue() ? "]" : ">");
    }

    @Nullable
    public JTabProvider getTabProvider() {
        return tabProvider;
    }

    //----------------------------------------------------
    // [Default] -> Getters and Setters
    //----------------------------------------------------

    public JArgument<T> setTabProvider(JTabProvider tabProvider) {
        this.tabProvider = tabProvider;
        return this;
    }

    public String getArgName() {
        return argName;
    }

    public record JArgumentValidateResponse(boolean wasInvalid, String reason) {
    }

}
