package cc.spea.playertroll.subcommands;

import cc.spea.playertroll.PlayerTroll;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Vanish extends SubCommand {

    public Vanish(PlayerTroll pt) {
        super(pt);
    }

    @Override
    public String getName() {
        return "vanish";
    }

    @Override
    public boolean perform(CommandSender sender, String[] args) {
        String message = ChatColor.RED + "[PlayerTroll] Usage: /pt vanish <player>";

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

        if (this.pt.trolls.get("vanish").contains(player.getUniqueId())) {
            this.pt.trolls.get("vanish").remove(player.getUniqueId());
            sender.sendMessage(ChatColor.GREEN + "[PlayerTroll] vanish disabled for " + player.getName());

            if (player.isOnline()) {
                for (Player p : this.pt.onlinePlayers.values()) {
                    p.showPlayer(this.pt, (Player) player);
                }
            }
        } else {
            this.pt.trolls.get("vanish").add(player.getUniqueId());
            sender.sendMessage(ChatColor.GREEN + "[PlayerTroll] vanish enabled for " + player.getName());

            if (player.isOnline()) {
                for (Player p : this.pt.onlinePlayers.values()) {
                    p.hidePlayer(this.pt, (Player) player);
                }
            }
        }

        return true;
    }
}
