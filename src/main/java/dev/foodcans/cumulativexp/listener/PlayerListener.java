package dev.foodcans.cumulativexp.listener;

import dev.foodcans.cumulativexp.manager.PlayerManager;
import dev.foodcans.cumulativexp.util.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import java.util.UUID;

public class PlayerListener implements Listener
{
    private final PlayerManager playerManager;

    public PlayerListener(PlayerManager playerManager)
    {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event)
    {
        UUID uuid = event.getPlayer().getUniqueId();
        int amount = event.getAmount();

        // If the player loses XP we don't want to do anything
        if (amount > 0)
        {
            playerManager.addXp(uuid, amount);
        }
    }

    @EventHandler
    public void onPlayerLevelChange(PlayerLevelChangeEvent event)
    {
        UUID uuid = event.getPlayer().getUniqueId();
        int change =  event.getNewLevel() - event.getOldLevel();

        // If the player loses level we don't want to do anything
        if (change > 0)
        {
            playerManager.addLevel(uuid, change);
        }
    }

    // If a player dies in a hardcore world remove them from the database
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getEntity();
        if (player.getWorld().isHardcore() && Config.REMOVE_HARDCORE)
        {
            playerManager.removePlayer(player.getUniqueId());
        }
    }
}
