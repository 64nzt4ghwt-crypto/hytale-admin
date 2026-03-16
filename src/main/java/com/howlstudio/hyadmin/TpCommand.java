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

/** /tp <player> | /tp <x> <y> <z> */
public class TpCommand extends AbstractPlayerCommand {
    private final TeleportManager tpManager;
    public TpCommand(TeleportManager tpManager) {
        super("tp", "[Admin] Teleport. Usage: /tp <player> | /tp <x> <y> <z>");
        this.tpManager = tpManager;
    }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        String[] args = Util.args(ctx.getInputString(), 1);
        UUID uuid = playerRef.getUuid();
        if (args.length == 0) {
            playerRef.sendMessage(Message.raw("§c[HyAdmin] Usage: /tp <player> | /tp <x> <y> <z>"));
            return;
        }
        if (args.length == 3) {
            Double x = Util.parseDouble(args[0]), y = Util.parseDouble(args[1]), z = Util.parseDouble(args[2]);
            if (x != null && y != null && z != null) {
                double[] cur = Util.getPos(playerRef);
                if (cur != null) tpManager.saveLastLocation(uuid, cur[0], cur[1], cur[2]);
                Util.teleport(playerRef, world, x, y, z);
                playerRef.sendMessage(Message.raw("§a[HyAdmin] §fTeleported to §e" + Util.posStr(x, y, z) + "§f."));
                return;
            }
        }
        PlayerRef target = Util.findPlayer(args[0]);
        if (target == null) {
            playerRef.sendMessage(Message.raw("§c[HyAdmin] Player §e" + args[0] + "§c not found."));
            return;
        }
        double[] tPos = Util.getPos(target);
        if (tPos == null) {
            playerRef.sendMessage(Message.raw("§c[HyAdmin] Could not get target's location."));
            return;
        }
        double[] cur = Util.getPos(playerRef);
        if (cur != null) tpManager.saveLastLocation(uuid, cur[0], cur[1], cur[2]);
        Util.teleport(playerRef, world, tPos[0], tPos[1], tPos[2]);
        playerRef.sendMessage(Message.raw("§a[HyAdmin] §fTeleported to §e" + target.getUsername() + "§f."));
    }
}
