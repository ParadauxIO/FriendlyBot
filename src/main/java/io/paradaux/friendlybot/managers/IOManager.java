/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.managers.IOManager :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.configuration.PermissionEntry;
import io.paradaux.friendlybot.core.utils.models.exceptions.ManagerNotReadyException;
import io.paradaux.friendlybot.core.utils.models.exceptions.NoSuchResourceException;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.*;
import java.util.Scanner;

public class IOManager {

    public static IOManager instance = null;
    private final Logger logger;

    public IOManager(Logger logger) {
        this.logger = logger;
        logger.info("Initialising: IO Manager");
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
