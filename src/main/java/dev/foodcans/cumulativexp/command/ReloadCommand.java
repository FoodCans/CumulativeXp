package dev.foodcans.cumulativexp.command;

import dev.foodcans.cumulativexp.CumulativeXp;
import dev.foodcans.cumulativexp.lang.Lang;
import dev.foodcans.cumulativexp.manager.PlayerManager;
import dev.foodcans.cumulativexp.util.Config;
import org.bukkit.command.CommandSender;

import java.util.Collections;

public class ReloadCommand extends CXPCommand
{
    public ReloadCommand(PlayerManager playerManager)
    {
        super("reload", "cxp.command.reload", Collections.emptyList(), playerManager);
    }

    @Override
    public void onCommand(CommandSender sender, String... args)
    {
        CumulativeXp.getInstance().reloadConfig();
        Config.loadConfig(CumulativeXp.getInstance().getConfig());
        Lang.CONFIG_RELOADED.sendMessage(sender);
    }

    @Override
    public int getMinArgs()
    {
        return 0;
    }

    @Override
    public int getMaxArgs()
    {
        return 0;
    }

    @Override
    public boolean allowConsoleSender()
    {
        return true;
    }
}
