/*
 * Copyright (c) 2021 |  Rían Errity. GPLv3
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.friendlybot.utils.models.enums;

import java.util.HashMap;
import java.util.Map;

public class WebSettings {

    // Web headers
    private static final Map<String, String> jsonWebHeaders = new HashMap<>();
    private static final Map<String, String> textWebHeaders = new HashMap<>();
    private static final Map<String, String> rawWebHeaders = new HashMap<>();
    public static final String WEB_USER_AGENT = "Paradaux/friendlybot";
    public static final int WEB_TIMEOUT = 20000;

    static {
        jsonWebHeaders.put("Accept", "application/json");
        jsonWebHeaders.put("Accept-Language", "en-US,en;q=0.8");

        textWebHeaders.put("Accept", "text/plain");
        textWebHeaders.put("Accept-Language", "en-US,en;q=0.8");

        rawWebHeaders.put("Accept-Language", "en-US,en;q=0.8");
    }

    public static Map<String, String> getJsonWebHeaders() {
        return jsonWebHeaders;
    }

    public static Map<String, String> getTextWebHeaders() {
        return textWebHeaders;
    }

    public static Map<String, String> getRawWebHeaders() {
        return rawWebHeaders;
    }
}
