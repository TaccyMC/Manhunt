package net.taccy.manhunt;

import net.taccy.manhunt.commands.ManhuntCommand;
import net.taccy.manhunt.game.Game;
import net.taccy.manhunt.listeners.JoinListener;
import net.taccy.manhunt.listeners.MoveListener;
import net.taccy.manhunt.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class Manhunt extends JavaPlugin {

    public static final String WORLD_NAME = "manhunt";
    public static UUID WORLD_UUID;

    private Game game;

    public ConfigManager cm = new ConfigManager(this);

    @Override
    public void onEnable() {
        WORLD_UUID = Bukkit.getWorld(WORLD_NAME).getUID();

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
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new MoveListener(this), this);
    }

    public Game getGame() {
        return game;
    }

}
