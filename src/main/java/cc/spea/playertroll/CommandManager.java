package cc.spea.playertroll;

import cc.spea.playertroll.subcommands.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements TabExecutor {
    ArrayList<SubCommand> subcommands = new ArrayList<>();

    public CommandManager(PlayerTroll pt) {
        subcommands.add(new CrouchHissCommand(pt));
        subcommands.add(new GhostBlocks(pt));
        subcommands.add(new Vanish(pt));
        subcommands.add(new ListAll(pt));
    }

    public ArrayList<SubCommand> getSubCommands() { return subcommands; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean flag = false;
        if (args.length > 0) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    flag = getSubCommands().get(i).perform(sender, args);
                }
            }
        }
        if (!flag) {
            sender.sendMessage(ChatColor.RED + "[PlayerTroll] Invalid command.");
        }
        return flag;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < getSubCommands().size(); i++) {
                list.add(getSubCommands().get(i).getName());
            }
            return list;
        }
        return null;
    }
}
