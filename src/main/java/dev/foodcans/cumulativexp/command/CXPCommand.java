package dev.foodcans.cumulativexp.command;

import dev.foodcans.cumulativexp.manager.PlayerManager;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class CXPCommand
{
    private String name;
    private String permission;
    private List<String> args;

    PlayerManager playerManager;

    CXPCommand(String name, String permission, List<String> args, PlayerManager playerManager)
    {
        this.name = name;
        this.permission = permission;
        this.args = args;

        this.playerManager = playerManager;
    }

    public abstract void onCommand(CommandSender sender, String... args);

    String getName()
    {
        return name;
    }

    String getPermission()
    {
        return permission;
    }

    List<String> getArgs()
    {
        return args;
    }

    public abstract int getMinArgs();

    public abstract int getMaxArgs();

    public abstract boolean allowConsoleSender();

    protected boolean isInt(String input)
    {
        try
        {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e)
        {
            return false;
        }
    }
}
