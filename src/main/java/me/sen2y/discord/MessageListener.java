package me.sen2y.discord;

import me.sen2y.Utils;
import me.sen2y.VelocityStaffChat;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.Component;

public class MessageListener extends ListenerAdapter {

    private VelocityStaffChat plugin;

    public MessageListener(VelocityStaffChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getChannelType().equals(ChannelType.TEXT)) {
            return;
        }
        if (event.getChannel().getId().equals(plugin.getConfig().getChannel())) {
            User user = event.getAuthor();
            Message msg = event.getMessage();
            if (user.isBot()) {
                return;
            }
            Component message = Utils.minecraftLayout(plugin.getConfig().getMinecraftLayout(),
                    msg.getContentStripped(), user.getAsTag(), user.getName());
            Utils.sendMessage(message, plugin.toggledPlayers, plugin);
        }


    }

}
