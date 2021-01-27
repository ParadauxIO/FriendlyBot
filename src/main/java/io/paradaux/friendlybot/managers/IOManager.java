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

package io.paradaux.friendlybot.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import io.paradaux.friendlybot.utils.models.exceptions.NoSuchResourceException;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.configuration.PermissionEntry;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.*;
import java.util.Scanner;

public class IOManager {

    public static IOManager instance = null;
    private final Logger logger;

    public IOManager(Logger logger) {
        this.logger = logger;
        logger.info("Initialising: FileController");
        instance = this;
    }

    public static IOManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

    /**
     * Export a resource embedded into a Jar file to the local file path.
     *
     * @param inputPath ie.: "/configuration.yml" N.B / is a directory down in the "jar tree" been the jar the root of the tree
     * @throws NoSuchResourceException a generic exception to signal something went wrong
     */
    public static void exportResource(String inputPath, @Nullable String outputPath) throws NoSuchResourceException {

        OutputStream resourceOutputStream;

        try (InputStream resourceStream = IOManager.class.getResourceAsStream(inputPath)) {
            resourceOutputStream = new FileOutputStream(outputPath);

            if (resourceStream == null) {
                throw new FileNotFoundException("Cannot get resource \"" + inputPath + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];

            while ((readBytes = resourceStream.read(buffer)) > 0) {
                resourceOutputStream.write(buffer, 0, readBytes);
            }

            resourceOutputStream.close();
        } catch (IOException exception) {
            throw new NoSuchResourceException("Failed to deploy resource : " + exception.getMessage());
        }
    }

    /**
     * Reads the configuration file and maps it to the ConfigurationEntry object.
     * @return An Instance of ConfigurationEntry
     * @throws FileNotFoundException When the configuration file does not exist.
     * @see ConfigurationEntry
     * */
    public ConfigurationEntry readConfigurationFile() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        BufferedReader bufferedReader = new BufferedReader(new FileReader("config.json"));
        return gson.fromJson(bufferedReader, ConfigurationEntry.class);
    }

    /**
     * Reads the permission file and maps it to the PermissionEntry object.
     * @return An Instance of PermissionEntry
     * @throws FileNotFoundException When the configuration file does not exist.
     * @see PermissionEntry
     * */
    public PermissionEntry readPermissionFile() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        BufferedReader bufferedReader = new BufferedReader(new FileReader("permissions.json"));
        return gson.fromJson(bufferedReader, PermissionEntry.class);
    }


    /**
     * Copies the configuration file from the JAR to the current directory on first run.
     */
    public static void deployFiles(Logger logger) {
        String jarLocation = System.getProperty("user.dir");

        if (!new File("config.json").exists()) {
            try {
                exportResource("/config.json", jarLocation + "/config.json");
            } catch (Exception exception) {
                logger.error("Failed to deploy config.json\n", exception);
            }
        }

        if (!new File("permissions.json").exists()) {
            try {
                exportResource("/permissions.json", jarLocation + "/permissions.json");
            } catch (Exception exception) {
                logger.error("Failed to deploy permissions.json\n", exception);
            }
        }
    }

    /**
     * Reads the email template.
     * */
    @Nullable
    public String readEmailTemplate() {
        StringBuilder emailTemplate = new StringBuilder();

        InputStream emailTemplateStream = getClass().getClassLoader()
                .getResourceAsStream("emailtemplate.html");
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

    public void saveJson(File file, Object src) throws IOException {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        String prettyJsonString = gson.toJson(src);

        FileWriter fw = new FileWriter(file, false);
        fw.write(prettyJsonString);
        fw.close();
    }

    /**
     * Updates the permission file.
     * */
    public void updatePermissionFile(PermissionEntry entry) throws IOException {
        File permissionsFile = new File("permissions.json");
        saveJson(permissionsFile, entry);
    }

}
