package me.sen2y;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.List;

public class Utils {

    public static void sendMessage(Component message, List<Player> toggledPlayers, VelocityStaffChat plugin) {
        for (Player player : toggledPlayers) {
            if (player.hasPermission("staffchat.see")) {
                player.sendMessage(message);
            }
        }
        plugin.getProxy().getConsoleCommandSource().sendMessage(message);
    }

    public static Component mm(String msg) {
        return MiniMessage.miniMessage().deserialize(msg);
    }

    public static Component generalLayout(String layout, String msg, String player, String server) {
        return mm(layout)
                .replaceText(TextReplacementConfig.builder()
                        .match("%username%")
                        .replacement(player)
                        .build())
                .replaceText(TextReplacementConfig.builder()
                        .match("%server%")
                        .replacement(server)
                        .build())
                .replaceText(TextReplacementConfig.builder()
                        .match("%message%")
                        .replacement(msg)
                        .build());
    }

    public static Component minecraftLayout(String layout, String msg, String usertag, String username) {
        return mm(layout)
                .replaceText(TextReplacementConfig.builder()
                        .match("%usertag%")
                        .replacement(usertag)
                        .build())
                .replaceText(TextReplacementConfig.builder()
                        .match("%message%")
                        .replacement(msg)
                        .build())
                .replaceText(TextReplacementConfig.builder()
                        .match("%username%")
                        .replacement(username)
                        .build());
    }

    public static String discordLayout(String layout, String msg, String username, String server) {
        return layout
                .replace("%username%", username)
                .replace("%server%", server)
                .replace("%message%", msg);
    }
}
