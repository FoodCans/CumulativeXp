package dev.foodcans.cumulativexp.placeholderapi;

import dev.foodcans.cumulativexp.manager.PlayerManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class XpPlaceholder extends PlaceholderExpansion
{
    private PlayerManager playerManager;

    public XpPlaceholder(PlayerManager playerManager)
    {
        this.playerManager = playerManager;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params)
    {
        UUID uuid = player.getUniqueId();
        return String.valueOf(playerManager.getXp(uuid));
    }

    @Override
    public @NotNull String getIdentifier()
    {
        return "cxp";
    }

    @Override
    public @NotNull String getAuthor()
    {
        return "FoodCans";
    }

    @Override
    public @NotNull String getVersion()
    {
        return "1.0.0";
    }

    @Override
    public boolean persist()
    {
        return true;
    }
}
