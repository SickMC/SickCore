package me.anton.sickcore.games.monopoly.street;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@AllArgsConstructor
@Getter
public enum MonopolyStreet {

    LOS(Material.ARROW, "Los"),
    BADSTRASSE(Material.BROWN_CONCRETE, "Badstraße"),
    GEMEINSCHAFTSFELD_1(Material.CYAN_CONCRETE, "Gemeinschaftsfeld 1"),
    TURMSTRASSE(Material.BROWN_CONCRETE, "Turmstraße"),
    EINKOMMENSTEUER(Material.GOLD_BLOCK, "Einkommensteuer"),
    SÜDBAHNHOF(Material.BLACK_CONCRETE, "Südbahnhof"),
    CHAUSEESTRASSE(Material.LIGHT_BLUE_CONCRETE, "Chauseestraße"),
    EREIGNISFELD_1(Material.CYAN_CONCRETE, "Ereignisfeld 1"),
    ELISENSTRASSE(Material.LIGHT_BLUE_CONCRETE, "Elisenstraße"),
    POSTSTASSE(Material.LIGHT_BLUE_CONCRETE, "Poststraße"),
    GEFÄNGNIS(Material.IRON_BARS, "Gefängnis"),
    SEESTRASSE(Material.PINK_CONCRETE, "Seestraße"),
    ELEKTRIZITÄTSWERK(Material.GLOWSTONE_DUST, "Elektrizitätswerk"),
    HAFENSTRASSE(Material.PINK_CONCRETE, "Hafenstraße"),
    NEUE_STRASSE(Material.PINK_CONCRETE, "Neue Straße"),
    WESTBAHNHOF(Material.BLACK_CONCRETE, "Westbahnhof"),
    MÜNCHNER_STRASSE(Material.ORANGE_CONCRETE, "Münchner Straße"),
    GEMEINSCHAFTSFELD_2(Material.CYAN_CONCRETE, "Gemeinschaftsfeld 2"),
    WIENER_STRASSE(Material.ORANGE_CONCRETE, "Wiener Straße"),
    BERLINER_STRASSE(Material.ORANGE_CONCRETE, "Berliner Straße"),
    FREI_PARKEN(Material.CHEST, "Frei Parken"),
    THEATERSTRASSE(Material.RED_CONCRETE, "Theaterstraße"),
    EREIGNISFELD_2(Material.CYAN_CONCRETE, "Ereignisfeld 2"),
    MUSEUMSTRASSE(Material.RED_CONCRETE, "Museumstraße"),
    OPERNPLATZ(Material.RED_CONCRETE, "Opernplatz"),
    NORDBAHNHOF(Material.BLACK_CONCRETE, "Nordbahnhof"),
    LESSINGSTRASSE(Material.YELLOW_CONCRETE, "Lessingstraße"),
    SCHILLERSTRASSE(Material.YELLOW_CONCRETE, "Schillerstraße"),
    WASSERWERK(Material.WATER_BUCKET, "Wasserwerk"),
    GOETHESTRASSE(Material.YELLOW_CONCRETE, "Goethestraße"),
    AB_INS_GEFÄNGNIS(Material.END_PORTAL_FRAME, "Ab ins Gefängnis"),
    RATHAUSPLATZ(Material.GREEN_CONCRETE, "Rathausplatz"),
    HAUPTSTRASSE(Material.GREEN_CONCRETE, "Hauptstraße"),
    GEMEINSCHAFTSFELD_3(Material.CYAN_CONCRETE, "Gemeinschaftsfeld 3"),
    BAHNHOFSTRASSE(Material.GREEN_CONCRETE, "Bahnhofstraße"),
    HAUPTBAHNHOF(Material.BLACK_CONCRETE, "Hauptbahnhof"),
    EREIGNISFELD_3(Material.CYAN_CONCRETE, "Ereignisfeld 3"),
    PARKSTRASSE(Material.BLUE_CONCRETE, "Parkstraße"),
    ZUSATZSTEUER(Material.GOLD_BLOCK, "Zusatzsteuer"),
    SCHLOSSALLEE(Material.BLUE_CONCRETE, "Schlossallee");

    public final Material icon;
    public final String name;

}
