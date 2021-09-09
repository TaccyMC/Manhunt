package net.taccy.manhunt.game.states;

import net.taccy.manhunt.Manhunt;
import net.taccy.manhunt.game.Game;
import net.taccy.manhunt.game.GameState;
import net.taccy.manhunt.game.GameStateType;
import net.taccy.manhunt.utils.MessageUtil;

public class WaitingGameState extends GameState {

    public WaitingGameState(Game game, Manhunt pl) {
        super(game, pl);
        game.broadcastMessage("waiting started");
    }

    @Override
    public void handleTick() {

    }

    @Override
    public GameStateType getType() {
        return GameStateType.WAITING;
    }

}
