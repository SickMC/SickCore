package me.anton.sickcore.modules.discord.handlers.messages;

import me.anton.sickcore.api.player.discordPlayer.DiscordPlayer;
import me.anton.sickcore.api.player.discordPlayer.IDiscordPlayer;
import me.anton.sickcore.modules.discord.DiscordModule;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.Instant;

public class EmbedBuilder {

    private net.dv8tion.jda.api.EmbedBuilder builder;
    private EmbedType type;
    private IDiscordPlayer player = null;
    private Member member = null;
    private Color color = null;
    private boolean timeStamp = true;

    public EmbedBuilder(IDiscordPlayer player){
        this.type = EmbedType.UTILITY;
        this.builder = new net.dv8tion.jda.api.EmbedBuilder();
        this.player = player;
        this.member = player.member();
    }

    public EmbedBuilder(Member member){
        this.player = new DiscordPlayer(member);
        this.member = member;
        this.builder = new net.dv8tion.jda.api.EmbedBuilder();
        this.type = EmbedType.UTILITY;
    }

    public EmbedBuilder(){
        this.type = EmbedType.LOG;
        this.builder = new net.dv8tion.jda.api.EmbedBuilder();
    }

    public EmbedBuilder setContent(String content){
        builder.setDescription(content);
        return this;
    }

    public EmbedBuilder setTitle(String title){
        builder.setTitle("**" + title + " | SickMC**");
        return this;
    }

    public EmbedBuilder setColor(Color color){
        this.color = color;
        return this;
    }

    public EmbedBuilder addField(MessageEmbed.Field field){
        builder.addField(field);
        return this;
    }

    public EmbedBuilder addField(String name, String value, boolean inline){
        builder.addField(name, value, inline);
        return this;
    }

    public EmbedBuilder withoutTimeStamp(){
        this.timeStamp = false;
        return this;
    }

    public net.dv8tion.jda.api.EmbedBuilder getBuilder(){
        return builder;
    }

    public MessageEmbed build(){
        switch (type){
            case UTILITY -> {
                builder.setFooter("SickMC • Requested by " + member.getUser().getName(), member.getUser().getEffectiveAvatarUrl());
            }
            case LOG -> {
                builder.setFooter("SickMC • Requested by " + DiscordModule.getInstance().getJda().getSelfUser().getName(), DiscordModule.getInstance().getJda().getSelfUser().getEffectiveAvatarUrl());
                if (timeStamp)builder.setTimestamp(Instant.now());
            }
        }
        if (!(color == null))builder.setColor(color);
        else builder.setColor(Color.ORANGE);
        return builder.build();
    }

}
