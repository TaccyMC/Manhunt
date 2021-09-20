package net.taccy.manhunt.game.states;

import net.taccy.manhunt.Manhunt;
import net.taccy.manhunt.game.Game;
import net.taccy.manhunt.game.GameState;
import net.taccy.manhunt.game.GameStateType;
import net.taccy.manhunt.utils.Colorize;
import net.taccy.manhunt.utils.GenUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class GraceGameState extends GameState {

    public GraceGameState(Game game, Manhunt pl) {
        super(game, pl);
    }

    @Override
    public void onEnable(Manhunt pl) {
        super.onEnable(pl);
        game.setTimeLeft(20);
        game.broadcastMessage("&a> &fStarted grace period!");
    }

    @Override
    public void onPlayerJoin(Player player, Boolean alive) {
        if (alive == null) {
            // new player
            player.sendMessage(Colorize.color("&6> &fWelcome to &6ThumbTac's Manhunt!"));
            player.sendMessage(Colorize.color("&a> &fThe game will start shortly!"));
            player.sendMessage(Colorize.color("&b[!] &7Tip: Use &b/manhunt help &7if you need assistance."));
            Bukkit.getLogger().log(Level.INFO, player.getName() + " joined and is waiting.");

            int x = game.getWorld().getSpawnLocation().getBlockX() + GenUtils.generateRandom(-30, 30);
            int z = game.getWorld().getSpawnLocation().getBlockZ() + GenUtils.generateRandom(-30, 30);
            int y = game.getWorld().getHighestBlockAt(x, z).getY() + 30;

            player.teleport(new Location(game.getWorld(), x, y, z, 0, 0));
            game.freeze(player, true);
        }
    }

    @Override
    public void onPlayerLeave(Player player) {
        player.sendMessage("waiting state leave!!!");
        Bukkit.getLogger().log(Level.INFO, player.getName() + " left the waiting world.");
    }

    @Override
    public void handleTick() {
        game.broadcastActionbar("&d" + game.getTimeLeft() + " &fseconds remaining");

        if (game.getTimeLeft() == 0) {

        }
    }

    @Override
    public GameStateType getType() {
        return GameStateType.GRACE;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        if (!(e.getEntity() instanceof Player)) return;

        if (!(e.getDamager().getWorld().getUID() == Manhunt.WORLD_UUID)) return;
        e.setCancelled(true);
    }

}
