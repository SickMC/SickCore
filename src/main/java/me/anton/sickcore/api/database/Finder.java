package me.anton.sickcore.api.database;

import com.mongodb.client.model.Filters;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.conversions.Bson;

@Data
@Getter
@Setter
public class Finder {

    private final String key;
    private final Object value;

    public Bson bson(){
        return Filters.eq(key, value);
    }

    public static Finder stringFinder(String key, String value){
        return new Finder(key, value);
    }

}
