package dev.foodcans.cumulativexp.command;

import dev.foodcans.cumulativexp.lang.Lang;
import dev.foodcans.cumulativexp.manager.PlayerManager;
import dev.foodcans.cumulativexp.util.TaskUtil;
import dev.foodcans.cumulativexp.util.UUIDFetcher;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.UUID;

public class LevelCommand extends CXPCommand
{
    public LevelCommand(PlayerManager playerManager)
    {
        super("level", "cxp.command.level", Collections.singletonList("<player>"), playerManager);
    }

    @Override
    public void onCommand(CommandSender sender, String... args)
    {
        String name = args[0];
        TaskUtil.runAsync(() ->
        {
            UUID uuid = UUIDFetcher.getUUID(name);

            int level = playerManager.getLevel(uuid);
            Lang.EARNED_LEVEL.sendMessage(sender, name, String.valueOf(level));
        });
    }

    @Override
    public int getMinArgs()
    {
        return 1;
    }

    @Override
    public int getMaxArgs()
    {
        return 1;
    }

    @Override
    public boolean allowConsoleSender()
    {
        return true;
    }
}
