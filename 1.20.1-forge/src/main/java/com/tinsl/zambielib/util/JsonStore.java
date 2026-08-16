package com.tinsl.zambielib.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.function.Supplier;

/**
 * Small JSON persistence with an automatic backup. Saving keeps the previous
 * file as .bak and writes atomically (temp file, then move), and loading
 * falls back to the backup if the main file is missing or corrupt, so a
 * crash mid-write never eats a config.
 */
public final class JsonStore {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private JsonStore() {
    }

    public static <T> T load(Path path, Class<T> type, Supplier<T> fallback) {
        T loaded = tryRead(path, type);
        if (loaded != null) {
            return loaded;
        }
        loaded = tryRead(backupOf(path), type);
        return loaded != null ? loaded : fallback.get();
    }

    public static void save(Path path, Object value) throws IOException {
        Files.createDirectories(path.getParent());
        if (Files.exists(path)) {
            Files.copy(path, backupOf(path), StandardCopyOption.REPLACE_EXISTING);
        }
        writeAtomic(path, GSON.toJson(value));
    }

    /**
     * Writes to a sibling temp file, then moves it over the target, so a
     * crash mid-write can never truncate the real file. Falls back to a
     * plain replace on filesystems without atomic moves.
     */
    public static void writeAtomic(Path target, String content) throws IOException {
        Path temp = target.resolveSibling(target.getFileName() + ".tmp");
        Files.writeString(temp, content, StandardCharsets.UTF_8);
        try {
            Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException e) {
            Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static <T> T tryRead(Path path, Class<T> type) {
        try {
            if (!Files.exists(path)) {
                return null;
            }
            return GSON.fromJson(Files.readString(path, StandardCharsets.UTF_8), type);
        } catch (Exception e) {
            return null;
        }
    }

    private static Path backupOf(Path path) {
        return path.resolveSibling(path.getFileName() + ".bak");
    }
}
