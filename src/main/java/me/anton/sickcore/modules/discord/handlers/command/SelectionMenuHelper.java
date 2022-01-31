package me.anton.sickcore.modules.discord.handlers.command;

import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class SelectionMenuHelper {

    private static final List<SelectionMenuHelper> actives = new ArrayList<>();

    private final String identifier;
    private final SelectionMenu selectionMenu;
    private final Consumer<List<SelectOption>> consumer;

    public SelectionMenuHelper(SelectionMenu selectionMenu, Consumer<List<SelectOption>> consumer) {
        this.selectionMenu = selectionMenu;
        this.identifier = selectionMenu.getId();
        this.consumer = consumer;
        actives.add(this);
    }

    public void onResult(SelectionMenuEvent event){
        consumer.accept(event.getSelectedOptions());
        destroy();
    }

    public void destroy(){
        actives.removeIf(s -> s.equals(this));
    }

    public static SelectionMenuHelper get(SelectionMenu menu){
        for (SelectionMenuHelper helper : actives) {
            if(helper.getIdentifier().equalsIgnoreCase(menu.getId())) return helper;
        }
        return null;
    }

}
