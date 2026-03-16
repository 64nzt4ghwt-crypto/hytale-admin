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
/** /delhome <name> */
public class DelhomeCommand extends AbstractPlayerCommand {
    private final HomeManager homeManager;
    public DelhomeCommand(HomeManager homeManager) {
        super("delhome", "Delete a home. Usage: /delhome <name>");
        this.homeManager = homeManager;
    }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        String[] args = Util.args(ctx.getInputString(), 1);
        if (args.length == 0) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Usage: /delhome <name>")); return; }
        UUID uuid = playerRef.getUuid();
        boolean deleted = homeManager.deleteHome(uuid, args[0].toLowerCase());
        playerRef.sendMessage(deleted
            ? Message.raw("§a[HyAdmin] §fHome §e" + args[0] + "§f deleted.")
            : Message.raw("§c[HyAdmin] Home §e" + args[0] + "§c not found."));
    }
}
