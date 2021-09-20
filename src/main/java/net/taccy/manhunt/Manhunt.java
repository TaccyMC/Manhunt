package net.taccy.manhunt;

import net.taccy.manhunt.commands.ManhuntCommand;
import net.taccy.manhunt.game.Game;
import org.bukkit.plugin.java.JavaPlugin;

public final class Manhunt extends JavaPlugin {

    public static final String WORLD_NAME = "manhunt";

    private Game game;

    @Override
    public void onEnable() {
        registerListeners();
        registerCommands();

        game = new Game(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        getCommand("manhunt").setExecutor(new ManhuntCommand(this));
        getCommand("manhunt").setTabCompleter(new ManhuntCommand(this));
    }

    private void registerListeners() {
    }

    public Game getGame() {
        return game;
    }

}
