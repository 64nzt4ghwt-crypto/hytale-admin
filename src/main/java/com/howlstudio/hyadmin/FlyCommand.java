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
/** /fly — toggle fly mode (tracked in-memory) */
public class FlyCommand extends AbstractPlayerCommand {
    private final GodFlyManager godFlyManager;
    public FlyCommand(GodFlyManager godFlyManager) { super("fly", "Toggle flight mode. Usage: /fly"); this.godFlyManager = godFlyManager; }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        UUID uuid = playerRef.getUuid();
        boolean nowFlying = godFlyManager.toggleFly(uuid);
        String status = nowFlying ? "§aON" : "§cOFF";
        playerRef.sendMessage(Message.raw("§a[HyAdmin] §fFly mode " + status + "§f."));
        if (nowFlying) playerRef.sendMessage(Message.raw("§7(Flight state tracked. Requires movement system integration.)"));
    }
}
