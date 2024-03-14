package cc.spea.vaultblockplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class VaultBlockPlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getCommand("vbp").setExecutor(new CommandManager(this));
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() != Material.TRIAL_SPAWNER) return;
        if (event.getItem() == null) return;
        if (event.getItem().getType() != Material.TRIAL_KEY) return;

        if (event.getClickedBlock().hasMetadata("vaultblockplugin")) {
            Bukkit.broadcastMessage("test lol");
        }
    }
}