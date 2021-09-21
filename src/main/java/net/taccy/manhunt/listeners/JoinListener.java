package net.taccy.manhunt.listeners;

import net.taccy.manhunt.Manhunt;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    private Manhunt pl;

    public JoinListener(Manhunt pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        pl.getGame().join(e.getPlayer());
        e.setJoinMessage(null);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        pl.getGame().leave(e.getPlayer());
        e.setQuitMessage(null);
    }

}
