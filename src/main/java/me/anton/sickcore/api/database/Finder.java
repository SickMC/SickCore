package me.anton.sickcore.api.database;

import com.mongodb.client.model.Filters;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.conversions.Bson;

import java.util.UUID;

@Data
@Getter
@Setter
public class Finder {

    private final String key;
    private final Object value;

    public Bson toBson(){
        return Filters.eq(key, value);
    }

    public static Finder stringFinder(String key, String value){
        return new Finder(key, value);
    }
    public static Finder integerFinder(String key, Integer value){
        return new Finder(key, value);
    }
    public static Finder longFinder(String key, Long value){
        return new Finder(key, value);
    }
    public static Finder doubleFinder(String key, Double value){
        return new Finder(key, value);
    }
    public static Finder floatFinder(String key, Float value){
        return new Finder(key, value);
    }
    public static Finder objectFinder(String key, Object value){
        return new Finder(key, value);
    }
    public static Finder uniqueIdFinder(String key, UUID uniqueId) { return new Finder(key, uniqueId.toString()); }

}