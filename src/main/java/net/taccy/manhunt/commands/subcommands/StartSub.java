package net.taccy.manhunt.commands.subcommands;

import net.taccy.manhunt.Manhunt;
import net.taccy.manhunt.commands.SubCommand;
import net.taccy.manhunt.game.Game;
import net.taccy.manhunt.game.states.GraceGameState;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class StartSub extends SubCommand {

    public StartSub(Manhunt pl) {
        super(pl);
    }

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getPermission() {
        return "manhunt.start";
    }

    @Override
    public List<String> getSubCommands() {
        return null;
    }

    @Override
    public void handle(CommandSender sender, ArrayList<String> args) {
        Game game = pl.getGame();
        if (game == null) {
            sender.sendMessage("there is no game!");
            return;
        }

        game.setGameState(new GraceGameState(game, pl));
    }

}
