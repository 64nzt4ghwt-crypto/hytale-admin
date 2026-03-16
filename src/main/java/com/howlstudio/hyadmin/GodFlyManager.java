package com.howlstudio.hyadmin;

import java.util.*;

/**
 * Tracks god mode (invincibility) and fly mode toggles per player.
 * State is in-memory only (resets on server restart).
 */
public class GodFlyManager {

    private final Set<UUID> godPlayers = new HashSet<>();
    private final Set<UUID> flyPlayers = new HashSet<>();

    public boolean toggleGod(UUID uuid) {
        if (godPlayers.contains(uuid)) {
            godPlayers.remove(uuid);
            return false;
        }
        godPlayers.add(uuid);
        return true;
    }

    public boolean isGod(UUID uuid) {
        return godPlayers.contains(uuid);
    }

    public boolean toggleFly(UUID uuid) {
        if (flyPlayers.contains(uuid)) {
            flyPlayers.remove(uuid);
            return false;
        }
        flyPlayers.add(uuid);
        return true;
    }

    public boolean isFly(UUID uuid) {
        return flyPlayers.contains(uuid);
    }

    public void clear(UUID uuid) {
        godPlayers.remove(uuid);
        flyPlayers.remove(uuid);
    }
}
