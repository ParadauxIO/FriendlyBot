/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.csbot.api;

import net.dv8tion.jda.api.entities.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * EmailUtils provides various utilities pertaining to the email functionality of the discord-bot used for verifying @tcd.ie emails
 * As of 2/11/2020 This is based on a static hashmap. This will be replaced with a MongoDB Collection on full release.
 *
 * @author Rían Errity
 * @version Last Modified for 0.1.0-SNAPSHOT
 * @since 2/11/2020 DD/MM/YY
 * @see io.paradaux.csbot.CSBot
 * */

public class VerificationSystem {

    /**
     * This map represents the list of currently pending verification requests.
     * */
    public static HashMap<String, String> pendingVerification = new HashMap<>();
    public static HashMap<String, String> verifiedUsers = new HashMap<>();

    public static void addPendingUser(String userID, String verificationCode) {
        pendingVerification.put(userID, verificationCode);
    }

    public static boolean isPending(String userID) {
        return pendingVerification.containsKey(userID);
    }

    public static void removePendingUser(String userID) {
        pendingVerification.remove(userID);
    }

    public static String getVerificationCode(String userID) {
        return pendingVerification.get(userID);
    }

    public static void setVerified(User user) {
        try {
            File file = new File("verification.log");
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            br.write("\nDiscord User " + user.getAsTag() + " has verified their tcd email. @ " + new Date().toString());
            br.close();
            fr.close();
        } catch (IOException exception) {
            Logging.getLogger().error("Error writing to the verification logs. ", exception);
        }


    }


}
