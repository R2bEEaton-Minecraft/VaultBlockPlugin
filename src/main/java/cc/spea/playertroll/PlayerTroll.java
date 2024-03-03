package cc.spea.playertroll;

import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.*;

public class PlayerTroll extends JavaPlugin implements Listener {
    public HashMap<String, HashSet<UUID>> trolls = new HashMap<>();
    public HashMap<UUID, Player> onlinePlayers = new HashMap<>();

    @Override
    public void onEnable() {
        trolls.put("crouch_hiss", new HashSet<>());
        trolls.put("ghost_blocks", new HashSet<>());
        trolls.put("vanish", new HashSet<>());
        trolls.put("place_spoof", new HashSet<>());
        trolls.put("swiss_chest", new HashSet<>());

        for (Player p : getServer().getOnlinePlayers()) {
            onlinePlayers.put(p.getUniqueId(), p);
        }

        getCommand("josh").setExecutor(new CommandManager(this));
        getCommand("josh").setTabCompleter(new CommandManager(this));

        //getServer().getPluginManager().registerEvents(new SafeBlock(sb), this);
        getServer().getPluginManager().registerEvents(this, this);

        new BukkitRunnable() {
            @Override
            public void run() {

            }
        }.runTaskTimer(this, 0, 2L);
    }

    @Override
    public void onDisable() {
        for (UUID p : trolls.get("vanish")) {
            if (onlinePlayers.containsKey(p)) {
                for (Player q : onlinePlayers.values()) {
                    q.showPlayer(this, onlinePlayers.get(p));
                }
            }
        }
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        if (trolls.get("vanish").contains(event.getPlayer().getName())) {
            event.setJoinMessage(null);
            for (Player p : onlinePlayers.values()) {
                p.hidePlayer(this, event.getPlayer());
            }
        }
        onlinePlayers.put(event.getPlayer().getUniqueId(), event.getPlayer());

        for (UUID p : trolls.get("vanish")) {
            if (onlinePlayers.containsKey(p)) {
                event.getPlayer().hidePlayer(this, onlinePlayers.get(p));
            }
        }
    }

    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent event) {
        if (trolls.get("vanish").contains(event.getPlayer().getName())) {
            event.setQuitMessage(null);
        }

        onlinePlayers.remove(event.getPlayer().getName());
    }

    @EventHandler
    public void playerSneakEvent(PlayerToggleSneakEvent event) {
        if (event.isSneaking()) return;

        for (UUID p : trolls.get("crouch_hiss")) {
            Player player = onlinePlayers.get(p);
            if (player == null) continue;

            if (Objects.equals(p, event.getPlayer().getUniqueId())) return;

            Location current = player.getLocation();
            current.add(5, 0, 0);

            onlinePlayers.get(p).playSound(current, Sound.ENTITY_CREEPER_PRIMED, 0.3f, 1.0f);
        }
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event) {
        if (trolls.get("ghost_blocks").contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            //event.getBlock().getLocation().add(0, 1, 0).getBlock().breakNaturally(event.getPlayer().getItemInUse());
            //event.getPlayer().sendBlockChange(event.getBlock().getLocation().add(0, -1, 0), Material.AIR.createBlockData());
        }
    }

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent event) {
        if (trolls.get("place_spoof").contains(event.getPlayer().getUniqueId())) {
            if (event.getBlock().getLocation().clone().add(0, -16, 0).getBlock().getType().isAir()) return;
            BlockData place_spoofBlock = event.getBlock().getLocation().clone().add(0, -16, 0).getBlock().getBlockData();

            Bukkit.getScheduler().scheduleSyncDelayedTask(this , () -> {
                for (Player player : onlinePlayers.values()) {
                    if (player.getUniqueId() == event.getPlayer().getUniqueId()) continue;
                    event.getPlayer().sendBlockChange(event.getBlock().getLocation(), place_spoofBlock);
                }
            }, 5L);
        }
    }

    @EventHandler
    public void blockPlaceEvent(InventoryOpenEvent event) {
        if (trolls.get("swiss_chest").contains(event.getPlayer().getUniqueId())) {
            Inventory oldInventory = event.getInventory();
            if (oldInventory.getViewers().size() > 1) return;

            // Add swiss chest code to hide random items from the chest lol
        }
    }
}
