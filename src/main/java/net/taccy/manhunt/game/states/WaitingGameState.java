package net.taccy.manhunt.game.states;

import net.taccy.manhunt.Manhunt;
import net.taccy.manhunt.game.Game;
import net.taccy.manhunt.game.GameState;
import net.taccy.manhunt.game.GameStateType;
import net.taccy.manhunt.utils.MessageUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class WaitingGameState extends GameState {

    public WaitingGameState(Game game, Manhunt pl) {
        super(game, pl);
        game.broadcastMessage("waiting started");
    }

    @Override
    public void handleTick() {

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!(e.getPlayer().getWorld().getName().equals(Manhunt.WORLD_NAME))) return;
        game.join(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onChangeWorlds(PlayerChangedWorldEvent e) {
        if (!(e.getFrom().getName().equals(Manhunt.WORLD_NAME))) return;
        game.leave(e.getPlayer().getUniqueId());
    }

    @Override
    public GameStateType getType() {
        return GameStateType.WAITING;
    }

}
