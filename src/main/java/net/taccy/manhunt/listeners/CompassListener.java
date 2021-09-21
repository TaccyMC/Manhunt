package net.taccy.manhunt.listeners;

import net.taccy.manhunt.Manhunt;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class CompassListener implements Listener {

    private Manhunt pl;

    public CompassListener(Manhunt pl) {
        this.pl = pl;
    }

    // cancel dropping compass
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (!(e.getItemDrop().getItemStack().getType() == Material.COMPASS)) return;
        e.setCancelled(true);
    }

    // cancel moving compass in inventory
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getSlot() == Manhunt.COMPASS_SLOT)) return;
        e.setCancelled(true);
    }

    // cancel swapping compass to offhand
    @EventHandler
    public void swapItem(PlayerSwapHandItemsEvent e) {
        if (!(e.getPlayer().getInventory().getHeldItemSlot() == Manhunt.COMPASS_SLOT)) return;
        e.setCancelled(true);
    }

}
