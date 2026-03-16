package com.howlstudio.hyadmin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/** /setspawn */
public class SetspawnCommand extends AbstractPlayerCommand {
    private final WarpManager warpManager;
    public SetspawnCommand(WarpManager warpManager) { super("setspawn", "[Admin] Set world spawn. Usage: /setspawn"); this.warpManager = warpManager; }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        double[] pos = Util.getPos(playerRef);
        if (pos == null) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Could not get your location.")); return; }
        warpManager.setSpawn(pos[0], pos[1], pos[2]);
        playerRef.sendMessage(Message.raw("§a[HyAdmin] §fSpawn set to §e" + Util.posStr(pos[0],pos[1],pos[2]) + "§f."));
    }
}
