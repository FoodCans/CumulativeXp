package dev.foodcans.cumulativexp.command;

import dev.foodcans.cumulativexp.lang.Lang;
import dev.foodcans.cumulativexp.manager.PlayerManager;
import dev.foodcans.cumulativexp.util.Config;
import dev.foodcans.cumulativexp.util.NameFetcher;
import dev.foodcans.cumulativexp.util.TaskUtil;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.UUID;

public class TopCommand extends CXPCommand
{
    public TopCommand(PlayerManager playerManager)
    {
        super("top", "cxp.command.top", Collections.singletonList("[page]"), playerManager);
    }

    @Override
    public void onCommand(CommandSender sender, String... args)
    {
        TaskUtil.runAsync(() ->
        {
            int totalPlayers = playerManager.getTotalPlayers();
            int perPage = Config.ENTRIES_PER_PAGE;
            int totalPages = totalPlayers < perPage ? 1 : (totalPlayers / perPage + (totalPlayers % perPage > 0 ? 1 : 0));
            int page = 1;
            if (args.length > 0 && isInt(args[0]))
            {
                page = Integer.parseInt(args[0]);
                if (page > totalPages)
                {
                    page = totalPages;
                }
            }
            int limit = totalPlayers < perPage ? totalPlayers : page == totalPages ? totalPlayers % perPage : perPage;

            Lang.TOP_HEADER.sendMessage(sender);

            for (int i = 0; i < limit; i++)
            {
                int pos = i + (page == 1 ? 0 : (page - 1) * 10);
                UUID uuid = playerManager.getUuidByIndex(pos);
                int rank = playerManager.getRank(uuid);
                int xp = playerManager.getXp(uuid);
                String name = NameFetcher.getName(uuid);

                Lang.TOP_FORMAT.sendMessage(sender, String.valueOf(rank), name, String.valueOf(xp));
            }

            Lang.TOP_FOOTER.sendMessage(sender, String.valueOf(page), String.valueOf(totalPages));
        });
    }

    @Override
    public int getMinArgs()
    {
        return 0;
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
