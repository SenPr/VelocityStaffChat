package me.sen2y.listeners;

import com.velocitypowered.api.event.Continuation;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import me.sen2y.Utils;
import net.kyori.adventure.text.Component;
import me.sen2y.VelocityStaffChat;

public class ProxyChatEvents {
    private final VelocityStaffChat plugin;

    public ProxyChatEvents(final VelocityStaffChat plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void onChat(final PlayerChatEvent event) {
        if (!event.getPlayer().hasPermission("staffchat.use")) {
            plugin.getLogger().info("debug: event stopped because of permission");
            return;
        }
        if (plugin.toggledPlayers.contains(event.getPlayer())) {
            plugin.getLogger().info("debug: player is in toggledPlayers");
            // cancel event
            event.setResult(PlayerChatEvent.ChatResult.denied());
            plugin.getLogger().info("debug: cancelled chat event");
            String player = event.getPlayer().getUsername();
            String server;
            if (event.getPlayer().getCurrentServer().isEmpty()) {
                server = "Unknown";
            } else {
                server = event.getPlayer().getCurrentServer().get().getServerInfo().getName();
            }
            // add layout
            Component message = Utils.generalLayout(plugin.getConfig().getGeneralLayout(), event.getMessage(), player, server);
            Utils.sendMessage(message, plugin.toggledPlayers, plugin);
            plugin.getLogger().info("sent the staff message");
            // to do: add discord integration
        }
    }

    @Subscribe
    public void onPlayerQuit(DisconnectEvent event) {
        plugin.toggledPlayers.remove(event.getPlayer());
    }

}
