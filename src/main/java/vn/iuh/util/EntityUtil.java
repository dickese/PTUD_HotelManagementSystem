package vn.iuh.util;

public class EntityUtil {
    public static String increaseEntityID(String entityID, String prefix, int suffixLength) {
        String[] strings = entityID.split(prefix);
        int id = Integer.parseInt(strings[1]);
        id++;

        String format = "%0" + suffixLength + "d";
        return "RO" + String.format(format, id);
    }
}
