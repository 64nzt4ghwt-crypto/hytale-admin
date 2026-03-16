package com.howlstudio.hyadmin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.util.*;
/** /homes */
public class HomesCommand extends AbstractPlayerCommand {
    private final HomeManager homeManager;
    public HomesCommand(HomeManager homeManager) { super("homes", "List all your homes."); this.homeManager = homeManager; }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        UUID uuid = playerRef.getUuid();
        Map<String, double[]> homes = homeManager.getHomes(uuid);
        if (homes.isEmpty()) { playerRef.sendMessage(Message.raw("§e[HyAdmin] §fNo homes set. Use §e/sethome§f.")); return; }
        List<String> names = new ArrayList<>(homes.keySet());
        Collections.sort(names);
        StringBuilder sb = new StringBuilder("§a[HyAdmin] §fHomes (").append(homes.size()).append("/").append(homeManager.getMaxHomes()).append("): ");
        for (int i = 0; i < names.size(); i++) {
            if (i > 0) sb.append("§7, ");
            double[] p = homes.get(names.get(i));
            sb.append("§e").append(names.get(i)).append(" §7(").append(Util.posStr(p[0],p[1],p[2])).append(")");
        }
        playerRef.sendMessage(Message.raw(sb.toString()));
    }
}
