package dev.foodcans.cumulativexp.manager;

import java.util.UUID;

public class PlayerData
{
    private final UUID uuid;
    private int xp;
    private int level;

    public PlayerData(UUID uuid, int xp, int level)
    {
        this.uuid = uuid;
        this.xp = xp;
        this.level = level;
    }

    public UUID getUuid()
    {
        return this.uuid;
    }

    public int getXp()
    {
        return this.xp;
    }

    public void setXp(int xp)
    {
        this.xp = xp;
    }

    public int getLevel()
    {
        return this.level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }
}
