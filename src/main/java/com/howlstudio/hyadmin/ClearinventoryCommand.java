package com.howlstudio.hyadmin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/** /clearinventory — stub pending inventory API */
public class ClearinventoryCommand extends AbstractPlayerCommand {
    public ClearinventoryCommand() { super("clearinventory", "Clear your inventory. Usage: /clearinventory"); }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        playerRef.sendMessage(Message.raw("§e[HyAdmin] §f/clearinventory acknowledged. §7(Inventory clear will activate when inventory component API is available.)"));
    }
}
