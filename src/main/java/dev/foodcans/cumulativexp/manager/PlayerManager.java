package dev.foodcans.cumulativexp.manager;

import dev.foodcans.cumulativexp.database.DatabaseManager;
import dev.foodcans.cumulativexp.util.TaskUtil;

import java.util.*;

public class PlayerManager
{
    private volatile Map<UUID, Integer> xpMap;
    private List<UUID> ranks;

    private final DatabaseManager databaseManager;

    public PlayerManager(DatabaseManager databaseManager)
    {
        this.xpMap = new HashMap<>();
        this.ranks = new ArrayList<>();
        this.databaseManager = databaseManager;
        loadData();
    }

    private void loadData()
    {
        Set<String> uuids = databaseManager.getUuids();
        for (String uuid : uuids)
        {
            int xp = databaseManager.getTotalXp(uuid);
            xpMap.put(UUID.fromString(uuid), xp);
            ranks.add(UUID.fromString(uuid));
        }
        sortRanks();
    }

    public void addXp(UUID uuid, int amount)
    {
        int xp = xpMap.getOrDefault(uuid, 0);
        xpMap.put(uuid, xp + amount);
        if (!ranks.contains(uuid))
        {
            ranks.add(uuid);
        }

        sortRanks();
        TaskUtil.runAsync(() -> databaseManager.addXp(uuid.toString(), amount));
    }

    public int getXp(UUID uuid)
    {
        return xpMap.getOrDefault(uuid, 0);
    }

    public void sortRanks()
    {
        ranks.sort((uuid1, uuid2) ->
        {
            int xp1 = xpMap.getOrDefault(uuid1, 0);
            int xp2 = xpMap.getOrDefault(uuid2, 0);

            return Integer.compare(xp2, xp1);
        });
    }

    public int getRank(UUID uuid)
    {
        return ranks.contains(uuid) ? ranks.indexOf(uuid) + 1: -1;
    }

    public UUID getUuidByIndex(int index)
    {
        return ranks.get(index);
    }

    public int getTotalPlayers()
    {
        return ranks.size();
    }

    public void removePlayer(UUID uuid)
    {
        ranks.remove(uuid);
        xpMap.remove(uuid);
        TaskUtil.runAsync(() -> databaseManager.removeUuid(uuid.toString()));
    }
}
