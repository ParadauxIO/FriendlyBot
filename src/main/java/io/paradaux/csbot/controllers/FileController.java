/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
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

package io.paradaux.csbot.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.paradaux.csbot.IController;
import io.paradaux.csbot.ConfigurationCache;
import io.paradaux.csbot.models.ChatFilterEntry;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.*;
import java.util.Scanner;

public class FileController implements IController {

    // Singleton Instance
    public static FileController INSTANCE;

    // Dependencies
    private static final ConfigurationCache configurationCache = ConfigurationController.getConfigurationCache();
    private static final Logger logger = LogController.getLogger();

    @Override
    public void initialise() {
        INSTANCE = this;
    }

    /**
     * Export a resource embedded into a Jar file to the local file path.
     *
     * @param resourceName ie.: "/configuration.yml" N.B / is a directory down in the "jar tree" been the jar the root of the tree
     * @return The path to the exported resource
     * @throws Exception a generic exception to signal something went wrong
     */
    public static String ExportResource(String resourceName) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;
        try {
            stream = FileController.class.getResourceAsStream(resourceName);
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(FileController.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            resStreamOut = new FileOutputStream(jarFolder + resourceName);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            stream.close();
            resStreamOut.close();
        }

        return jarFolder + resourceName;
    }

    /**
     * Reads the configuration file and maps it to the ConfigurationCache object
     * @return An Instance of ConfigurationCache
     * @throws FileNotFoundException When the configuration file does not exist.
     * @see ConfigurationCache
     * */
    public static ConfigurationCache readConfigurationFile() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        BufferedReader bufferedReader = new BufferedReader(new FileReader("config.json"));
        return gson.fromJson(bufferedReader, ConfigurationCache.class);
    }

    /**
     * Copies the configuration file from the JAR to the current directory on first run
     */
    public static void deployFiles() {
        Logger logger = LogController.getLogger();
        if (!new File("config.json").exists()) {
            try {
                ExportResource("/config.json");
            } catch (Exception exception) {
                logger.error("Failed to deploy config.json\n", exception);
            }
        }

        if (!new File("chat-filter.json").exists()) {
            try {
                ExportResource("/chat-filter.json");
            } catch (Exception exception) {
                logger.error("Failed to deploy chat-filter.json\n", exception);
            }
        }

        if (!new File("reaction-roles.json").exists()) {
            try {
                ExportResource("/reaction-roles.json");
            } catch (Exception exception) {
                logger.error("Failed to deploy reaction-roles.json\n", exception);
            }
        }
    }

    @Nullable
    public String readEmailTemplate() {
        StringBuilder emailTemplate = new StringBuilder();

        InputStream emailTemplateStream = getClass().getClassLoader().getResourceAsStream("emailtemplate.html");
        if (emailTemplateStream == null) {
            return null;
        }

        Scanner scanner = new Scanner(emailTemplateStream);

        while (scanner.hasNext()) {
            emailTemplate.append(scanner.next()).append("\n");
        }

        scanner.close();
        return emailTemplate.toString();
    }

    public ChatFilterEntry readChatFilter() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        BufferedReader bufferedReader = new BufferedReader(new FileReader("chat-filter.json"));
        return gson.fromJson(bufferedReader, ChatFilterEntry.class);
    }

}
