package com.howlstudio.hyadmin;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Manages player home locations with persistent storage.
 * Homes are stored in <dataDir>/homes/<uuid>.dat
 * Format: home_name:x:y:z:worldId (one per line)
 */
public class HomeManager {

    private final Path dataDir;
    private final Map<UUID, Map<String, double[]>> homes = new HashMap<>();
    private static final int MAX_HOMES = 3;
    private static final String DEFAULT_HOME = "home";

    public HomeManager(Path dataDir) {
        this.dataDir = dataDir.resolve("homes");
        try {
            Files.createDirectories(this.dataDir);
        } catch (IOException e) {
            System.err.println("[HyAdmin] Could not create homes directory: " + e.getMessage());
        }
    }

    public Map<String, double[]> getHomes(UUID uuid) {
        return homes.computeIfAbsent(uuid, this::loadHomes);
    }

    public boolean setHome(UUID uuid, String name, double x, double y, double z) {
        Map<String, double[]> playerHomes = getHomes(uuid);
        if (!playerHomes.containsKey(name) && playerHomes.size() >= MAX_HOMES) {
            return false; // max homes reached
        }
        playerHomes.put(name.toLowerCase(), new double[]{x, y, z});
        saveHomes(uuid, playerHomes);
        return true;
    }

    public boolean deleteHome(UUID uuid, String name) {
        Map<String, double[]> playerHomes = getHomes(uuid);
        if (playerHomes.remove(name.toLowerCase()) == null) return false;
        saveHomes(uuid, playerHomes);
        return true;
    }

    public double[] getHome(UUID uuid, String name) {
        return getHomes(uuid).get(name.toLowerCase());
    }

    public double[] getDefaultHome(UUID uuid) {
        return getHome(uuid, DEFAULT_HOME);
    }

    public int getMaxHomes() { return MAX_HOMES; }

    private Map<String, double[]> loadHomes(UUID uuid) {
        Map<String, double[]> result = new HashMap<>();
        Path file = dataDir.resolve(uuid + ".dat");
        if (!Files.exists(file)) return result;
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split(":");
                if (parts.length < 4) continue;
                String name = parts[0];
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double z = Double.parseDouble(parts[3]);
                result.put(name, new double[]{x, y, z});
            }
        } catch (Exception e) {
            System.err.println("[HyAdmin] Failed to load homes for " + uuid + ": " + e.getMessage());
        }
        return result;
    }

    private void saveHomes(UUID uuid, Map<String, double[]> playerHomes) {
        Path file = dataDir.resolve(uuid + ".dat");
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file))) {
            for (Map.Entry<String, double[]> entry : playerHomes.entrySet()) {
                double[] pos = entry.getValue();
                writer.printf("%s:%.2f:%.2f:%.2f%n", entry.getKey(), pos[0], pos[1], pos[2]);
            }
        } catch (IOException e) {
            System.err.println("[HyAdmin] Failed to save homes for " + uuid + ": " + e.getMessage());
        }
    }

    public void saveAll() {
        for (Map.Entry<UUID, Map<String, double[]>> entry : homes.entrySet()) {
            saveHomes(entry.getKey(), entry.getValue());
        }
    }
}
