package me.sen2y.discord;

import me.sen2y.VelocityStaffChat;
import net.dv8tion.jda.api.entities.TextChannel;
import org.spicord.api.addon.SimpleAddon;
import org.spicord.bot.DiscordBot;

public class VelocityAddon extends SimpleAddon {


    private VelocityStaffChat plugin;
    private static VelocityAddon instance;
    private DiscordBot bot;
    public String prefix;

    public VelocityAddon(VelocityStaffChat plugin) {
        super("VelocityStaffChat", "velocitystaffchat", "sen2y");
        instance = this;
        this.plugin = plugin;
    }

    @Override
    public void onReady(DiscordBot bot) {
        this.bot = bot;
        prefix = bot.getCommandPrefix();
        // enable commands
        bot.getJda().addEventListener(new MessageListener(plugin));
    }

    public void sendMessage(String message) {
        TextChannel channel = bot.getJda().getTextChannelById(plugin.getConfig().getChannel());
        channel.sendMessage(message).queue();
    }

}
