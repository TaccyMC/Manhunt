package net.taccy.manhunt.managers;

import net.taccy.manhunt.utils.Colorize;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Freezer {

    public static void freeze(Player target, boolean clearInv) {
        target.setGameMode(GameMode.ADVENTURE);
        target.setWalkSpeed(0);
        target.setFlySpeed(0);
        target.setAllowFlight(true);
        target.setFlying(true);

        target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 250, true, false));

        if (clearInv) { target.getInventory().clear(); }
    }

    public static void unfreeze(Player target, boolean resets) {
        target.setFlySpeed(0.1F);
        target.setWalkSpeed(0.2F);
        target.setAllowFlight(false);
        target.setFlying(false);
        target.setGameMode(GameMode.SURVIVAL);

        for (PotionEffect effect : target.getActivePotionEffects()) {
            target.removePotionEffect(effect.getType());
        }

        if (resets) {
            target.setFoodLevel(20);
            target.setHealth(20);
            target.setSaturation(20);
        }
    }

}
