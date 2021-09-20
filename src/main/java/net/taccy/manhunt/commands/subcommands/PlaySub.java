package net.taccy.manhunt.commands.subcommands;

import net.taccy.manhunt.Manhunt;
import net.taccy.manhunt.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlaySub extends SubCommand {

    public PlaySub(Manhunt pl) {
        super(pl);
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public List<String> getSubCommands() {
        return null;
    }

    @Override
    public void handle(CommandSender sender, ArrayList<String> args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("The console cannot join the game :(");
            return;
        }

        Player p = (Player) sender;
        pl.getGame().join(p);
    }

}
