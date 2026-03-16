package com.howlstudio.hyadmin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/** /heal — restore health (acknowledgement command, full restore requires health component) */
public class HealCommand extends AbstractPlayerCommand {
    public HealCommand() { super("heal", "Restore full health. Usage: /heal"); }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        // TODO: Fire health restore event when Hytale health component API is available
        playerRef.sendMessage(Message.raw("§a[HyAdmin] §fHeal command received. §7(Health restore event will fire when health component is accessible.)"));
    }
}
