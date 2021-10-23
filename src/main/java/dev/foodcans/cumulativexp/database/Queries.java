package dev.foodcans.cumulativexp.database;

import dev.foodcans.cumulativexp.util.Config;

public class Queries
{
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + Config.DATABASE_TABLE_NAME + "(uuid CHAR(36) NOT NULL,xp INT DEFAULT 0,level INT DEFAULT 0,PRIMARY KEY(uuid))";
    public static final String UPDATE_XP = "INSERT INTO " + Config.DATABASE_TABLE_NAME + " (uuid,xp) VALUES(?,?) ON DUPLICATE KEY UPDATE xp=?";
    public static final String UPDATE_LEVEL = "INSERT INTO " + Config.DATABASE_TABLE_NAME + " (uuid,level) VALUES(?,?) ON DUPLICATE KEY UPDATE level=?";
    public static final String GET = "SELECT xp,level FROM " + Config.DATABASE_TABLE_NAME + " WHERE uuid=?";
    public static final String GET_PLAYERS = "SELECT uuid FROM " + Config.DATABASE_TABLE_NAME;
    public static final String REMOVE_PLAYER = "DELETE FROM " + Config.DATABASE_TABLE_NAME + " WHERE uuid=?";
}
