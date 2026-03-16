package com.howlstudio.hyadmin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/** /delwarp <name> */
public class DelwarpCommand extends AbstractPlayerCommand {
    private final WarpManager warpManager;
    public DelwarpCommand(WarpManager warpManager) { super("delwarp", "[Admin] Delete a warp. Usage: /delwarp <name>"); this.warpManager = warpManager; }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        String[] args = Util.args(ctx.getInputString(), 1);
        if (args.length == 0) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Usage: /delwarp <name>")); return; }
        boolean deleted = warpManager.deleteWarp(args[0].toLowerCase());
        playerRef.sendMessage(deleted
            ? Message.raw("§a[HyAdmin] §fWarp §e" + args[0] + "§f deleted.")
            : Message.raw("§c[HyAdmin] Warp §e" + args[0] + "§c not found."));
    }
}
