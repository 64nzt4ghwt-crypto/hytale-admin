package com.howlstudio.hyadmin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/** /seen <player> */
public class SeenCommand extends AbstractPlayerCommand {
    public SeenCommand() { super("seen", "Check if a player is online. Usage: /seen <player>"); }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        String[] args = Util.args(ctx.getInputString(), 1);
        if (args.length == 0) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Usage: /seen <player>")); return; }
        PlayerRef target = Util.findPlayer(args[0]);
        playerRef.sendMessage(target != null
            ? Message.raw("§a[HyAdmin] §e" + target.getUsername() + "§f is currently §aonline§f.")
            : Message.raw("§e[HyAdmin] §e" + args[0] + "§f is §coffline§f or has never joined."));
    }
}
