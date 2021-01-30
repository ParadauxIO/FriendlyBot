package io.paradaux.friendlybot.utils;

import javax.annotation.CheckReturnValue;

public class NumberUtils {

    /**
     * Picks a random color (short) between 0x000000 and 0xFFFFFF
     * @return A random value between 0x000000 and 0xFFFFFF
     * */
    @CheckReturnValue
    public static short randomColor() {
        return (short) Math.round(0xffffff * Math.random());
    }

}
