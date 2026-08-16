package com.tinsl.zambielib.text;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Percent-token expansion for user-editable text, the way DiscordPresence
 * fills its presence lines. Unknown tokens pass through untouched so a typo
 * shows itself instead of vanishing.
 */
public final class Placeholders {
    private final Map<String, Supplier<String>> suppliers = new HashMap<>();

    public Placeholders register(String key, Supplier<String> supplier) {
        suppliers.put(key, supplier);
        return this;
    }

    public String expand(String template) {
        if (template == null || template.indexOf('%') < 0) {
            return template;
        }
        StringBuilder out = new StringBuilder(template.length());
        int i = 0;
        while (i < template.length()) {
            char c = template.charAt(i);
            if (c == '%') {
                int end = template.indexOf('%', i + 1);
                if (end > i) {
                    String key = template.substring(i + 1, end);
                    Supplier<String> supplier = suppliers.get(key);
                    if (supplier != null) {
                        String value = supplier.get();
                        out.append(value == null ? "" : value);
                        i = end + 1;
                        continue;
                    }
                }
            }
            out.append(c);
            i++;
        }
        return out.toString();
    }
}
