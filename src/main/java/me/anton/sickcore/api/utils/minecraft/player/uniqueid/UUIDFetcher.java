package me.anton.sickcore.api.utils.minecraft.player.uniqueid;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class UUIDFetcher {


    private static final HashMap<String, UUID> uniqueIdCache = new HashMap<>();
    private static final HashMap<UUID, String> nameCache = new HashMap<>();

    private static final Pattern pattern = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");

    public static UUID fetchUniqueId(String name) {
        if (uniqueIdCache.containsKey(name)) return uniqueIdCache.get(name);
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://api.minetools.eu/uuid/" + name).openConnection();
            connection.setDoOutput(false);
            connection.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11"
            );
            connection.setUseCaches(true);
            connection.connect();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String uniqueIDString = fetchLineByUUID(reader.lines());
                if (uniqueIDString != null) {
                    UUID uniqueID = UUID.fromString(pattern.matcher(uniqueIDString).replaceAll("$1-$2-$3-$4-$5"));
                    uniqueIdCache.put(name, uniqueID);
                    nameCache.put(uniqueID, name);
                    return uniqueID;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String fetchName(UUID uniqueId) {
        if (nameCache.containsKey(uniqueId)) return nameCache.get(uniqueId);
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://api.minetools.eu/profile/" + uniqueId.toString()).openConnection();
            connection.setDoOutput(false);
            connection.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11"
            );
            connection.setUseCaches(true);
            connection.connect();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String name = fetchLineByName(reader.lines());
                if (name != null) {
                    nameCache.put(uniqueId, name);
                    uniqueIdCache.put(name, uniqueId);
                    return name;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String fetchLineByName(Stream<String> stream) {
        String line = stream.filter(e -> e.trim().startsWith("\"name\": ")).findFirst().orElse(null);
        return line == null ? null : line
                .replaceAll("\"", "")
                .replaceAll("name: ", "")
                .replaceAll(",", "")
                .trim();
    }

    private static String fetchLineByUUID(Stream<String> stream) {
        String line = stream.filter(e -> e.trim().startsWith("\"id\": ")).findFirst().orElse(null);
        return line == null ? null : line
                .replace("\"", "")
                .replace("id: ", "")
                .replace(",", "")
                .trim();
    }

}
