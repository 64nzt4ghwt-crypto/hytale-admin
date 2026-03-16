package com.howlstudio.hyadmin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/** /feed — restore hunger (stub pending hunger component API) */
public class FeedCommand extends AbstractPlayerCommand {
    public FeedCommand() { super("feed", "Restore full hunger. Usage: /feed"); }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        playerRef.sendMessage(Message.raw("§a[HyAdmin] §fFeed command received. §7(Hunger restore event will fire when hunger component is accessible.)"));
    }
}
