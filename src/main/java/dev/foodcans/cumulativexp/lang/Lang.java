package dev.foodcans.cumulativexp.lang;

import dev.foodcans.cumulativexp.CumulativeXp;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum Lang
{
    TOP_HEADER("Top-Header", "&7Top XP Earners"),
    TOP_FORMAT("Top-Format", "&a{0}. &f{1} &a- &f{2}"),
    TOP_FOOTER("Top-Footer", "&7Page &f{0}&7/&f{1}"),

    RANK("Rank", "&8[&9CXP&8] &f{0} &7is rank &f{1}!"),
    EARNED_XP("Earned-Xp", "&8[&9CXP&8] &f{0} &7has earned a total of &f{1} &7xp!"),
    EARNED_LEVEL("Earned-Level", "&8[&9CXP&8] &f{0} &7has earned a total of &f{1} &7levels!"),
    CONFIG_RELOADED("Config-Reloaded", "&8[&9CXP&8] &7Config reloaded!"),
    HELP("Help", "&8[&9CXP&8] &7Available commands: &f/cxp {0}"),

    ALIVE_STATUS("Alive-Status", "&aAlive"),
    DEAD_STATUS("Dead-Status", "&cDead"),

    NOT_ENOUGH_ARGS("Not-Enough-Args", "&8[&cCXP&8] &7Not enough args: &f{0}"),
    TOO_MANY_ARGS("Too-Many-Args", "&8[&cCXP&8] &7Too many args: &f{0}"),
    COMMAND_ONLY_RUN_BY_PLAYERS("Command-Only-Run-By-Players", "&8[&cCXP&8] &7This command can only be run by players."),
    LIST_COMMANDS("List-Commands", "&8[&9CXP&8] &7To view a list of commands type: &f/cxp help"),
    COMMAND_NOT_FOUND("Command-Not-Found", "&8[&cCXP&8] &7Command not found, to view a list of commands type: &f/cxp help"),
    ARGUMENT_MUST_BE_INTEGER("Argument-Must-Be-Integer", "&8[&cCXP&8] &f{0} &7must be an integer."),
    NO_PERMISSION_COMMAND("No-Permission", "&8[&cCXP&8] &7You do not have the required permission to perform this command: &f{0}");


    private String path;
    private String def;

    Lang(String path, String def)
    {
        this.path = path;
        this.def = def;
    }

    public void sendMessage(CommandSender sender, String... replacements)
    {
        sender.sendMessage(getMessage(replacements));
    }

    public String getMessage(String... replacements)
    {
        String message = CumulativeXp.getInstance().getLangFile().getValue(path, def);
        for (int i = 0; i < replacements.length; i++)
        {
            message = message.replace("{" + i + "}", replacements[i]);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String getPath()
    {
        return path;
    }

    public String getDefault()
    {
        return def;
    }
}
