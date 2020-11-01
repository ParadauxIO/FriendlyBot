package io.paradaux.csbot.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.paradaux.csbot.CSBot;
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
        Logger logger = Logging.getLogger();
        if (!new File("config.yml").exists()) {
            try {
                ExportResource("/config.json");
            } catch (Exception exception) {
                logger.error("Failed to deploy configuration.\n", exception);
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
