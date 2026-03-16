package com.howlstudio.hyadmin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/** /whois <player> — show basic player info */
public class WhoisCommand extends AbstractPlayerCommand {
    public WhoisCommand() { super("whois", "[Admin] Show player info. Usage: /whois <player>"); }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        String[] args = Util.args(ctx.getInputString(), 1);
        if (args.length == 0) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Usage: /whois <player>")); return; }
        PlayerRef target = Util.findPlayer(args[0]);
        if (target == null) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Player §e" + args[0] + "§c not found online.")); return; }
        double[] pos = Util.getPos(target);
        String posStr = pos != null ? Util.posStr(pos[0], pos[1], pos[2]) : "unknown";
        String uuid = target.getUuid() != null ? target.getUuid().toString() : "unknown";
        playerRef.sendMessage(Message.raw("§6[HyAdmin] §ePlayer: §f" + target.getUsername()));
        playerRef.sendMessage(Message.raw("  §7UUID: §f" + uuid));
        playerRef.sendMessage(Message.raw("  §7Location: §f" + posStr));
        playerRef.sendMessage(Message.raw("  §7Language: §f" + target.getLanguage()));
    }
}
