package dev.foodcans.cumulativexp.util;

import dev.foodcans.cumulativexp.CumulativeXp;
import org.bukkit.Bukkit;

public final class TaskUtil
{
    public static void runSync(Runnable runnable)
    {
        Bukkit.getScheduler().runTask(CumulativeXp.getInstance(), runnable);
    }

    public static void runAsync(Runnable runnable)
    {
        Bukkit.getScheduler().runTaskAsynchronously(CumulativeXp.getInstance(), runnable);
    }
}
