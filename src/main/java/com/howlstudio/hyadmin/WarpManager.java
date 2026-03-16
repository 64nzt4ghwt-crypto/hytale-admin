package com.howlstudio.hyadmin;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Manages server-wide warp points with persistent storage.
 * Warps stored in <dataDir>/warps.dat
 * Format: warp_name:x:y:z (one per line)
 * Special warp "_spawn" is used as the server spawn point.
 */
public class WarpManager {

    private final Path dataFile;
    private final Map<String, double[]> warps = new LinkedHashMap<>();
    private static final String SPAWN_KEY = "_spawn";

    public WarpManager(Path dataDir) {
        this.dataFile = dataDir.resolve("warps.dat");
        loadWarps();
    }

    public void setWarp(String name, double x, double y, double z) {
        warps.put(name.toLowerCase(), new double[]{x, y, z});
        saveAll();
    }

    public boolean deleteWarp(String name) {
        if (warps.remove(name.toLowerCase()) == null) return false;
        saveAll();
        return true;
    }

    public double[] getWarp(String name) {
        return warps.get(name.toLowerCase());
    }

    public void setSpawn(double x, double y, double z) {
        setWarp(SPAWN_KEY, x, y, z);
    }

    public double[] getSpawn() {
        return warps.get(SPAWN_KEY);
    }

    public List<String> getWarpNames() {
        List<String> names = new ArrayList<>();
        for (String name : warps.keySet()) {
            if (!name.startsWith("_")) names.add(name); // hide internal keys
        }
        return names;
    }

    public int getWarpCount() {
        return (int) warps.keySet().stream().filter(k -> !k.startsWith("_")).count();
    }

    private void loadWarps() {
        if (!Files.exists(dataFile)) return;
        try (BufferedReader reader = Files.newBufferedReader(dataFile)) {
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
                warps.put(name, new double[]{x, y, z});
            }
        } catch (Exception e) {
            System.err.println("[HyAdmin] Failed to load warps: " + e.getMessage());
        }
    }

    public void saveAll() {
        try {
            Files.createDirectories(dataFile.getParent());
            try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(dataFile))) {
                for (Map.Entry<String, double[]> entry : warps.entrySet()) {
                    double[] pos = entry.getValue();
                    writer.printf("%s:%.2f:%.2f:%.2f%n", entry.getKey(), pos[0], pos[1], pos[2]);
                }
            }
        } catch (IOException e) {
            System.err.println("[HyAdmin] Failed to save warps: " + e.getMessage());
        }
    }
}
