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
/** /tpa <player> */
public class TpaCommand extends AbstractPlayerCommand {
    private final TeleportManager tpManager;
    public TpaCommand(TeleportManager tpManager) { super("tpa", "Request teleport to a player. Usage: /tpa <player>"); this.tpManager = tpManager; }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        String[] args = Util.args(ctx.getInputString(), 1);
        if (args.length == 0) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Usage: /tpa <player>")); return; }
        PlayerRef target = Util.findPlayer(args[0]);
        if (target == null) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Player §e" + args[0] + "§c not found.")); return; }
        UUID requester = playerRef.getUuid(), targetUuid = target.getUuid();
        if (requester != null && requester.equals(targetUuid)) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Can't teleport to yourself.")); return; }
        tpManager.sendRequest(requester, targetUuid, TeleportManager.TpaType.TO_TARGET);
        playerRef.sendMessage(Message.raw("§a[HyAdmin] §fRequest sent to §e" + target.getUsername() + "§f. Expires in 60s."));
        target.sendMessage(Message.raw("§e[HyAdmin] §e" + playerRef.getUsername() + "§f wants to teleport to you. §aType §e/tpaccept§a or §c/tpdeny§a."));
    }
}
