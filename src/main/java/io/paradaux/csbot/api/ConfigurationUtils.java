package io.paradaux.csbot.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.paradaux.csbot.CSBot;
import org.slf4j.Logger;

import java.io.*;
import java.util.Arrays;

/**
 * ConfigurationUtils serves as a basis for the FileIO done by the application
 *
 * @author Rían Errity
 * @version Last Modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 * @see CSBot
 * */
public class ConfigurationUtils {

    static Logger logger = CSBot.getLogger();

    /**
     * Copies the configuration file from the JAR to the current directory on first run
     */
    public static void deployConfiguration() {
        if (!new File("config.yml").exists()) {
            try {
                ExportResource("config.json");
            } catch (Exception exception) {
                logger.error("Failed to deploy configuration.\n" + Arrays.toString(exception.getStackTrace()));
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

    public static ConfigurationCache readConfigurationFile() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        BufferedReader bufferedReader = new BufferedReader(new FileReader("configuration.json"));
        return gson.fromJson(bufferedReader, ConfigurationCache.class);
    }

}
