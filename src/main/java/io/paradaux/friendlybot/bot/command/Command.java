package io.paradaux.friendlybot.bot.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String name() default "";
    String description() default "";
    String permission() default  "";
    String[] aliases() default {};

}
