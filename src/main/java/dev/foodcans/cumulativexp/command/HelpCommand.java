package dev.foodcans.cumulativexp.command;

import dev.foodcans.cumulativexp.lang.Lang;
import dev.foodcans.cumulativexp.manager.PlayerManager;
import org.bukkit.command.CommandSender;

import java.util.Collections;

public class HelpCommand extends CXPCommand
{
    public HelpCommand(PlayerManager playerManager)
    {
        super("help", "cxp.command.help", Collections.emptyList(), playerManager);
    }

    @Override
    public void onCommand(CommandSender sender, String... args)
    {
        String commands = "[xp, rank, top, reload, help]";
        Lang.HELP.sendMessage(sender, commands);
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
