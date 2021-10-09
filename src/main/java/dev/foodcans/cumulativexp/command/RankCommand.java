package dev.foodcans.cumulativexp.command;

import dev.foodcans.cumulativexp.lang.Lang;
import dev.foodcans.cumulativexp.manager.PlayerManager;
import dev.foodcans.cumulativexp.util.TaskUtil;
import dev.foodcans.cumulativexp.util.UUIDFetcher;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.UUID;

public class RankCommand extends CXPCommand
{
    public RankCommand(PlayerManager playerManager)
    {
        super("rank", "cxp.command.rank", Collections.singletonList("<player>"), playerManager);
    }

    @Override
    public void onCommand(CommandSender sender, String... args)
    {
        String name = args[0];
        TaskUtil.runAsync(() ->
        {
            UUID uuid = UUIDFetcher.getUUID(name);

            int rank = playerManager.getRank(uuid);
            Lang.RANK.sendMessage(sender, name, String.valueOf(rank));
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
