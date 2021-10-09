package dev.foodcans.cumulativexp.util;

import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class LogUtil
{
    public static final Logger LOGGER;

    static
    {
        LOGGER = Bukkit.getLogger();
    }

    public static void info(String message)
    {
        LOGGER.log(Level.INFO, message);
    }

    public static void warn(String message)
    {
        LOGGER.log(Level.WARNING, message);
    }

    public static void error(String message)
    {
        LOGGER.log(Level.SEVERE, message);
    }
}
