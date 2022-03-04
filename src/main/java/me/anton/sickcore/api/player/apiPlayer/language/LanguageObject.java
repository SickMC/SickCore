package me.anton.sickcore.api.player.apiPlayer.language;

import eu.thesimplecloud.api.CloudAPI;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.utils.common.Replacable;
import me.anton.sickcore.core.Core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LanguageObject {

    private final DatabaseModel model;
    private final String path;
    private String rawString;
    private String rawStringDe;
    private final APIPlayer player;
    private String message = null;
    private String messageDe = null;
    private List<LanguageObject> appends;

    public LanguageObject(APIPlayer iapiPlayer, LanguagePath path){
        this.model = Core.getInstance().getLanguageModel();
        this.path = path.getPath();
        this.player = iapiPlayer;
        this.rawString = (String) model.get(path.getPath(), Finder.stringFinder("language", "englishUK"));
        this.rawStringDe = (String) model.get(path.getPath(), Finder.stringFinder("language", "deutschDE"));
        this.message = rawString;
        this.messageDe = rawStringDe;
        this.appends = new ArrayList<>();
    }

    public LanguageObject replace(Replacable... replacement){
        Arrays.stream(replacement).forEach(replacable -> {
            messageDe = messageDe.replace(replacable.getKey(), replacable.getReplacement());
            message = message.replace(replacable.getKey(), replacable.getReplacement());
        });

        return this;
    }

    public LanguageObject append(LanguageObject... object){
        appends.addAll(Arrays.asList(object));
        return this;
    }

    public String build(){
        StringBuilder de = new StringBuilder(messageDe.replace("%n", "\n").replace("%server%", CloudAPI.getInstance().getThisSidesName()));
        StringBuilder en = new StringBuilder(message.replace("%n", "\n").replace("%server%", CloudAPI.getInstance().getThisSidesName()));

        if (appends.isEmpty()) {
            if (player.getLanguage() == Language.DEUTSCHDE) return de.toString();
            if (player.getLanguage() == Language.ENGLISCHUK) return en.toString();
        }
        if (!appends.isEmpty()){
            for (LanguageObject append : appends) {
                de = de.append(append.build());
                en = en.append(append.build());
            }
            if (player.getLanguage() == Language.DEUTSCHDE) return de.toString();
            if (player.getLanguage() == Language.ENGLISCHUK) return en.toString();
        }
        return null;
    }

}
