package com.howlstudio.hyadmin;

import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

/**
 * HyAdmin — The essential admin toolkit for Hytale servers.
 * The Hytale equivalent of EssentialsX.
 *
 * Commands:
 *   /tp, /tpa, /tpahere, /tpaccept, /tpdeny — teleportation
 *   /back — return to last location
 *   /home [name], /sethome [name], /delhome <name>, /homes — home system (3 homes max)
 *   /spawn, /setspawn — world spawn
 *   /gamemode <creative|adventure> — game mode
 *   /god — toggle invincibility (tracked)
 *   /fly — toggle flight (tracked)
 *   /heal, /feed — restore health/hunger (stubs)
 *   /give <player> <item> [amount] — give item (stub)
 *   /clearinventory — clear inventory (stub)
 *   /hat — wear held item as helmet (stub)
 *   /broadcast <message> — server announcement
 *   /warp <name>, /setwarp <name>, /delwarp <name>, /warps — warp system
 *   /whois <player>, /seen <player>, /list — player info
 */
public final class HyAdminPlugin extends JavaPlugin {

    private HomeManager homeManager;
    private WarpManager warpManager;
    private TeleportManager tpManager;
    private GodFlyManager godFlyManager;

    public HyAdminPlugin(JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        System.out.println("[HyAdmin] Loading HyAdmin v1.0.0...");

        homeManager = new HomeManager(getDataDirectory());
        warpManager = new WarpManager(getDataDirectory());
        tpManager = new TeleportManager();
        godFlyManager = new GodFlyManager();

        CommandManager cmd = CommandManager.get();

        // Teleportation
        cmd.register(new TpCommand(tpManager));
        cmd.register(new TpaCommand(tpManager));
        cmd.register(new TpahereCommand(tpManager));
        cmd.register(new TpacceptCommand(tpManager));
        cmd.register(new TpdenyCommand(tpManager));
        cmd.register(new BackCommand(tpManager));
        cmd.register(new HomeCommand(homeManager, tpManager));
        cmd.register(new SethomeCommand(homeManager));
        cmd.register(new DelhomeCommand(homeManager));
        cmd.register(new HomesCommand(homeManager));
        cmd.register(new SpawnCommand(warpManager, tpManager));
        cmd.register(new SetspawnCommand(warpManager));

        // Admin tools
        cmd.register(new GamemodeCommand());
        cmd.register(new GodCommand(godFlyManager));
        cmd.register(new FlyCommand(godFlyManager));
        cmd.register(new HealCommand());
        cmd.register(new FeedCommand());
        cmd.register(new GiveCommand());
        cmd.register(new ClearinventoryCommand());
        cmd.register(new HatCommand());
        cmd.register(new BroadcastCommand());

        // Warps
        cmd.register(new WarpCommand(warpManager, tpManager));
        cmd.register(new SetwarpCommand(warpManager));
        cmd.register(new DelwarpCommand(warpManager));
        cmd.register(new WarpsCommand(warpManager));

        // Information
        cmd.register(new WhoisCommand());
        cmd.register(new SeenCommand());
        cmd.register(new ListCommand());

        System.out.println("[HyAdmin] Loaded 27 commands. Ready.");
    }

    @Override
    protected void shutdown() {
        System.out.println("[HyAdmin] Saving data...");
        if (homeManager != null) homeManager.saveAll();
        if (warpManager != null) warpManager.saveAll();
        System.out.println("[HyAdmin] Stopped.");
    }
}
