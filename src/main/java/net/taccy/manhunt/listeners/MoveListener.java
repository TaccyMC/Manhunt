package net.taccy.manhunt.listeners;

import net.taccy.manhunt.Manhunt;
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
        if (!(pl.getGame().getPlayers().contains(e.getPlayer()))) return;
        if (!(e.getPlayer().getWorld().getUID() == Manhunt.WORLD_UUID)) return;
        Object frozen = pl.cm.get(e.getPlayer().getUniqueId(), "frozen");
        if (frozen == null) return;
        if (!((boolean) frozen)) return;
        if (e.getFrom().getBlockX() != e.getTo().getBlockX() ||
            e.getFrom().getBlockY() != e.getTo().getBlockY() ||
            e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
            e.setCancelled(true);
            e.getPlayer().setAllowFlight(true);
            e.getPlayer().setFlying(true);
        }
    }

}
