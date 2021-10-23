package dev.foodcans.cumulativexp.command;

import dev.foodcans.cumulativexp.lang.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class CommandManager implements TabExecutor
{
    private Set<CXPCommand> commands;

    public CommandManager()
    {
        this.commands = new HashSet<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if (args.length == 0)
        {
            Lang.LIST_COMMANDS.sendMessage(sender);
            return true;
        }

        String subCommand = args[0];

        // Remove first arg (/cxp)
        args = Arrays.copyOfRange(args, 1, args.length);

        for (CXPCommand command : commands)
        {
            if (command.getName().equalsIgnoreCase(subCommand))
            {
                if (!permissionCheck(sender, command.getPermission(), true))
                {
                    return true;
                }
                if (!argsLengthCheck(command, sender, args))
                {
                    return true;
                }
                if (!consoleSenderCheck(command, sender))
                {
                    return true;
                }
                command.onCommand(sender, args);
                return true;
            }
        }

        Lang.COMMAND_NOT_FOUND.sendMessage(sender);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args)
    {
        List<String> completions = new ArrayList<>();
        if (args.length == 1)
        {
            String arg = args[0].toLowerCase();
            for (CXPCommand command : commands)
            {
                if (command.getName().toLowerCase().startsWith(arg) && permissionCheck(sender, command.getPermission(), true))
                {
                    completions.add(command.getName());
                }
            }
            return completions;
        }
        return null;
    }

    public void registerCommand(CXPCommand... commands)
    {
        this.commands.addAll(Arrays.asList(commands));
    }

    private boolean permissionCheck(CommandSender sender, String permission, boolean verbose)
    {
        if (sender != null)
        {
            if (sender.hasPermission(permission))
            {
                return true;
            } else
            {
                if (verbose)
                {
                    Lang.NO_PERMISSION_COMMAND.sendMessage(sender, permission);
                }
                return false;
            }
        }
        return true;
    }

    private boolean argsLengthCheck(CXPCommand cmd, CommandSender sender, String... args)
    {
        int min = cmd.getMinArgs();
        int max = cmd.getMaxArgs();
        StringBuilder argsString = new StringBuilder();
        for (String arg : cmd.getArgs())
        {
            argsString.append(" ").append(arg);
        }

        if (args.length < min)
        {
            Lang.NOT_ENOUGH_ARGS.sendMessage(sender, argsString.toString());
            return false;
        } else if (args.length > max && max > 0)
        {
            Lang.TOO_MANY_ARGS.sendMessage(sender, argsString.toString());
            return false;
        }

        return true;
    }

    private boolean consoleSenderCheck(CXPCommand cmd, CommandSender sender)
    {
        if (sender instanceof ConsoleCommandSender && !cmd.allowConsoleSender())
        {
            Lang.COMMAND_ONLY_RUN_BY_PLAYERS.sendMessage(sender);
            return false;
        }

        return true;
    }
}
