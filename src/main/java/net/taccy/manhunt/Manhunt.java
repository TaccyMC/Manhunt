package net.taccy.manhunt;

import net.taccy.manhunt.commands.ManhuntCommand;
import net.taccy.manhunt.game.Game;
import net.taccy.manhunt.listeners.CompassListener;
import net.taccy.manhunt.listeners.JoinListener;
import net.taccy.manhunt.listeners.MoveListener;
import net.taccy.manhunt.managers.CompassManager;
import net.taccy.manhunt.managers.ConfigManager;
import net.taccy.manhunt.managers.TimingsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class Manhunt extends JavaPlugin {

    public static final String WORLD_NAME = "world";
    public static final String TARGET_NAME = "Flexibles";
    public static final int COMPASS_SLOT = 8;
    public static UUID WORLD_UUID;

    private Game game;

    public ConfigManager cm = new ConfigManager(this);
    public TimingsManager tm = new TimingsManager(this);
    public CompassManager cpm = new CompassManager(this);

    @Override
    public void onEnable() {
        WORLD_UUID = Bukkit.getWorld(WORLD_NAME).getUID();

        registerListeners();
        registerCommands();
        registerRunnables();

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
        getServer().getPluginManager().registerEvents(new CompassListener(this), this);
    }

    private void registerRunnables() {
        tm.runTaskTimer(this, 0, 20);
        cpm.runTaskTimer(this, 0, 20);
    }

    public Game getGame() {
        return game;
    }

}
