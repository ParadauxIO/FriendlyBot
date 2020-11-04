/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.

 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.

 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).

 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.csbot.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.paradaux.csbot.CSBot;
import io.paradaux.csbot.controllers.LogController;
import org.slf4j.Logger;

import java.io.*;

/**
 * ConfigurationUtils serves as a basis for the FileIO done by the application
 *
 * @author Rían Errity
 * @version Last Modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 * @see CSBot
 * */

public class ConfigurationUtils {

    /**
     * Copies the configuration file from the JAR to the current directory on first run
     */
    public static void deployConfiguration() {
        Logger logger = LogController.getLogger();
        if (!new File("config.json").exists()) {
            try {
                ExportResource("/config.json");
            } catch (Exception exception) {
                logger.error("Failed to deploy configuration.\n", exception);
            }
        }

        if (!new File("verification.log").exists()) {
            try {
                ExportResource("/verification.log");
            } catch (Exception exception) {
                logger.error("Failed to deploy verification log.\n", exception);
            }
        }
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
            stream = ConfigurationUtils.class.getResourceAsStream(resourceName);
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(ConfigurationUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
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

}
