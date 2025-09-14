package vn.iuh.util;

public class EntityUtil {
    public static String increaseEntityID(String entityID, String prefix, int suffixLength) {
        if (entityID == null) {
            String format = "%0" + suffixLength + "d";
            return prefix + String.format(format, 1);
        }

        String[] strings = entityID.split(prefix);
        int id = Integer.parseInt(strings[1]);
        id++;

        String format = "%0" + suffixLength + "d";
        return prefix + String.format(format, id);
    }
}
