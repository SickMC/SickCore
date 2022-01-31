package me.anton.sickcore.api.utils.common;

import lombok.SneakyThrows;
import org.bson.Document;

import java.io.*;
import java.util.Map;

public class FileUtils {

    @SneakyThrows
    public static Document getAsDocument(String file){
        InputStream inputStream = FileUtils.class.getClassLoader().getResourceAsStream("json/" + file + ".json");
        InputStreamReader reader = new InputStreamReader(inputStream, "UTF8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        return Document.parse(builder.toString());
    }

    public static Document getSubDocument(String key, Document document){
        return document.get(key, Document.class);
    }

}


