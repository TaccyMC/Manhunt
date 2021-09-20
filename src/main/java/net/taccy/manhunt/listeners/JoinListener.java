package net.taccy.manhunt.listeners;

import net.taccy.manhunt.Manhunt;
import net.taccy.manhunt.managers.Freezer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.logging.Level;

public class JoinListener implements Listener {

    private Manhunt pl;

    public JoinListener(Manhunt pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!(e.getPlayer().getWorld().getUID() == Manhunt.WORLD_UUID)) return;
        pl.getGame().join(e.getPlayer());
    }

    @EventHandler
    public void onChangeWorlds(PlayerChangedWorldEvent e) {
        if (!(e.getFrom().getUID() == Manhunt.WORLD_UUID)) return;
        pl.getGame().leave(e.getPlayer());
        pl.getGame().unfreeze(e.getPlayer(), false);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (!(e.getPlayer().getWorld().getUID() == Manhunt.WORLD_UUID)) return;
        pl.getGame().leave(e.getPlayer());
        pl.getGame().unfreeze(e.getPlayer(), false);
    }

}
