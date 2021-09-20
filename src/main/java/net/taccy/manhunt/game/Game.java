package net.taccy.manhunt.game;

import net.taccy.manhunt.Manhunt;
import net.taccy.manhunt.game.states.WaitingGameState;
import net.taccy.manhunt.managers.Freezer;
import net.taccy.manhunt.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class Game {

    private Manhunt pl;
    private Boolean paused = false;
    private GameState state;
    private World world;
    private int timeLeft = 0;

    private List<Player> players = new ArrayList<>();

    public Game(Manhunt pl) {
        world = Bukkit.getWorld(Manhunt.WORLD_NAME);

        this.pl = pl;
        broadcastMessage("Game has been created");

        setGameState(new WaitingGameState(this, pl));
    }

    public void setGameState(GameState state) {
        if (this.state != null) {
            this.state.onDisable(pl);
        }

        this.state = state;
        state.onEnable(pl);

        MessageUtil.log(Level.INFO, "Game state update to " + state.getType().getDisplayName());
    }

    public void join(Player p) {
        players.add(p);
        Object alive = pl.cm.get(p.getUniqueId(), "alive");
        if (alive == null) {
            state.onPlayerJoin(p, null);
            return;
        }

        state.onPlayerJoin(p, (boolean) alive);
    }

    public void leave(Player p) {
        players.remove(p);
        p.sendMessage("You have left the manhunt game.");
        state.onPlayerLeave(p);
    }

    public void freeze(Player p, boolean clearInv) {
        pl.cm.set(p.getUniqueId(), "frozen", true);
        Freezer.freeze(p, clearInv);
    }

    public void unfreeze(Player p,boolean resets) {
        pl.cm.set(p.getUniqueId(), "frozen", false);
        Freezer.unfreeze(p, resets);
    }

    public void tickEvent() {
        if (!paused) {
            state.handleTick();
            if (timeLeft > 0) {
                timeLeft--;
            }
        }
    }

    public void broadcastMessage(String message) {
        Bukkit.getOnlinePlayers().forEach((Player player) -> {
            player.sendMessage(MessageUtil.color(message));
        });
    }

    public void broadcastTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        Bukkit.getOnlinePlayers().forEach((Player player) -> {
            player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
        });
    }

    public void broadcastActionbar(String message) {
        Bukkit.getOnlinePlayers().forEach((Player player) -> {
            MessageUtil.sendActionBar(player, message);
        });
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public Player getPlayer(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    public World getWorld() {
        return world;
    }

    public List<Player> getPlayers() {
        return players;
    }

}
