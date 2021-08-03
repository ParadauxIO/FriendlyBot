package io.paradaux.friendlybot.config;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.IOException;
import java.nio.file.Path;

public class FConfigurationLoader {

    private static HoconConfigurationLoader loader;
    private static FConfiguration config;

    public static HoconConfigurationLoader getLoader() {
        return loader;
    }

    public static FConfiguration loadConfig() throws SerializationException {
        loader = HoconConfigurationLoader.builder()
                .path(Path.of("friendlybot.conf"))
                .build();

        CommentedConfigurationNode root;

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        config = root.get(FConfiguration.class);
        config.setRoot(root);
        return config;
    }

    public static void saveConfig() throws ConfigurateException {
        config.save();
        loader.save(config.getRoot());
    }

}
