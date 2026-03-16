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
import java.util.List;
import java.util.stream.Collectors;
/** /list — list all online players */
public class ListCommand extends AbstractPlayerCommand {
    public ListCommand() { super("list", "List online players. Usage: /list"); }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        List<PlayerRef> players = Universe.get().getPlayers();
        if (players.isEmpty()) { playerRef.sendMessage(Message.raw("§e[HyAdmin] §fNo players online.")); return; }
        String names = players.stream()
            .map(p -> "§e" + (p.getUsername() != null ? p.getUsername() : "?"))
            .collect(Collectors.joining("§7, "));
        playerRef.sendMessage(Message.raw("§a[HyAdmin] §fOnline (" + players.size() + "): " + names));
    }
}
