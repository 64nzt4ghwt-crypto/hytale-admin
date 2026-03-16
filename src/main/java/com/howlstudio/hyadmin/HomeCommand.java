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

/** /home [name] */
public class HomeCommand extends AbstractPlayerCommand {
    private final HomeManager homeManager;
    private final TeleportManager tpManager;
    public HomeCommand(HomeManager homeManager, TeleportManager tpManager) {
        super("home", "Teleport to your home. Usage: /home [name]");
        this.homeManager = homeManager;
        this.tpManager = tpManager;
    }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        UUID uuid = playerRef.getUuid();
        String[] args = Util.args(ctx.getInputString(), 1);
        String homeName = args.length > 0 ? args[0] : "home";
        double[] pos = homeManager.getHome(uuid, homeName);
        if (pos == null) {
            playerRef.sendMessage(Message.raw(args.length == 0
                ? "§c[HyAdmin] No default home. Use §e/sethome§c to set one."
                : "§c[HyAdmin] Home §e" + homeName + "§c not found."));
            return;
        }
        double[] cur = Util.getPos(playerRef);
        if (cur != null) tpManager.saveLastLocation(uuid, cur[0], cur[1], cur[2]);
        Util.teleport(playerRef, world, pos[0], pos[1], pos[2]);
        playerRef.sendMessage(Message.raw("§a[HyAdmin] §fWelcome home" +
            (homeName.equals("home") ? "" : " (" + homeName + ")") + "§f."));
    }
}
