package cc.spea.playertroll.subcommands;

import cc.spea.playertroll.PlayerTroll;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GhostBlocks extends SubCommand {

    public GhostBlocks(PlayerTroll pt) {
        super(pt);
    }

    @Override
    public String getName() {
        return "ghost_blocks";
    }

    @Override
    public boolean perform(CommandSender sender, String[] args) {
        String message = ChatColor.RED + "[PlayerTroll] Usage: /pt ghost_blocks <player>";

        if (args.length != 2) {
            sender.sendMessage(message);
            return false;
        }

        String playerName;
        Player player = null;
        try {
            playerName = args[1];
            for (Player p : sender.getServer().getOnlinePlayers()) {
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

        if (this.pt.trolls.get("ghost_blocks").contains(player.getName())) {
            this.pt.trolls.get("ghost_blocks").remove(player.getName());
            sender.sendMessage(ChatColor.GREEN + "[PlayerTroll] ghost_blocks disabled for " + player.getName());
        } else {
            this.pt.trolls.get("ghost_blocks").add(player.getName());
            sender.sendMessage(ChatColor.GREEN + "[PlayerTroll] ghost_blocks enabled for " + player.getName());
        }

        return true;
    }
}
