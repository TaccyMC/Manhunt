package net.taccy.manhunt.game.states;

import net.taccy.manhunt.Manhunt;
import net.taccy.manhunt.game.Game;
import net.taccy.manhunt.game.GameState;
import net.taccy.manhunt.game.GameStateType;
import net.taccy.manhunt.game.player.ManhuntPlayer;
import net.taccy.manhunt.utils.Colorize;
import net.taccy.manhunt.utils.GenUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.logging.Level;

public class WaitingGameState extends GameState {

    public WaitingGameState(Game game, Manhunt pl) {
        super(game, pl);
    }

    @Override
    public void onEnable(Manhunt pl) {
        super.onEnable(pl);
        game.broadcastMessage("waiting started");
    }

    @Override
    public void handleTick() {

    }

    @Override
    public void onPlayerJoin(ManhuntPlayer mp) {
        if (mp.isAlive() == null) {
            // new player
            mp.sendMessage(Colorize.color("&6> &fWelcome to &6ThumbTac's Manhunt!"));
            mp.sendMessage(Colorize.color("&a> &fThe game will start shortly!"));
            mp.sendMessage(Colorize.color("&b[!] &7Tip: Use &b/manhunt help &7if you need assistance."));
            Bukkit.getLogger().log(Level.INFO, mp.getName() + " joined and is waiting.");

            int x = game.getWorld().getSpawnLocation().getBlockX() + GenUtils.generateRandom(-30, 30);
            int z = game.getWorld().getSpawnLocation().getBlockZ() + GenUtils.generateRandom(-30, 30);
            int y = game.getWorld().getHighestBlockAt(x, z).getY() + 30;

            mp.getPlayer().teleport(new Location(game.getWorld(), x, y, z, 0, 0));
            game.freeze(mp, true);
        } else if (mp.isAlive()) {
            // restore player
            game.setGameState(new ActiveGameState(game, pl));
            game.getState().onPlayerJoin(mp);
        } else {
            // restore spectator
            game.setGameState(new ActiveGameState(game, pl));
            game.getState().onPlayerJoin(mp);
        }
    }

    @Override
    public void onPlayerLeave(ManhuntPlayer mp) {
        Bukkit.getLogger().log(Level.INFO, mp.getName() + " left the waiting world.");
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        if (!(e.getEntity() instanceof Player)) return;

        if (!(e.getDamager().getWorld().getUID() == Manhunt.WORLD_UUID)) return;
        e.setCancelled(true);
    }

    @Override
    public GameStateType getType() {
        return GameStateType.WAITING;
    }

}
