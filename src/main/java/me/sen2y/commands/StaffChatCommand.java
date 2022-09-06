package me.sen2y.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.Player;
import me.sen2y.Utils;
import me.sen2y.VelocityStaffChat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class StaffChatCommand implements SimpleCommand {

    private final VelocityStaffChat plugin;

    public StaffChatCommand(VelocityStaffChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        if (!invocation.source().hasPermission("staffchat.use")) {
            invocation.source().sendMessage(
                    MiniMessage.miniMessage().deserialize("<red>You don't have permission")
            );
            return;
        }
        final String[] args = invocation.arguments();
        if (args.length < 1 || args[0].equalsIgnoreCase("help")) {
            invocation.source().sendMessage(
                    MiniMessage.miniMessage().deserialize("<red>Usage: /staffchat <message>")
            );
            return;
        }
        if (args[0].equalsIgnoreCase("toggle")) {
            if (invocation.source() instanceof ConsoleCommandSource) {
                invocation.source().sendMessage(
                        MiniMessage.miniMessage().deserialize("<red>Console cannot toggle staffchat.")
                );
                return;
            }
            if (plugin.toggledPlayers.contains((Player) invocation.source())) {
                plugin.toggledPlayers.remove((Player) invocation.source());
                invocation.source().sendMessage(
                        MiniMessage.miniMessage().deserialize(
                                plugin.getConfig().getMessageToggleOff()
                        )
                );
            } else {
                plugin.toggledPlayers.add((Player) invocation.source());
                invocation.source().sendMessage(
                        MiniMessage.miniMessage().deserialize(
                                plugin.getConfig().getMessageToggleOn()
                        )
                );
            }
        } else {
            String player;
            String server;
            if (invocation.source() instanceof Player) {
                Player p = (Player) invocation.source();
                player = p.getUsername();
                if (p.getCurrentServer().isEmpty()) {
                    server = "Unknown";
                } else {
                    server = p.getCurrentServer().get().getServerInfo().getName();
                }
            } else {
                player = "Console";
                server = "Proxy";
            }
            String msg = String.join(" ", args);
            Component message = Utils.generalLayout(plugin.getConfig().getGeneralLayout(), msg, player, server);
            String discordMessage = Utils.discordLayout(plugin.getConfig().getDiscordLayout(), msg, player, server);
            plugin.getAddon().sendMessage(discordMessage);
            Utils.sendMessage(message, plugin.toggledPlayers, plugin);
        }
    }

}
