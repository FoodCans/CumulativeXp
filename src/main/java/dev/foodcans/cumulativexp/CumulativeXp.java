package dev.foodcans.cumulativexp;

import dev.foodcans.cumulativexp.command.*;
import dev.foodcans.cumulativexp.database.DatabaseManager;
import dev.foodcans.cumulativexp.lang.LangFile;
import dev.foodcans.cumulativexp.listener.PlayerListener;
import dev.foodcans.cumulativexp.manager.PlayerManager;
import dev.foodcans.cumulativexp.placeholderapi.CxpPlaceholder;
import dev.foodcans.cumulativexp.util.Config;
import dev.foodcans.cumulativexp.util.LogUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

/**
 * Spigot plugin that keeps track of the total amount of experience points and levels players have earned
 * @author FoodCans
 */
public class CumulativeXp extends JavaPlugin
{
    private static CumulativeXp instance;

    private LangFile langFile;
    private DatabaseManager databaseManager;
    private PlayerManager playerManager;

    @Override
    public void onLoad()
    {
        instance = this;
        saveDefaultConfig();
        Config.loadConfig(getConfig());
    }

    @Override
    public void onEnable()
    {
        langFile = new LangFile(getDataFolder(), "lang.yml");
        databaseManager = new DatabaseManager();
        try
        {
            databaseManager.setupTable();
        } catch (SQLException e)
        {
            LogUtil.error(e.getMessage());
        }

        this.playerManager = new PlayerManager(databaseManager);
        this.playerManager.startTask();

        // PlaceholderAPI registration
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
        {
            new CxpPlaceholder(playerManager).register();
        }

        getServer().getPluginManager().registerEvents(new PlayerListener(playerManager), this);

        CommandManager commandManager = new CommandManager();
        commandManager.registerCommand(new XPCommand(playerManager), new LevelCommand(playerManager), new RankCommand(playerManager), new TopCommand(playerManager), new ReloadCommand(playerManager), new HelpCommand(playerManager));
        getCommand("cxp").setExecutor(commandManager);
    }

    @Override
    public void onDisable()
    {
        databaseManager.shutdown();
        playerManager.stopTask();
    }

    public LangFile getLangFile()
    {
        return langFile;
    }

    public static CumulativeXp getInstance()
    {
        return instance;
    }
}
