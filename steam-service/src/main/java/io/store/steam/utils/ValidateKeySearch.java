package io.store.steam.utils;

import java.util.Set;

public class ValidateKeySearch {
    public static String validateKeyForSearch(String key) {
        if (key == null || key.isEmpty()) {
            return null;
        }

        Set<String> allowedFields = Set.of("title", "developer", "price", "releaseDate", "publisher", "platform", "country", "status", "stock", "discount", "genres", "features");
        if (!allowedFields.contains(key)) {
            throw new IllegalArgumentException("Invalid search field: " + key);
        }

        return key.toLowerCase();
    }
}
