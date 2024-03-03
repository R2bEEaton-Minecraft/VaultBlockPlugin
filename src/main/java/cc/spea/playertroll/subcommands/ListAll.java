package cc.spea.playertroll.subcommands;

import cc.spea.playertroll.PlayerTroll;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.UUID;

public class ListAll extends SubCommand {

    public ListAll(PlayerTroll pt) {
        super(pt);
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public boolean perform(CommandSender sender, String[] args) {
        String message = ChatColor.RED + "[PlayerTroll] Usage: /pt list, /pt list <player>";

        if (args.length < 1) {
            sender.sendMessage(message);
            return false;
        }

        if (args[0].compareTo("list") != 0) {
            sender.sendMessage(message);
            return false;
        }

        ArrayList<String> str = new ArrayList<>();
        if (args.length == 1) {
            sender.sendMessage(ChatColor.GREEN + "[PlayerTroll] List of active trolls");
            for (String k : pt.trolls.keySet()) {
                StringBuilder out = new StringBuilder();
                out.append(ChatColor.GRAY).append(k).append(": ");
                for (UUID p : pt.trolls.get(k)) {
                    out.append(pt.getServer().getOfflinePlayer(p).getName()).append(" ");
                }
                str.add(out.toString());
            }
            sender.sendMessage(String.join("\n", str));
            return true;
        } else if (args.length == 2) {
            sender.sendMessage(ChatColor.GREEN + "[PlayerTroll] List of active trolls on player " + args[1]);
            for (String k : pt.trolls.keySet()) {
                for (UUID p : pt.trolls.get(k)) {
                    if (args[1].equalsIgnoreCase(pt.getServer().getOfflinePlayer(p).getName())) {
                        str.add(ChatColor.GRAY + k);
                    }
                }
            }
            sender.sendMessage(String.join("\n", str));
            return true;
        }

        sender.sendMessage(message);
        return false;
    }
}
