package net.taccy.manhunt.commands;

import net.taccy.manhunt.Manhunt;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class SubCommand {

    public Manhunt pl;

    public SubCommand(Manhunt pl) {
        this.pl = pl;
    }

    public abstract String getName();
    public abstract String getPermission();
    public abstract List<String> getSubCommands();
    public abstract void handle(CommandSender sender, ArrayList<String> args);

}