package com.howlstudio.hyadmin;

import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.NameMatching;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;

/** Common helpers for HyAdmin commands */
public final class Util {
    private Util() {}

    /** Parse args from raw input string, splitting on whitespace. */
    public static String[] args(String input, int skipWords) {
        String trimmed = input.trim();
        String[] all = trimmed.split("\\s+");
        if (all.length <= skipWords) return new String[0];
        String[] result = new String[all.length - skipWords];
        System.arraycopy(all, skipWords, result, 0, result.length);
        return result;
    }

    /** Look up online player by name (exact match). */
    public static PlayerRef findPlayer(String name) {
        return Universe.get().getPlayerByUsername(name, NameMatching.EXACT);
    }

    /** Get position from a PlayerRef as double[3] {x,y,z}. Returns null if unavailable. */
    public static double[] getPos(PlayerRef player) {
        Transform t = player.getTransform();
        if (t == null) return null;
        Vector3d pos = t.getPosition();
        if (pos == null) return null;
        return new double[]{pos.getX(), pos.getY(), pos.getZ()};
    }

    /** Teleport a player to a position in their current world. */
    public static void teleport(PlayerRef player, World world, double x, double y, double z) {
        Transform t = new Transform(x, y, z);
        player.updatePosition(world, t, new Vector3f(0, 0, 0));
    }

    /** Format a position as "X, Y, Z". */
    public static String posStr(double x, double y, double z) {
        return String.format("%.0f, %.0f, %.0f", x, y, z);
    }

    /** Try parsing a double, return null if invalid. */
    public static Double parseDouble(String s) {
        try { return Double.parseDouble(s); } catch (NumberFormatException e) { return null; }
    }

    /** Try parsing an int in [min, max], return null if invalid. */
    public static Integer parseInt(String s, int min, int max) {
        try {
            int v = Integer.parseInt(s);
            return v >= min && v <= max ? v : null;
        } catch (NumberFormatException e) { return null; }
    }
}
