package me.anton.sickcore.modules.discord.modules.ticket;

import lombok.Getter;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.modules.IDiscordModule;

@Getter
public class TicketModule implements IDiscordModule {

    @Getter
    private static TicketModule instance;
    private DatabaseModel model;
    private DiscordModule module;

    @Override
    public void load() {
        instance = this;
        this.module = DiscordModule.getInstance();
        this.model = new DatabaseModel("ticket");
        register();
    }

    @Override
    public void unload() {

    }

    @Override
    public void register() {
        //new TicketMessage();
        module.getJda().addEventListener(new TicketListener());
    }

    @Override
    public DiscordModule getModule() {
        return IDiscordModule.super.getModule();
    }

    public String getTicketCategoryID(){
        return (String) getModule().readFromConfig("ticketCategory");
    }

    public String getTicketArchiveCategoryID(){
        return (String) getModule().readFromConfig("ticketArchiveCategory");
    }

    public String getTicketChannel(){
        return (String) getModule().readFromConfig("ticketChannel");
    }
}
