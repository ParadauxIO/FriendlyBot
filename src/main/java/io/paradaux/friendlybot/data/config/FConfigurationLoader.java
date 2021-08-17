package io.paradaux.friendlybot.data.config;

import io.paradaux.friendlybot.core.utils.IOUtils;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class FConfigurationLoader {

    private static HoconConfigurationLoader loader;
    private static FConfiguration config;
    private static CommentedConfigurationNode root;

    public static HoconConfigurationLoader getLoader() {
        return loader;
    }

    public static FConfiguration loadConfig() throws SerializationException {
        deployConfig();

        loader = HoconConfigurationLoader.builder()
                .path(Path.of("friendlybot.conf"))
                .build();

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        config = root.get(FConfiguration.class);

        if (config == null) {
            throw new IllegalStateException("Configuration is null");
        }

        return config;
    }

    public static void saveConfig() throws ConfigurateException {
        root.set(FConfiguration.class, config);
        loader.save(root);
    }

    private static void deployConfig() {
        if (!new File("friendlybot.conf").exists()) {
            IOUtils.exportResource("/friendlybot.conf",System.getProperty("user.dir") + "/friendlybot.conf");
        }
    }

}
