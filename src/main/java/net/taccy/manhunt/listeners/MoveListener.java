package net.taccy.manhunt.listeners;

import net.taccy.manhunt.Manhunt;
import net.taccy.manhunt.game.player.ManhuntPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    private Manhunt pl;

    public MoveListener(Manhunt pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        ManhuntPlayer mp = pl.getGame().getOnlinePlayer(e.getPlayer().getUniqueId());
        if (mp == null) return;
        if (mp.isFrozen() == null) return;
        if (!mp.isFrozen()) return;
        if (e.getFrom().getBlockX() != e.getTo().getBlockX() ||
            e.getFrom().getBlockY() != e.getTo().getBlockY() ||
            e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
            e.setCancelled(true);
            e.getPlayer().setAllowFlight(true);
            e.getPlayer().setFlying(true);
        }
    }

}
