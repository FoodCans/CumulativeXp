package dev.foodcans.cumulativexp.manager;

import dev.foodcans.cumulativexp.CumulativeXp;
import dev.foodcans.cumulativexp.database.DatabaseManager;
import dev.foodcans.cumulativexp.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class PlayerManager
{
    private final Map<UUID, PlayerData> dataMap;
    private final List<UUID> ranks;

    private final DatabaseManager databaseManager;

    private BukkitTask task;

    public PlayerManager(DatabaseManager databaseManager)
    {
        this.dataMap = new HashMap<>();
        this.ranks = new ArrayList<>();
        this.databaseManager = databaseManager;
        loadData();
    }

    private void loadData()
    {
        dataMap.clear();
        ranks.clear();
        Set<String> uuids = databaseManager.getUuids();
        for (String uuid : uuids)
        {
            PlayerData playerData = databaseManager.getPlayerData(uuid);
            dataMap.put(playerData.getUuid(), playerData);
            ranks.add(playerData.getUuid());
        }
        sortRanks();

    }

    public void startTask()
    {
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(CumulativeXp.getInstance(), () ->
        {
            Map<UUID, PlayerData> dataMap = new HashMap<>();
            List<UUID> ranks = new ArrayList<>();
            Set<String> uuids = databaseManager.getUuids();
            for (String uuid : uuids)
            {
                PlayerData playerData = databaseManager.getPlayerData(uuid);
                dataMap.put(playerData.getUuid(), playerData);
                ranks.add(playerData.getUuid());
            }
            Bukkit.getScheduler().runTask(CumulativeXp.getInstance(), () ->
            {
               this.dataMap.clear();
               this.ranks.clear();
               this.dataMap.putAll(dataMap);
               this.ranks.addAll(ranks);
               sortRanks();
            });
        }, 20L, 20L);
    }

    public void stopTask()
    {
        if (task != null)
        {
            task.cancel();
            task = null;
        }
    }

    public boolean containsPlayer(UUID uuid)
    {
        return dataMap.containsKey(uuid);
    }

    public void addXp(UUID uuid, int amount)
    {
        PlayerData playerData = dataMap.getOrDefault(uuid, new PlayerData(uuid, 0, 0));
        playerData.setXp(playerData.getXp() + amount);
        dataMap.put(uuid, playerData);
        sortRanks();
        TaskUtil.runAsync(() -> databaseManager.updateXp(uuid.toString(), playerData.getXp()));
    }

    public void addLevel(UUID uuid, int amount)
    {
        PlayerData playerData = dataMap.getOrDefault(uuid, new PlayerData(uuid, 0, 0));
        playerData.setLevel(playerData.getLevel() + amount);
        dataMap.put(uuid, playerData);
        sortRanks();
        TaskUtil.runAsync(() -> databaseManager.updateLevel(uuid.toString(), playerData.getLevel()));
    }

    public int getXp(UUID uuid)
    {
        return containsPlayer(uuid) ? dataMap.get(uuid).getXp() : 0;
    }

    public int getLevel(UUID uuid)
    {
        return containsPlayer(uuid) ? dataMap.get(uuid).getLevel() : 0;
    }

    public void sortRanks()
    {
        ranks.sort((uuid1, uuid2) ->
        {
            int xp1 = dataMap.get(uuid1).getXp();
            int xp2 = dataMap.get(uuid2).getXp();

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
        dataMap.remove(uuid);
        TaskUtil.runAsync(() -> databaseManager.removeUuid(uuid.toString()));
    }
}
