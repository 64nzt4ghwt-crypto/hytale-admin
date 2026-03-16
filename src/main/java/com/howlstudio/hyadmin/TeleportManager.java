package com.howlstudio.hyadmin;

import java.util.*;
import java.util.concurrent.*;

/**
 * Manages teleport operations: previous locations (back), teleport requests (tpa/tpahere),
 * and teleport delay logic.
 *
 * TPA flow: requester sends request → target accepts → requester teleports to target
 * TPAHERE flow: requester sends request → target accepts → target teleports to requester
 */
public class TeleportManager {

    public static final int TPA_TIMEOUT_MS = 60_000;

    /** Stores the last location of each player for /back */
    private final Map<UUID, double[]> lastLocations = new HashMap<>();

    /** Stores pending TPA requests: requester → {target, type, timestamp} */
    private final Map<UUID, TpaRequest> pendingRequests = new HashMap<>();

    /** Pending requests indexed by target for easy accept/deny: target → requester */
    private final Map<UUID, UUID> pendingByTarget = new HashMap<>();

    public enum TpaType { TO_TARGET, TARGET_TO_ME }

    public static class TpaRequest {
        public final UUID requester;
        public final UUID target;
        public final TpaType type;
        public final long timestamp;
        public TpaRequest(UUID requester, UUID target, TpaType type) {
            this.requester = requester;
            this.target = target;
            this.type = type;
            this.timestamp = System.currentTimeMillis();
        }
        public boolean isExpired() {
            return System.currentTimeMillis() - timestamp > TPA_TIMEOUT_MS;
        }
    }

    public void saveLastLocation(UUID uuid, double x, double y, double z) {
        lastLocations.put(uuid, new double[]{x, y, z});
    }

    public double[] getLastLocation(UUID uuid) {
        return lastLocations.get(uuid);
    }

    public boolean hasPendingRequest(UUID target) {
        UUID requester = pendingByTarget.get(target);
        if (requester == null) return false;
        TpaRequest req = pendingRequests.get(requester);
        if (req == null || req.isExpired()) {
            pendingRequests.remove(requester);
            pendingByTarget.remove(target);
            return false;
        }
        return true;
    }

    public void sendRequest(UUID requester, UUID target, TpaType type) {
        // Remove any existing request by this requester
        TpaRequest old = pendingRequests.get(requester);
        if (old != null) pendingByTarget.remove(old.target);
        TpaRequest req = new TpaRequest(requester, target, type);
        pendingRequests.put(requester, req);
        pendingByTarget.put(target, requester);
    }

    public TpaRequest getPendingRequest(UUID target) {
        UUID requester = pendingByTarget.get(target);
        if (requester == null) return null;
        TpaRequest req = pendingRequests.get(requester);
        if (req == null || req.isExpired()) {
            pendingRequests.remove(requester);
            pendingByTarget.remove(target);
            return null;
        }
        return req;
    }

    public void clearRequest(UUID target) {
        UUID requester = pendingByTarget.remove(target);
        if (requester != null) pendingRequests.remove(requester);
    }

    public void clearAllExpired() {
        Iterator<Map.Entry<UUID, TpaRequest>> it = pendingRequests.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<UUID, TpaRequest> entry = it.next();
            if (entry.getValue().isExpired()) {
                pendingByTarget.remove(entry.getValue().target);
                it.remove();
            }
        }
    }
}
