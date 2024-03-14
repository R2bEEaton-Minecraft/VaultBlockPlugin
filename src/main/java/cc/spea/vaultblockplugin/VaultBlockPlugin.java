package cc.spea.vaultblockplugin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VaultBlockPlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() != Material.TRIAL_SPAWNER) return;
        if (event.getItem() == null) return;
        if (event.getItem().getType() != Material.TRIAL_KEY) return;

        Random random = new Random();
        event.getClickedBlock().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.ENTITY_TURTLE_EGG_CRACK, 1.0f, 0.5f);
        LootContext.Builder lootContextBuilder = new LootContext.Builder(event.getClickedBlock().getLocation());

        // Pick between ship treasure and trial reward
        List<ItemStack> lootItems = new ArrayList<>();
        if (random.nextBoolean()) {
            lootItems.addAll(LootTables.TRIAL_CHAMBERS_REWARD.getLootTable().populateLoot(random, lootContextBuilder.build()));
        } else {
            lootItems.addAll(LootTables.SHIPWRECK_TREASURE.getLootTable().populateLoot(random, lootContextBuilder.build()));
        }

        // Drop all items
        for (ItemStack item : lootItems) {
            event.getClickedBlock().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), item);
        }

        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) event.getItem().setAmount(event.getItem().getAmount() - 1);
    }
}