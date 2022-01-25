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
        register();
    }

    @Override
    public void unload() {

    }

    @Override
    public void register() {
        this.model = new DatabaseModel("ticket");
        //new TicketMessage();
        module.getJda().addEventListener(new TicketListener());
    }

    @Override
    public DiscordModule getModule() {
        return IDiscordModule.super.getModule();
    }

    public String getTicketCategoryID(){
        return getModule().getDocString("ticketCategory");
    }

    public String getTicketArchiveCategoryID(){
        return getModule().getDocString("ticketArchiveCategory");
    }

    public String getTicketChannel(){
        return getModule().getDocString("ticketChannel");
    }
}
