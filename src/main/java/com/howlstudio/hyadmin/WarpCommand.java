package com.howlstudio.hyadmin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.util.List;
import java.util.UUID;
/** /warp <name> */
public class WarpCommand extends AbstractPlayerCommand {
    private final WarpManager warpManager; private final TeleportManager tpManager;
    public WarpCommand(WarpManager warpManager, TeleportManager tpManager) { super("warp", "Teleport to a warp. Usage: /warp <name>"); this.warpManager = warpManager; this.tpManager = tpManager; }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        String[] args = Util.args(ctx.getInputString(), 1);
        if (args.length == 0) {
            List<String> names = warpManager.getWarpNames();
            playerRef.sendMessage(names.isEmpty()
                ? Message.raw("§e[HyAdmin] §fNo warps defined. Admins can use §e/setwarp§f.")
                : Message.raw("§a[HyAdmin] §fWarps: §e" + String.join("§7, §e", names)));
            return;
        }
        double[] pos = warpManager.getWarp(args[0].toLowerCase());
        if (pos == null) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Warp §e" + args[0] + "§c not found. Try §e/warps§c.")); return; }
        UUID uuid = playerRef.getUuid();
        double[] cur = Util.getPos(playerRef);
        if (cur != null) tpManager.saveLastLocation(uuid, cur[0], cur[1], cur[2]);
        Util.teleport(playerRef, world, pos[0], pos[1], pos[2]);
        playerRef.sendMessage(Message.raw("§a[HyAdmin] §fWarped to §e" + args[0] + "§f."));
    }
}
