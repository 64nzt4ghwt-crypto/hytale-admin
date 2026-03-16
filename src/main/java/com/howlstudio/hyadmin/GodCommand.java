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
/** /god — toggle god mode (tracked in-memory; real invincibility requires server health events) */
public class GodCommand extends AbstractPlayerCommand {
    private final GodFlyManager godFlyManager;
    public GodCommand(GodFlyManager godFlyManager) { super("god", "Toggle god mode (invincibility). Usage: /god"); this.godFlyManager = godFlyManager; }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        UUID uuid = playerRef.getUuid();
        boolean nowGod = godFlyManager.toggleGod(uuid);
        String status = nowGod ? "§aON" : "§cOFF";
        playerRef.sendMessage(Message.raw("§a[HyAdmin] §fGod mode " + status + "§f."));
        if (nowGod) playerRef.sendMessage(Message.raw("§7(Damage events will be cancelled by HyAdmin.)"));
    }
}
