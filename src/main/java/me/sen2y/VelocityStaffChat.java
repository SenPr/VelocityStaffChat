package me.sen2y;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginManager;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import me.sen2y.commands.StaffChatCommand;
import me.sen2y.config.Configuration;
import me.sen2y.config.Loader;
import me.sen2y.discord.VelocityAddon;
import net.byteflux.libby.Library;
import net.byteflux.libby.VelocityLibraryManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.slf4j.Logger;
import org.spicord.SpicordLoader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Plugin(
        id = "velocitystaffchat",
        name = "VelocityStaffChat",
        version = "1.0",
        description = "Provides staff chat proxy-wide with Spicord integration",
        authors = {"sen2y"},
        dependencies = {
                @Dependency(id = "spicord", optional = false)
        }
)
public class VelocityStaffChat {

    private final Path path;
    private final CommandManager commandManager;
    private final EventManager eventManager;
    private final Logger logger;
    private final PluginManager manager;
    private Configuration config;

    public VelocityAddon addon;

    public List<Player> toggledPlayers;

    @Inject
    public VelocityStaffChat(
            @DataDirectory Path path,
            CommandManager commandManager,
            EventManager eventManager,
            Logger logger,
            PluginManager pmanager
    ) {
        this.path = path;
        this.commandManager = commandManager;
        this.eventManager = eventManager;
        this.logger = logger;
        this.manager = pmanager;
    }

    @Inject
    private ProxyServer serverInstance;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // load dependencies
        this.loadDependencies();

        if (!this.reloadConfig()) {
            return;
        }

        addon = new VelocityAddon(this);
        SpicordLoader.addStartupListener(spicord -> {
            spicord.getAddonManager().registerAddon(addon);
        });

        toggledPlayers = new ArrayList<>();

        final CommandMeta meta = commandManager.metaBuilder("staffchat")
                .aliases("sc")
                .plugin(this)
                .build();

        commandManager.register(meta, new StaffChatCommand(this));
        // Can't be used due to Velocity chat API
        // eventManager.register(this, new ProxyChatEvents(this));
        serverInstance.getConsoleCommandSource().sendMessage(
                MiniMessage.miniMessage().deserialize(
                        "<dark_purple>VelocityStaffChat by sen2y"
                )
        );
    }

    private void loadDependencies() {
        final VelocityLibraryManager<VelocityStaffChat> libraryManager = new VelocityLibraryManager<>(logger, this.path, manager, this, "libs");

        final Library hocon = Library.builder()
                .groupId("org{}spongepowered")
                .artifactId("configurate-hocon")
                .version("4.1.2")
                .id("configurate-hocon")
                .build();
        final Library confCore = Library.builder()
                .groupId("org{}spongepowered")
                .artifactId("configurate-core")
                .version("4.1.2")
                .id("configurate-core")
                .build();
        final Library geantyref = Library.builder()
                .groupId("io{}leangen{}geantyref")
                .artifactId("geantyref")
                .version("1.3.13")
                .id("geantyref")
                .build();

        libraryManager.addMavenCentral();
        libraryManager.loadLibrary(hocon);
        libraryManager.loadLibrary(confCore);
        libraryManager.loadLibrary(geantyref);
    }

    public boolean reloadConfig() {
        Configuration config = Loader.loadConfig(path, logger);
        if (config != null) {
            this.config = config;
        }
        return !(config == null);
    }

    public Configuration getConfig() {
        return this.config;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public ProxyServer getProxy() {
        return this.serverInstance;
    }

    public VelocityAddon getAddon() {
        return this.addon;
    }

}
