package cc.spea.playertroll.subcommands;

import cc.spea.playertroll.PlayerTroll;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlaceSpoof extends SubCommand {

    public PlaceSpoof(PlayerTroll pt) {
        super(pt);
    }

    @Override
    public String getName() {
        return "place_spoof";
    }

    @Override
    public boolean perform(CommandSender sender, String[] args) {
        String message = ChatColor.RED + "[PlayerTroll] Usage: /pt place_spoof <player>";

        if (args.length != 2) {
            sender.sendMessage(message);
            return false;
        }

        String playerName;
        OfflinePlayer player = null;
        try {
            playerName = args[1];
            for (OfflinePlayer p : sender.getServer().getOfflinePlayers()) {
                if (p.getName().equalsIgnoreCase(playerName)) {
                    player = p;
                    break;
                }
            }
            if (player == null) throw new Exception("Player " + playerName + " not found");
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "[PlayerTroll] " + e.getMessage());
            return false;
        }

        if (this.pt.trolls.get("place_spoof").contains(player.getUniqueId())) {
            this.pt.trolls.get("place_spoof").remove(player.getUniqueId());
            sender.sendMessage(ChatColor.GREEN + "[PlayerTroll] place_spoof disabled for " + player.getName());
        } else {
            this.pt.trolls.get("place_spoof").add(player.getUniqueId());
            sender.sendMessage(ChatColor.GREEN + "[PlayerTroll] place_spoof enabled for " + player.getName());
        }

        return true;
    }
}
