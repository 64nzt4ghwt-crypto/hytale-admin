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
/** /tpahere <player> */
public class TpahereCommand extends AbstractPlayerCommand {
    private final TeleportManager tpManager;
    public TpahereCommand(TeleportManager tpManager) { super("tpahere", "Request a player to teleport to you. Usage: /tpahere <player>"); this.tpManager = tpManager; }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        String[] args = Util.args(ctx.getInputString(), 1);
        if (args.length == 0) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Usage: /tpahere <player>")); return; }
        PlayerRef target = Util.findPlayer(args[0]);
        if (target == null) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Player §e" + args[0] + "§c not found.")); return; }
        tpManager.sendRequest(playerRef.getUuid(), target.getUuid(), TeleportManager.TpaType.TARGET_TO_ME);
        playerRef.sendMessage(Message.raw("§a[HyAdmin] §fRequested §e" + target.getUsername() + "§f to come to you."));
        target.sendMessage(Message.raw("§e[HyAdmin] §e" + playerRef.getUsername() + "§f wants you to teleport to them. §aType §e/tpaccept§a or §c/tpdeny§a."));
    }
}
