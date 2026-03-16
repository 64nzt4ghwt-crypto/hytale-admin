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
/** /back */
public class BackCommand extends AbstractPlayerCommand {
    private final TeleportManager tpManager;
    public BackCommand(TeleportManager tpManager) { super("back", "Return to your last location. Usage: /back"); this.tpManager = tpManager; }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        UUID uuid = playerRef.getUuid();
        double[] last = tpManager.getLastLocation(uuid);
        if (last == null) { playerRef.sendMessage(Message.raw("§c[HyAdmin] No previous location saved.")); return; }
        double[] cur = Util.getPos(playerRef);
        if (cur != null) tpManager.saveLastLocation(uuid, cur[0], cur[1], cur[2]);
        Util.teleport(playerRef, world, last[0], last[1], last[2]);
        playerRef.sendMessage(Message.raw("§a[HyAdmin] §fReturned to §e" + Util.posStr(last[0],last[1],last[2]) + "§f."));
    }
}
