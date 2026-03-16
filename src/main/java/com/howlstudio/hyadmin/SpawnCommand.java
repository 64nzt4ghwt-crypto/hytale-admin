package com.howlstudio.hyadmin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.util.UUID;
/** /spawn */
public class SpawnCommand extends AbstractPlayerCommand {
    private final WarpManager warpManager; private final TeleportManager tpManager;
    public SpawnCommand(WarpManager warpManager, TeleportManager tpManager) { super("spawn", "Teleport to world spawn."); this.warpManager = warpManager; this.tpManager = tpManager; }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        double[] spawn = warpManager.getSpawn();
        if (spawn == null) { playerRef.sendMessage(Message.raw("§c[HyAdmin] No spawn set. Admin can use §e/setspawn§c.")); return; }
        UUID uuid = playerRef.getUuid();
        double[] cur = Util.getPos(playerRef);
        if (cur != null) tpManager.saveLastLocation(uuid, cur[0], cur[1], cur[2]);
        Util.teleport(playerRef, world, spawn[0], spawn[1], spawn[2]);
        playerRef.sendMessage(Message.raw("§a[HyAdmin] §fTeleported to spawn."));
    }
}
