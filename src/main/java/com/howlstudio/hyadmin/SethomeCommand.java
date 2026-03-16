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

/** /sethome [name] */
public class SethomeCommand extends AbstractPlayerCommand {
    private final HomeManager homeManager;
    public SethomeCommand(HomeManager homeManager) {
        super("sethome", "Set a home at your location. Usage: /sethome [name]");
        this.homeManager = homeManager;
    }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        UUID uuid = playerRef.getUuid();
        String[] args = Util.args(ctx.getInputString(), 1);
        String name = args.length > 0 ? args[0].toLowerCase() : "home";
        if (!name.matches("[a-z0-9_]+") || name.length() > 16) {
            playerRef.sendMessage(Message.raw("§c[HyAdmin] Home name: 1-16 chars, letters/numbers/underscore only."));
            return;
        }
        double[] pos = Util.getPos(playerRef);
        if (pos == null) {
            playerRef.sendMessage(Message.raw("§c[HyAdmin] Could not get your location."));
            return;
        }
        if (!homeManager.setHome(uuid, name, pos[0], pos[1], pos[2])) {
            playerRef.sendMessage(Message.raw("§c[HyAdmin] Max homes reached (" + homeManager.getMaxHomes() + "). Delete one with §e/delhome§c."));
            return;
        }
        playerRef.sendMessage(Message.raw("§a[HyAdmin] §fHome §e" + name + "§f set at §e" +
            Util.posStr(pos[0], pos[1], pos[2]) + "§f."));
    }
}
