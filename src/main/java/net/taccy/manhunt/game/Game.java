package net.taccy.manhunt.game;

import net.taccy.manhunt.Manhunt;
import net.taccy.manhunt.game.states.WaitingGameState;
import net.taccy.manhunt.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class Game {

    private Manhunt pl;
    private Boolean paused = false;
    private GameState state;

    public Game(Manhunt pl) {
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

}
