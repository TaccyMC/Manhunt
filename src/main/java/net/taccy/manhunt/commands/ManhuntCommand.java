package net.taccy.manhunt.commands;

import net.taccy.manhunt.Manhunt;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ManhuntCommand implements CommandExecutor {

    private Manhunt pl;

    public ManhuntCommand(Manhunt pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }

}
