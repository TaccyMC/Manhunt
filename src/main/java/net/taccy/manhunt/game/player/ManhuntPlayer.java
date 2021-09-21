package net.taccy.manhunt.game.player;

import net.taccy.manhunt.utils.Colorize;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ManhuntPlayer {

    private UUID uuid;
    private String name;
    private String skin;
    private Player player;

    private Boolean alive;
    private Boolean frozen;

    public ManhuntPlayer(UUID uuid, String name, String skin, Boolean alive, Boolean frozen) {
        this.uuid = uuid;
        this.name = name;
        this.skin = skin;

        this.alive = alive;
        this.frozen = frozen;
    }

    public ManhuntPlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.player = player;
    }

    public UUID getUUID() {
        return uuid;
    }
    public String getName() {
        return name;
    }
    public String getSkin() {
        return skin;
    }
    public Player getPlayer() {
        return player;
    }
    public Boolean isAlive() {
        return alive;
    }
    public Boolean isFrozen() {
        return frozen;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSkin(String skin) {
        this.skin = skin;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setAlive(Boolean alive) {
        this.alive = alive;
    }
    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public void sendMessage(String message) {
        player.sendMessage(Colorize.color(message));
    }

}
