package cc.spea.playertroll.subcommands;

import cc.spea.playertroll.PlayerTroll;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CrouchHissCommand extends SubCommand {

    public CrouchHissCommand(PlayerTroll pt) {
        super(pt);
    }

    @Override
    public String getName() {
        return "crouch_hiss";
    }

    @Override
    public boolean perform(CommandSender sender, String[] args) {
        String message = ChatColor.RED + "[PlayerTroll] Usage: /pt crouch_hiss <player>";

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

        if (this.pt.trolls.get("crouch_hiss").contains(player.getUniqueId())) {
            this.pt.trolls.get("crouch_hiss").remove(player.getUniqueId());
            sender.sendMessage(ChatColor.GREEN + "[PlayerTroll] crouch_hiss disabled for " + player.getName());
        } else {
            this.pt.trolls.get("crouch_hiss").add(player.getUniqueId());
            sender.sendMessage(ChatColor.GREEN + "[PlayerTroll] crouch_hiss enabled for " + player.getName());
        }

        return true;
    }
}
