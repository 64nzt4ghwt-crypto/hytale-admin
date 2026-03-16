package com.howlstudio.hyadmin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/** /broadcast <message> — server-wide announcement */
public class BroadcastCommand extends AbstractPlayerCommand {
    public BroadcastCommand() { super("broadcast", "[Admin] Broadcast a message to all players. Usage: /broadcast <message>"); }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        String input = ctx.getInputString().trim();
        int sp = input.indexOf(' ');
        if (sp < 0 || sp == input.length() - 1) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Usage: /broadcast <message>")); return; }
        String text = input.substring(sp + 1);
        Universe.get().sendMessage(Message.raw("§6[Broadcast] §e" + text));
    }
}
