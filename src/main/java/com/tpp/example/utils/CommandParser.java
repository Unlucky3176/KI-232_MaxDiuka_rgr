package com.tpp.example.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {
    private static final Pattern COMMAND_PATTERN = Pattern.compile(
            "^\\s*(insert|update|delete|read)\\s+([a-zA-Z_][a-zA-Z0-9_]*)\\s*\\((.*)\\)\\s*;?\\s*$",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern PAIR_PATTERN = Pattern.compile("\\s*([a-zA-Z_][a-zA-Z0-9_]*)\\s*=\\s*'([^']*)'\\s*(?:,|$)");

    public static class Command {
        public final String action;
        public final String table;
        public final Map<String, String> fields;
        public Command(String action, String table, Map<String, String> fields) {
            this.action = action.toLowerCase();
            this.table = table.toLowerCase();
            this.fields = fields;
        }
    }

    public static Optional<Command> parse(String input) {
        Matcher m = COMMAND_PATTERN.matcher(input);
        if (!m.matches()) return Optional.empty();

        String action = m.group(1);
        String table = m.group(2);
        String inside = m.group(3);

        Map<String, String> fields = new LinkedHashMap<>();
        Matcher pm = PAIR_PATTERN.matcher(inside);
        int pos = 0;
        while (pm.find()) {
            if (pm.start() != pos) {
                return Optional.empty();
            }
            String key = pm.group(1);
            String value = pm.group(2);
            fields.put(key.toLowerCase(), value);
            pos = pm.end();
        }
        if (inside.trim().length() > 0 && pos != inside.length()) {
            return Optional.empty();
        }
        return Optional.of(new Command(action, table, fields));
    }
}
