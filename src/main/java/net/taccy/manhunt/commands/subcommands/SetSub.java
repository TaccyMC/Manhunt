package net.taccy.manhunt.commands.subcommands;

import net.taccy.manhunt.Manhunt;
import net.taccy.manhunt.commands.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class SetSub extends SubCommand {

    public SetSub(Manhunt pl) {
        super(pl);
    }

    @Override
    public String getName() {
        return "set";
    }

    @Override
    public String getPermission() {
        return "manhunt.set";
    }

    @Override
    public List<String> getSubCommands() {
        return null;
    }

    @Override
    public void handle(CommandSender sender, ArrayList<String> args) {
        if (args.size() == 0) {
            sender.sendMessage("specify name");
            return;
        }

        String name = args.get(0);
        Manhunt.TARGET_NAME = name;
        sender.sendMessage("Set target to " + name);
    }

}
