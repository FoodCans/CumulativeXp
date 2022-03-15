package dev.foodcans.cumulativexp.placeholderapi;

import dev.foodcans.cumulativexp.lang.Lang;
import dev.foodcans.cumulativexp.manager.PlayerManager;
import dev.foodcans.cumulativexp.util.NameFetcher;
import dev.foodcans.cumulativexp.util.UUIDFetcher;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
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
    public String onRequest(OfflinePlayer offlinePlayer, @NotNull String params)
    {
        if (offlinePlayer != null && offlinePlayer.isOnline() && offlinePlayer.getPlayer() != null)
        {
            Player player = offlinePlayer.getPlayer();
            switch (params)
            {
                case "rank":
                    return handleRank(player);
                case "xp":
                    return handleXp(player);
                case "level":
                    return handleLevel(player);
                case "status":
                    return handleStatus(player);
                default: return null;
            }
        }
        if (params.startsWith("rank_"))
        {
            return handleRank(params);
        } else if (params.startsWith("xp_"))
        {
            return handleXp(params);
        } else if (params.startsWith("level_"))
        {
            return handleLevel(params);
        } else if (params.startsWith("ranktop_"))
        {
            return handleRankTop(params);
        } else if (params.startsWith("xptop_"))
        {
            return handleXpTop(params);
        } else if (params.startsWith("leveltop_"))
        {
            return handleLevelTop(params);
        }
        return null;
    }

    private String handleRank(Player player)
    {
        int rank = playerManager.getRank(player.getUniqueId());
        return rank == -1 ? "Unranked" : String.valueOf(rank);
    }

    private String handleRank(String params)
    {
        String playerName = params.replace("rank_", "");
        if (playerName.length() == 0)
        {
            return null;
        }
        UUID uuid = UUIDFetcher.getUUID(playerName);
        int rank = playerManager.getRank(uuid);
        return rank == -1 ? "Unranked" : String.valueOf(rank);
    }

    private String handleXp(Player player)
    {
        return String.valueOf(playerManager.getXp(player.getUniqueId()));
    }

    private String handleXp(String params)
    {
        String playerName = params.replace("xp_", "");
        if (playerName.length() == 0)
        {
            return null;
        }
        UUID uuid = UUIDFetcher.getUUID(playerName);
        return String.valueOf(playerManager.getXp(uuid));
    }

    private String handleLevel(Player player)
    {
        return String.valueOf(playerManager.getLevel(player.getUniqueId()));
    }

    private String handleLevel(String params)
    {
        String playerName = params.replace("level_", "");
        if (playerName.length() == 0)
        {
            return null;
        }
        UUID uuid = UUIDFetcher.getUUID(playerName);
        return String.valueOf(playerManager.getLevel(uuid));
    }

    private String handleRankTop(String params)
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
        return null;
    }

    private String handleXpTop(String params)
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
        return null;
    }

    private String handleLevelTop(String params)
    {
        String posStr = params.replace("leveltop_", "");
        if (isInt(posStr))
        {
            int pos = Integer.parseInt(posStr);
            try
            {
                UUID uuid = playerManager.getUuidByIndex(pos - 1);
                return String.valueOf(playerManager.getLevel(uuid));
            } catch (IndexOutOfBoundsException e)
            {
                return "No player at this rank";
            }
        }
        return null;
    }

    private String handleStatus(Player player)
    {
        return player.isDead() ? Lang.DEAD_STATUS.getMessage() : Lang.ALIVE_STATUS.getMessage();
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
