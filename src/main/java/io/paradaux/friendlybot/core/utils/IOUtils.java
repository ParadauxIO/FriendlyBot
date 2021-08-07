package io.paradaux.friendlybot.core.utils;

import io.paradaux.friendlybot.core.utils.models.exceptions.NoSuchResourceException;
import io.paradaux.friendlybot.managers.IOManager;

import javax.annotation.Nullable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {

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

}
