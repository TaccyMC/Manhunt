package net.taccy.manhunt.managers;

import net.taccy.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

public class ConfigManager {

    private Manhunt pl;
    private final FileConfiguration dataConfiguration;
    private final File dataFile;

    public ConfigManager(Manhunt pl) {
        this.pl = pl;

        this.dataFile = new File(pl.getDataFolder(), "data.yml");
        this.dataConfiguration = new YamlConfiguration();

        // loading config could throw errors so try catch
        try {
            this.dataConfiguration.load(this.dataFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void set(UUID uuid, String key, Object value) {
        ConfigurationSection playerSection = dataConfiguration.getConfigurationSection(uuid.toString());
        if (playerSection == null) { playerSection = dataConfiguration.createSection(uuid.toString()); }
        playerSection.set(key, value);
        saveConfig();
    }

    public Object get(UUID uuid, String key) {
        ConfigurationSection playerSection = dataConfiguration.getConfigurationSection(uuid.toString());
        if (playerSection == null) { return null; }
        return playerSection.get(key);
    }

    public void writeLocation(Location loc, ConfigurationSection section) {
        section.set("world", loc.getWorld().getName());
        section.set("x", loc.getX());
        section.set("y", loc.getY());
        section.set("z", loc.getZ());
        section.set("yaw", loc.getYaw());
        section.set("pitch", loc.getPitch());
    }

    public void saveConfig() {
        try {
            dataConfiguration.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
