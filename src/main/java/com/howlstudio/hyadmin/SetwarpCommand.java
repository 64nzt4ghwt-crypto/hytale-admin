package com.howlstudio.hyadmin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/** /setwarp <name> */
public class SetwarpCommand extends AbstractPlayerCommand {
    private final WarpManager warpManager;
    public SetwarpCommand(WarpManager warpManager) { super("setwarp", "[Admin] Create warp at your location. Usage: /setwarp <name>"); this.warpManager = warpManager; }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        String[] args = Util.args(ctx.getInputString(), 1);
        if (args.length == 0) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Usage: /setwarp <name>")); return; }
        String name = args[0].toLowerCase();
        if (!name.matches("[a-z0-9_]+") || name.length() > 24) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Warp name: 1-24 chars, letters/numbers/underscore only.")); return; }
        double[] pos = Util.getPos(playerRef);
        if (pos == null) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Could not get your location.")); return; }
        warpManager.setWarp(name, pos[0], pos[1], pos[2]);
        playerRef.sendMessage(Message.raw("§a[HyAdmin] §fWarp §e" + name + "§f created at §e" + Util.posStr(pos[0],pos[1],pos[2]) + "§f."));
    }
}
