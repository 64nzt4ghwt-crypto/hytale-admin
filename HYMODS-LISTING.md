# HyAdmin — Admin Toolkit Plugin

**Name:** HyAdmin  
**Version:** 1.0.0  
**Category:** Administration  
**Price:** $4.99  
**Tags:** admin, essentials, teleport, home, warp, gamemode, broadcast, staff

---

## Short Description (hymods.io listing)
The essential admin toolkit for Hytale servers. Home system, warps, teleportation, game mode control, and 27 commands in one plugin — the Hytale equivalent of EssentialsX.

---

## Full Description

**HyAdmin** is the all-in-one administration plugin every Hytale server needs. If you run a server, you need HyAdmin.

### Teleportation System
- `/tp <player>` or `/tp <x> <y> <z>` — Admin teleport
- `/tpa <player>` — Request teleport to another player
- `/tpahere <player>` — Request player to teleport to you
- `/tpaccept` / `/tpdeny` — Accept or deny requests (60s timeout)
- `/back` — Return to your last location

### Home System
- `/home [name]` — Teleport to a named home
- `/sethome [name]` — Set a home at your current position (up to 3 homes)
- `/delhome <name>` — Delete a home
- `/homes` — List all your homes with coordinates

### Spawn & Warps
- `/spawn` — Teleport to server spawn
- `/setspawn` — Set server spawn (admin)
- `/warp <name>` — Teleport to a server warp
- `/setwarp <name>` — Create a warp (admin)
- `/delwarp <name>` — Remove a warp (admin)
- `/warps` — List all warps

### Admin Tools
- `/gamemode <creative|adventure>` — Change game mode
- `/god` — Toggle invincibility
- `/fly` — Toggle flight
- `/heal` — Restore health
- `/feed` — Restore hunger
- `/give <player> <item> [amount]` — Give items
- `/clearinventory` — Clear inventory
- `/hat` — Wear held item as helmet
- `/broadcast <message>` — Server-wide announcement

### Player Info
- `/whois <player>` — UUID, location, language
- `/seen <player>` — Check if player is online
- `/list` — List all online players

### Features
- **Persistent homes and warps** — Survive server restarts
- **Teleport request system** — With 60-second timeout and accept/deny
- **Back command** — Never lose your place after teleporting
- **ECS-native** — Uses Hytale's event system properly

---

## Compatibility
- Hytale Early Access
- Java 21+
- No dependencies

---

## Installation
Drop `HyAdmin-1.0.0.jar` into your server's `plugins/` folder. Restart. All commands immediately available.
