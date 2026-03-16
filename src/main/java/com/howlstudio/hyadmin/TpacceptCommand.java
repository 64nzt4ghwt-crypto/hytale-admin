package com.howlstudio.hyadmin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.util.UUID;
/** /tpaccept */
public class TpacceptCommand extends AbstractPlayerCommand {
    private final TeleportManager tpManager;
    public TpacceptCommand(TeleportManager tpManager) { super("tpaccept", "Accept a teleport request. Usage: /tpaccept"); this.tpManager = tpManager; }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        UUID targetUuid = playerRef.getUuid();
        TeleportManager.TpaRequest req = tpManager.getPendingRequest(targetUuid);
        if (req == null) { playerRef.sendMessage(Message.raw("§c[HyAdmin] No pending teleport request.")); return; }
        tpManager.clearRequest(targetUuid);
        PlayerRef requester = Universe.get().getPlayer(req.requester);
        if (requester == null) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Requesting player is no longer online.")); return; }
        if (req.type == TeleportManager.TpaType.TO_TARGET) {
            double[] myPos = Util.getPos(playerRef);
            if (myPos == null) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Location error.")); return; }
            double[] rPos = Util.getPos(requester);
            if (rPos != null) tpManager.saveLastLocation(req.requester, rPos[0], rPos[1], rPos[2]);
            Util.teleport(requester, world, myPos[0], myPos[1], myPos[2]);
            requester.sendMessage(Message.raw("§a[HyAdmin] §fTeleported to §e" + playerRef.getUsername() + "§f."));
            playerRef.sendMessage(Message.raw("§a[HyAdmin] §fAccepted. §e" + requester.getUsername() + "§f teleported to you."));
        } else {
            double[] rPos = Util.getPos(requester);
            if (rPos == null) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Location error.")); return; }
            double[] myPos = Util.getPos(playerRef);
            if (myPos != null) tpManager.saveLastLocation(targetUuid, myPos[0], myPos[1], myPos[2]);
            Util.teleport(playerRef, world, rPos[0], rPos[1], rPos[2]);
            playerRef.sendMessage(Message.raw("§a[HyAdmin] §fTeleported to §e" + requester.getUsername() + "§f."));
            requester.sendMessage(Message.raw("§a[HyAdmin] §e" + playerRef.getUsername() + "§f teleported to you."));
        }
    }
}
