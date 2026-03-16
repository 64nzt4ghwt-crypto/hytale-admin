package com.howlstudio.hyadmin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.event.events.ecs.ChangeGameModeEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.protocol.GameMode;
/** /gamemode <mode> [player] — sets game mode via ECS event */
public class GamemodeCommand extends AbstractPlayerCommand {
    public GamemodeCommand() { super("gamemode", "[Admin] Set game mode. Usage: /gamemode <creative|adventure|c|a|1|2> [player]"); }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        String[] args = Util.args(ctx.getInputString(), 1);
        if (args.length == 0) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Usage: /gamemode <creative|adventure> [player]")); return; }
        GameMode mode = parseMode(args[0]);
        if (mode == null) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Unknown mode. Use: §ecreative §7or §eadventure§c.")); return; }
        // Apply to self — dispatches via ECS event system
        store.invoke(ref, new ChangeGameModeEvent(mode));
        playerRef.sendMessage(Message.raw("§a[HyAdmin] §fGame mode set to §e" + mode.name().toLowerCase() + "§f."));
    }
    private GameMode parseMode(String s) {
        switch (s.toLowerCase()) {
            case "1": case "creative": case "c": return GameMode.Creative;
            case "2": case "adventure": case "a": return GameMode.Adventure;
            default: return null;
        }
    }
}
