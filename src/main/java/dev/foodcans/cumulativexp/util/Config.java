package dev.foodcans.cumulativexp.util;

import org.bukkit.configuration.file.FileConfiguration;

public class Config
{
    public static String DATABASE_URL;
    public static String DATABASE_USERNAME;
    public static String DATABASE_PASSWORD;
    public static String DATABASE_TABLE_NAME;

    public static boolean REMOVE_HARDCORE;

    public static int ENTRIES_PER_PAGE;

    public static void loadConfig(FileConfiguration config)
    {
        DATABASE_URL = config.getString("mysql.url");
        DATABASE_USERNAME = config.getString("mysql.username");
        DATABASE_PASSWORD = config.getString("mysql.password");
        DATABASE_TABLE_NAME = config.getString("mysql.table_name");

        REMOVE_HARDCORE = config.getBoolean("remove_player_if_hardcore");

        ENTRIES_PER_PAGE = config.getInt("entries_per_page");
    }
}
