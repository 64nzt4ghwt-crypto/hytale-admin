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
/** /warps */
public class WarpsCommand extends AbstractPlayerCommand {
    private final WarpManager warpManager;
    public WarpsCommand(WarpManager warpManager) { super("warps", "List all warp points. Usage: /warps"); this.warpManager = warpManager; }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        List<String> names = warpManager.getWarpNames();
        if (names.isEmpty()) { playerRef.sendMessage(Message.raw("§e[HyAdmin] §fNo warps defined. Use §e/setwarp <name>§f.")); return; }
        java.util.Collections.sort(names);
        playerRef.sendMessage(Message.raw("§a[HyAdmin] §fWarps (" + names.size() + "): §e" + String.join("§7, §e", names) + "§f. Use §e/warp <name>§f."));
    }
}
