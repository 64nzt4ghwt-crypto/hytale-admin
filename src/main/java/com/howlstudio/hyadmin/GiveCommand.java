package com.howlstudio.hyadmin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/** /give <player> <item> [amount] — stub pending inventory component API */
public class GiveCommand extends AbstractPlayerCommand {
    public GiveCommand() { super("give", "[Admin] Give item to player. Usage: /give <player> <item> [amount]"); }
    @Override
    protected void execute(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref,
                           PlayerRef playerRef, World world) {
        String[] args = Util.args(ctx.getInputString(), 1);
        if (args.length < 2) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Usage: /give <player> <item> [amount]")); return; }
        PlayerRef target = Util.findPlayer(args[0]);
        if (target == null) { playerRef.sendMessage(Message.raw("§c[HyAdmin] Player §e" + args[0] + "§c not found.")); return; }
        String item = args[1];
        String amount = args.length > 2 ? args[2] : "1";
        playerRef.sendMessage(Message.raw("§e[HyAdmin] §f/give called: §e" + item + " x" + amount + "§f to §e" + target.getUsername() + "§7. (Inventory API stub — will be activated when item component is available.)"));
    }
}
