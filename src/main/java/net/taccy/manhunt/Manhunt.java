package net.taccy.manhunt;

import net.taccy.manhunt.commands.ManhuntCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Manhunt extends JavaPlugin {

    @Override
    public void onEnable() {
        registerListeners();
        registerCommands();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        getCommand("manhunt").setExecutor(new ManhuntCommand(this));
    }

    private void registerListeners() {
    }

}
