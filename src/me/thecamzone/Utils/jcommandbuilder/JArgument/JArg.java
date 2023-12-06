package me.thecamzone.Utils.jcommandbuilder.JArgument;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(JArg.JArgs.class)
public @interface JArg {
    String name();

    Class<? extends JArgument<?>> type();

    String defaultValue() default "Not Set";

    String tabProvider() default "Not Set";

    String tabProviderCase() default "LOWER";

    @Retention(RetentionPolicy.RUNTIME)
    @interface JArgs {
        JArg[] value();
    }
}
