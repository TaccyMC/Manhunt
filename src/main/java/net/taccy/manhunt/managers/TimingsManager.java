package net.taccy.manhunt.managers;

import net.taccy.manhunt.Manhunt;
import net.taccy.manhunt.game.Game;
import org.bukkit.scheduler.BukkitRunnable;

public class TimingsManager extends BukkitRunnable {

    private Manhunt pl;

    public TimingsManager(Manhunt pl) {
        this.pl = pl;
    }

    @Override
    public void run() {
        Game game = pl.getGame();
        if (game != null) {
            pl.getGame().tickEvent();
        }
    }
}
