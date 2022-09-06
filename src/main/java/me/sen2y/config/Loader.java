package me.sen2y.config;

import org.slf4j.Logger;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.nio.file.Path;

public class Loader {
    private Loader(){}

    public static Configuration loadConfig(Path path, Logger logger) {
        Path configPath = path.resolve("config.conf");
        final HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
                .defaultOptions(opts -> opts
                        .shouldCopyDefaults(true)
                        .header("VelocityStaffChat | by sen2y"))
                .path(configPath)
                .build();

        try {
            final CommentedConfigurationNode node = loader.load();
            Configuration config = node.get(Configuration.class);
            node.set(Configuration.class, config);
            loader.save(node);
            return config;
        } catch (ConfigurateException e) {
            logger.error("Could not load config.conf file, error: {}", e.getMessage());
            return null;
        }
    }
}
