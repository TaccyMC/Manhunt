package net.taccy.manhunt.game;

import net.taccy.manhunt.Manhunt;
import net.taccy.manhunt.game.player.ManhuntPlayer;
import net.taccy.manhunt.game.states.WaitingGameState;
import net.taccy.manhunt.managers.Freezer;
import net.taccy.manhunt.utils.Colorize;
import net.taccy.manhunt.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public class Game {

    private Manhunt pl;
    private Boolean paused = false;
    private GameState state;
    private World world;
    private int timeLeft = 0;

    private List<ManhuntPlayer> onlinePlayers = new ArrayList<>();
    private List<ManhuntPlayer> offlinePlayers = new ArrayList<>();

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
        ManhuntPlayer mp = getOfflinePlayer(p.getUniqueId());
        if (mp == null) {
            mp = new ManhuntPlayer(p);
            onlinePlayers.add(mp);
        } else {
            offlinePlayers.remove(mp);
            onlinePlayers.add(mp);
            mp.setName(p.getName());
            mp.setPlayer(p);
            // todo: set skin (grab from Mojang in separate thread)
        }
        state.onPlayerJoin(mp);
        if (mp.isAlive() != null) {
            broadcastMessage("&a+ &f" + mp.getName());
        }
    }

    public void leave(Player p) {
        ManhuntPlayer mp = getOnlinePlayer(p.getUniqueId());
        if (mp == null) return;
        onlinePlayers.remove(mp);
        offlinePlayers.add(mp);
        state.onPlayerLeave(mp);
        unfreeze(mp, false);
        mp.setPlayer(null);
        if (mp.isAlive() != null) {
            broadcastMessage("&c- &f" + mp.getName());
        }
    }

    public void kill(ManhuntPlayer killer, ManhuntPlayer killed) {
        killed.setAlive(false);
        killed.getPlayer().setGameMode(GameMode.SPECTATOR);
        dropItems(killed.getPlayer());
        killed.getPlayer().getInventory().clear();
        killed.getPlayer().getWorld().strikeLightningEffect(killed.getPlayer().getLocation());
        killed.getPlayer().sendTitle(Colorize.color("&cYou died!"), "You're now a spectator.", 0, 50, 50);
        String killMessage = "&c> &f";
        if (killer == null) {
            killMessage += killed.getName() + " has died!";
        } else {
            killMessage += killer.getName() + " has killed " + killed.getName();
        }
        broadcastMessage(Colorize.color(killMessage));
    }

    public void freeze(ManhuntPlayer mp, boolean clearInv) {
        mp.setFrozen(true);
        Freezer.freeze(mp.getPlayer(), clearInv);
    }

    public void unfreeze(ManhuntPlayer mp,boolean resets) {
        mp.setFrozen(false);
        Freezer.unfreeze(mp.getPlayer(), resets);
    }

    public void tickEvent() {
        if (!paused) {
            state.handleTick();
            if (timeLeft > 0) {
                timeLeft--;
            }
        }
    }

    public ManhuntPlayer getOnlinePlayer(UUID uuid) {
        for (ManhuntPlayer mp : onlinePlayers) {
            if (mp.getUUID().compareTo(uuid) == 0) {
                return mp;
            }
        }
        return null;
    }

    public ManhuntPlayer getOfflinePlayer(UUID uuid) {
        for (ManhuntPlayer mp : offlinePlayers) {
            if (mp.getUUID().compareTo(uuid) == 0) {
                return mp;
            }
        }
        return null;
    }

    public Boolean isOnline(ManhuntPlayer mp) {
        if (getOnlinePlayer(mp.getUUID()) != null) return true;
        if (getOfflinePlayer(mp.getUUID()) != null) return false;
        return null;
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

    private void dropItems(Player player) {
        for (ItemStack item : player.getInventory()) {
            if (item == null) continue;
            if (item.getType() == Material.COMPASS) continue;
            player.getWorld().dropItem(player.getLocation(), item);
        }
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getTimeLeft() {
        return timeLeft;
    }
    public World getWorld() {
        return world;
    }
    public GameState getState() {
        return state;
    }
    public List<ManhuntPlayer> getOnlinePlayers() {
        return onlinePlayers;
    }

}
