package cc.spea.playertroll.subcommands;

import cc.spea.playertroll.PlayerTroll;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    public final PlayerTroll pt;

    protected SubCommand(PlayerTroll pt) {
        this.pt = pt;
    }

    public abstract String getName();
    public abstract boolean perform(CommandSender sender, String[] args);

}
