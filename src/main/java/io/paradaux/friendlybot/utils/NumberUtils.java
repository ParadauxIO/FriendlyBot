/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.NumberUtils :  31/01/2021, 01:26
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

    /**
     * Turns Degrees Kelvin to Degrees Celsius
     * */
    public static double kelvinToCelsius(double degKelvin) {
        return degKelvin - 273.15d;
    }

    /**
     * Turns Degrees Kelvin to Degrees Fahrenheit.
     * You're a filthy little American aren't you?
     * */
    public static double kelvinToFahrenheit(double degKelvin) {
        return ((degKelvin - 273.15d) * 9/5) + 32;
    }

}
