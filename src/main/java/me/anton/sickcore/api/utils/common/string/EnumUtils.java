package me.anton.sickcore.api.utils.common.string;

public class EnumUtils {

    public static String toName(Enum e){
        StringBuilder builder = new StringBuilder();
        for (String s : e.name().split("_")){
            if(!builder.toString().isEmpty()) builder.append(" ");
            builder.append(s.substring(0, 1).toUpperCase());
            builder.append(s.toLowerCase().substring(1, s.length()));
        }

        return builder.toString();
    }

}
