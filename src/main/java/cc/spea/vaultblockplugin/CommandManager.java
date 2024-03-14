
package cc.spea.vaultblockplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.TrialSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class CommandManager implements CommandExecutor, Listener {
    private String inventoryName = "VaultBlockPlugin Items";
    private VaultBlockPlugin vbp;

    public CommandManager(VaultBlockPlugin vaultBlockPlugin) {
        this.vbp = vaultBlockPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        int inventorySize = 9;
        Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);

        ItemStack trial_vault = new ItemStack(Material.TRIAL_SPAWNER);
        ItemMeta orbMeta = trial_vault.getItemMeta();
        NamespacedKey key = new NamespacedKey(vbp, "vaultblockplugin");
        orbMeta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
        trial_vault.setItemMeta(orbMeta);
        inventory.addItem(trial_vault);

        player.openInventory(inventory);

        return true;
    }
}
