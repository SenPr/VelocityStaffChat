package me.sen2y.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class Configuration {
    private Configuration(){}

    @Comment("Staff channel id you want to relay messages to")
    private String channel = "123456";

    @Comment("General layout for staff chat in-game")
    @Setting(value = "general-layout")
    private String generalLayout = "<dark_red>StaffChat <red>%username% (%server%): <green>%message%";

    @Comment("Discord -> MC layout" +
            "Use %usertag% for the Discord tag" +
            "Use %username% for the Discord name")
    @Setting(value = "minecraft-layout")
    private String minecraftLayout = "<blue>Discord <dark_red>StaffChat <gray>%usertag%: <green>%message%";

    @Comment("MC -> Discord layout")
    @Setting(value = "discord-layout")
    private String discordLayout = "**StaffChat** %username% (%server%): %message%";

    @Comment("Toggle staff chat message")
    @Setting(value = "msg-toggle-on")
    private String messageToggleOn = "<yellow>Toggled StaffChat on";

    @Comment("Toggle off staff chat message")
    @Setting(value = "msg-toggle-off")
    private String messageToggleOff = "<red>Toggled StaffChat off";

    public String getChannel() {
        return this.channel;
    }

    public String getGeneralLayout() {
        return this.generalLayout;
    }

    public String getMinecraftLayout() {
        return this.minecraftLayout;
    }

    public String getDiscordLayout() {
        return this.discordLayout;
    }

    public String getMessageToggleOn() {
        return this.messageToggleOn;
    }

    public String getMessageToggleOff() {
        return this.messageToggleOff;
    }
}
