package dev.foodcans.cumulativexp.placeholderapi;

import dev.foodcans.cumulativexp.manager.PlayerManager;
import dev.foodcans.cumulativexp.util.NameFetcher;
import dev.foodcans.cumulativexp.util.UUIDFetcher;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CxpPlaceholder extends PlaceholderExpansion
{
    private final PlayerManager playerManager;

    public CxpPlaceholder(PlayerManager playerManager)
    {
        this.playerManager = playerManager;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params)
    {
        if (params.startsWith("rank_"))
        {
            String playerName = params.replace("rank_", "");
            UUID uuid = UUIDFetcher.getUUID(playerName);
            int rank = playerManager.getRank(uuid);
            return rank == -1 ? "Unranked" : String.valueOf(rank);
        } else if (params.startsWith("xp_"))
        {
            String playerName = params.replace("xp_", "");
            UUID uuid = UUIDFetcher.getUUID(playerName);
            return String.valueOf(playerManager.getXp(uuid));
        } else if (params.startsWith("ranktop_"))
        {
            String posStr = params.replace("ranktop_", "");
            if (isInt(posStr))
            {
                int pos = Integer.parseInt(posStr);
                try
                {
                    UUID uuid = playerManager.getUuidByIndex(pos - 1);
                    return NameFetcher.getName(uuid);
                } catch (IndexOutOfBoundsException e)
                {
                    return "No player at this rank";
                }
            }
        } else if (params.startsWith("xptop_"))
        {
            String posStr = params.replace("xptop_", "");
            if (isInt(posStr))
            {
                int pos = Integer.parseInt(posStr);
                try
                {
                    UUID uuid = playerManager.getUuidByIndex(pos - 1);
                    return String.valueOf(playerManager.getXp(uuid));
                } catch (IndexOutOfBoundsException e)
                {
                    return "No player at this rank";
                }
            }
        }
        return null;
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

    private boolean isInt(String input)
    {
        try
        {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e)
        {
            return false;
        }
    }
}
