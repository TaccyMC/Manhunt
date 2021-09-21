package net.taccy.manhunt.managers;

import net.taccy.manhunt.Manhunt;
import net.taccy.manhunt.game.player.ManhuntPlayer;
import net.taccy.manhunt.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.scheduler.BukkitRunnable;

import org.bukkit.World;

import java.util.Arrays;

public class CompassManager extends BukkitRunnable {

    private Manhunt pl;

    private Player target = null;
    private Location lastOverworldLocation = null;
    private Location lastNetherLocation = null;
    private Location lastEndLocation = null;

    public CompassManager(Manhunt pl) {
        this.pl = pl;
    }

    public void giveCompass(Player target) {
        target.getInventory().setItem(Manhunt.COMPASS_SLOT,
                new ItemBuilder(Material.COMPASS)
                        .setName("&6&l" + Manhunt.TARGET_NAME + " Tracker")
                        .setLore(Arrays.asList(
                                "This compass will always point to",
                                Manhunt.TARGET_NAME + "'s current location!"))
                        .toItemStack());
    }

    @Override
    public void run() {
        if (target == null || !target.isOnline()) {
            target = Bukkit.getPlayer(Manhunt.TARGET_NAME);
            return;
        }

        World.Environment targetEnv = target.getWorld().getEnvironment();
        if (targetEnv == World.Environment.NORMAL) {
            lastOverworldLocation = target.getLocation();
        } else if (targetEnv == World.Environment.NETHER) {
            lastNetherLocation = target.getLocation();
        } else if (targetEnv == World.Environment.THE_END) {
            lastEndLocation = target.getLocation();
        }

        for (ManhuntPlayer mp : pl.getGame().getOnlinePlayers()) {
            ItemStack item = mp.getPlayer().getInventory().getItem(8);
            if (item == null || item.getType() != Material.COMPASS) return;

            CompassMeta meta = (CompassMeta) item.getItemMeta();

            World.Environment playerEnv = mp.getPlayer().getWorld().getEnvironment();
            if (playerEnv == World.Environment.NORMAL) {
                if (lastOverworldLocation == null) continue;
                if (meta.getLodestone() != null) giveCompass(mp.getPlayer());
                mp.getPlayer().setCompassTarget(lastOverworldLocation);
            } else if (playerEnv == World.Environment.NETHER) {
                meta.setLodestoneTracked(false);
                meta.setLodestone(lastNetherLocation);
                item.setItemMeta(meta);
            } else if (playerEnv == World.Environment.THE_END) {
                if (lastEndLocation == null) continue;
                mp.getPlayer().setCompassTarget(lastEndLocation);
            }
        }
    }

}
