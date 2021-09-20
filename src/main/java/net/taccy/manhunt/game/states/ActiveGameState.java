package net.taccy.manhunt.game.states;

import net.taccy.manhunt.Manhunt;
import net.taccy.manhunt.game.Game;
import net.taccy.manhunt.game.GameState;
import net.taccy.manhunt.game.GameStateType;
import net.taccy.manhunt.managers.Freezer;
import net.taccy.manhunt.utils.Colorize;
import net.taccy.manhunt.utils.GenUtils;
import net.taccy.manhunt.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.logging.Level;

public class ActiveGameState extends GameState {

    public ActiveGameState(Game game, Manhunt pl) {
        super(game, pl);
    }

    @Override
    public void onEnable(Manhunt pl) {
        super.onEnable(pl);
        game.setTimeLeft(20);
        game.broadcastMessage("&a> &fThe game has started!");

        for (Player p : game.getPlayers()) {
            pl.cm.set(p.getUniqueId(), "frozen", false);
            pl.cm.set(p.getUniqueId(), "alive", true);
            pl.cpm.giveCompass(p);
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 4, 255, true, false));
        }
    }

    @Override
    public void onPlayerJoin(Player player, Boolean alive) {
        if (alive == null) {
            // new player
            player.sendMessage(Colorize.color("&6> &fWelcome to &6ThumbTac's Manhunt!"));
            player.sendMessage(Colorize.color("&d> &fYou've joined mid game!"));
            player.sendMessage(Colorize.color("&b[!] &7Tip: Use &b/manhunt help &7if you need assistance."));
            Bukkit.getLogger().log(Level.INFO, player.getName() + " joined mid game");

            int x = game.getWorld().getSpawnLocation().getBlockX() + GenUtils.generateRandom(-30, 30);
            int z = game.getWorld().getSpawnLocation().getBlockZ() + GenUtils.generateRandom(-30, 30);
            int y = game.getWorld().getHighestBlockAt(x, z).getY();

            player.teleport(new Location(game.getWorld(), x, y, z, 0, 0));
            game.unfreeze(player, true);
        } else if (alive) {
            // restore player
            player.sendMessage(Colorize.color("&6> &fYou've rejoined &6ThumbTac's Manhunt!"));
            player.sendMessage(Colorize.color("&d> &fYou are currently &dalive."));
        } else {
            // restore spectator
            player.sendMessage(Colorize.color("&6> &fYou've rejoined &6ThumbTac's Manhunt!"));
            player.sendMessage(Colorize.color("&d> &fYou are currently &da spectator."));
        }
    }

    @Override
    public void onPlayerLeave(Player player) {

    }

    @Override
    public void handleTick() {

    }

    @Override
    public GameStateType getType() {
        return GameStateType.ACTIVE;
    }

}
