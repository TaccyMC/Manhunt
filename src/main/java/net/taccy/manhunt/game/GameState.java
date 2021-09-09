package net.taccy.manhunt.game;

import net.taccy.manhunt.Manhunt;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class GameState implements Listener {

    public void onEnable(Manhunt pl) { pl.getServer().getPluginManager().registerEvents(this, pl); }
    public void onDisable(Manhunt pl) {
        HandlerList.unregisterAll(this);
    }

    public abstract void handleTick();
    public abstract GameStateType getType();

}
