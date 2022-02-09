package me.anton.sickcore.api.utils.common;

import net.md_5.bungee.api.ChatColor;

public class StringUtils {

    public static String capitalize(String s){
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static String centerText(String text, int lineLength) {
        char[] chars = text.toCharArray(); // Get a list of all characters in text
        boolean isBold = false;
        double length = 0;
        net.md_5.bungee.api.ChatColor pholder = null;
        for (int i = 0; i < chars.length; i++) { // Loop through all characters
            // Check if the character is a ColorCode..
            if (chars[i] == '&' && chars.length != (i + 1) && (pholder = ChatColor.getByChar(chars[i + 1])) != null) {
                if (pholder != ChatColor.UNDERLINE && pholder != ChatColor.ITALIC
                        && pholder != ChatColor.STRIKETHROUGH && pholder != ChatColor.MAGIC) {
                    isBold = (chars[i + 1] == 'l'); // Setting bold  to true or false, depending on if the ChatColor is Bold.
                    length--; // Removing one from the length, of the string, because we don't wanna count color codes.
                    i += isBold ? 1 : 0;
                }
            } else {
                // If the character is not a color code:
                length++; // Adding a space
                length += (isBold ? (chars[i] != ' ' ? 0.1555555555555556 : 0) : 0); // Adding 0.156 spaces if the character is bold.
            }
        }

        double spaces = (lineLength - length) / 2; // Getting the spaces to add by (max line length - length) / 2

        // Adding the spaces
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < spaces; i++) {
            builder.append(' ');
        }
        String copy = builder.toString();
        builder.append(text).append(copy);

        return builder.toString();
    }

    public static String getEnumName(Enum e){
        StringBuilder builder = new StringBuilder();
        for (String s : e.name().split("_")){
            if(!builder.toString().isEmpty()) builder.append(" ");
            builder.append(s.substring(0, 1).toUpperCase());
            builder.append(s.toLowerCase().substring(1, s.length()));
        }

        return builder.toString();
    }

}
