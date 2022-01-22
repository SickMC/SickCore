package me.anton.sickcore.api.database;

import lombok.Data;

@Data
public class DatabaseConfig {

    private final String address;
    private final Integer port;
    private final String username;
    private final String password;
    private final String database;
    private final String authBase;

}