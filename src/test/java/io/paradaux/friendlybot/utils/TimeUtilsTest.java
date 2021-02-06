package io.paradaux.friendlybot.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeUtilsTest {

    @Test
    void getTimeTest() {
        System.out.println(TimeUtils.getTime("2w2s"));
    }

    @Test
    void displayTimeTest() {
        System.out.println(TimeUtils.millisecondsToDisplay(TimeUtils.getTime("4w13h")));
    }


}