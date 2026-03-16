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
/** /tpdeny */
public class TpdenyCommand extends AbstractPlayerCommand {
    private final TeleportManager tpManager;
    public TpdenyCommand(TeleportManager tpManager) { super("tpdeny", "Deny a teleport request. Usage: /tpdeny"); this.tpManager = tpManager; }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        UUID targetUuid = playerRef.getUuid();
        TeleportManager.TpaRequest req = tpManager.getPendingRequest(targetUuid);
        if (req == null) { playerRef.sendMessage(Message.raw("§c[HyAdmin] No pending request to deny.")); return; }
        tpManager.clearRequest(targetUuid);
        playerRef.sendMessage(Message.raw("§e[HyAdmin] §fTeleport request denied."));
        PlayerRef requester = Universe.get().getPlayer(req.requester);
        if (requester != null) requester.sendMessage(Message.raw("§c[HyAdmin] §e" + playerRef.getUsername() + "§c denied your teleport request."));
    }
}
