package net.taccy.manhunt.game.states;

import net.taccy.manhunt.Manhunt;
import net.taccy.manhunt.game.Game;
import net.taccy.manhunt.game.GameState;
import net.taccy.manhunt.game.GameStateType;
import net.taccy.manhunt.game.player.ManhuntPlayer;
import net.taccy.manhunt.utils.Colorize;
import net.taccy.manhunt.utils.GenUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.World;

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

        for (ManhuntPlayer mp : game.getOnlinePlayers()) {
            game.unfreeze(mp, true);
            pl.cpm.giveCompass(mp.getPlayer());
            mp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 4, 255, true, false));
            mp.setAlive(true);
            Bukkit.getLogger().log(Level.INFO, "set " + mp.getName() + " alive to " + mp.isAlive());
        }
    }

    @Override
    public void onPlayerJoin(ManhuntPlayer mp) {
        if (mp.isAlive() == null) {
            // new player
            mp.sendMessage(Colorize.color("&6> &fWelcome to &6ThumbTac's Manhunt!"));
            mp.sendMessage(Colorize.color("&d> &fYou've joined mid game!"));
            mp.sendMessage(Colorize.color("&b[!] &7Tip: Use &b/manhunt help &7if you need assistance."));
            Bukkit.getLogger().log(Level.INFO, mp.getName() + " joined mid game");

            int x = game.getWorld().getSpawnLocation().getBlockX() + GenUtils.generateRandom(-30, 30);
            int z = game.getWorld().getSpawnLocation().getBlockZ() + GenUtils.generateRandom(-30, 30);
            int y = game.getWorld().getHighestBlockAt(x, z).getY() + 1; // + 1 so not in floor

            mp.getPlayer().teleport(new Location(game.getWorld(), x, y, z, 0, 0));
            mp.setAlive(true);
            game.unfreeze(mp, true);
            pl.cpm.giveCompass(mp.getPlayer());
        } else if (mp.isAlive()) {
            // restore player
            mp.sendMessage(Colorize.color("&6> &fYou've rejoined &6ThumbTac's Manhunt!"));
            mp.sendMessage(Colorize.color("&d> &fYou are currently &dalive."));
        } else {
            // restore spectator
            mp.sendMessage(Colorize.color("&6> &fYou've rejoined &6ThumbTac's Manhunt!"));
            mp.sendMessage(Colorize.color("&d> &fYou are currently &da spectator."));
            mp.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }

    @Override
    public void onPlayerLeave(ManhuntPlayer mp) {

    }

    @EventHandler
    public void onKill(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        if (!(p.getHealth() - e.getFinalDamage() <= 0)) return;
        ManhuntPlayer mp = game.getOnlinePlayer(e.getEntity().getUniqueId());
        if (mp == null) return;
        e.setCancelled(true);
        game.kill(null, mp);
    }

    @Override
    public void handleTick() {

    }

    @Override
    public GameStateType getType() {
        return GameStateType.ACTIVE;
    }

    private String colorizeKillMessage(String string) {
        StringBuilder newMessage = new StringBuilder();
        boolean first = true;
        for (String word : string.split(" ")) {
            if (Bukkit.getPlayer(word) != null) {
                if (first) {
                    newMessage.append("&c").append(word).append(" &f");
                    first = false;
                } else {
                    newMessage.append("&a").append(word).append(" &f");
                }
            } else {
                newMessage.append(word).append(" ");
            }
        }
        return Colorize.color(newMessage.toString());
    }

}
