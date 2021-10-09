package dev.foodcans.cumulativexp.command;

import dev.foodcans.cumulativexp.lang.Lang;
import dev.foodcans.cumulativexp.manager.PlayerManager;
import dev.foodcans.cumulativexp.util.TaskUtil;
import dev.foodcans.cumulativexp.util.UUIDFetcher;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.UUID;

public class XPCommand extends CXPCommand
{
    public XPCommand(PlayerManager playerManager)
    {
        super("xp", "cxp.command.xp", Collections.singletonList("<player>"), playerManager);
    }

    @Override
    public void onCommand(CommandSender sender, String... args)
    {
        String name = args[0];
        TaskUtil.runAsync(() ->
        {
            UUID uuid = UUIDFetcher.getUUID(name);

            int xp = playerManager.getXp(uuid);
            Lang.EARNED_XP.sendMessage(sender, name, String.valueOf(xp));
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
