package cc.spea.playertroll;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.*;

public class PlayerTroll extends JavaPlugin implements Listener {
    public HashMap<String, HashSet<String>> trolls = new HashMap<>();
    public HashMap<String, Player> onlinePlayers = new HashMap<>();

    @Override
    public void onEnable() {
        trolls.put("crouch_hiss", new HashSet<>());
        trolls.put("ghost_blocks", new HashSet<>());
        trolls.put("vanish", new HashSet<>());

        for (Player p : getServer().getOnlinePlayers()) {
            onlinePlayers.put(p.getName(), p);
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
        for (String p : trolls.get("vanish")) {
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
        onlinePlayers.put(event.getPlayer().getName(), event.getPlayer());

        for (String p : trolls.get("vanish")) {
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

        for (String p : trolls.get("crouch_hiss")) {
            Player player = onlinePlayers.get(p);
            if (player == null) continue;

            if (Objects.equals(p, event.getPlayer().getName())) return;

            Location current = player.getLocation();
            current.add(5, 0, 0);

            onlinePlayers.get(p).playSound(current, Sound.ENTITY_CREEPER_PRIMED, 0.3f, 1.0f);
        }
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event) {
        if (trolls.get("ghost_blocks").contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            //event.getBlock().getLocation().add(0, 1, 0).getBlock().breakNaturally(event.getPlayer().getItemInUse());
            //event.getPlayer().sendBlockChange(event.getBlock().getLocation().add(0, -1, 0), Material.AIR.createBlockData());
        }
    }
}
