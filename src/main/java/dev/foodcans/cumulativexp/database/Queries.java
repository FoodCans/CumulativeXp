package dev.foodcans.cumulativexp.database;

import dev.foodcans.cumulativexp.util.Config;

public class Queries
{
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + Config.DATABASE_TABLE_NAME + "(uuid CHAR(36) NOT NULL,xp INT,PRIMARY KEY(uuid))";
    public static final String UPDATE_XP = "INSERT INTO " + Config.DATABASE_TABLE_NAME + " (uuid,xp) VALUES(?,?) ON DUPLICATE KEY UPDATE xp=?";
    public static final String GET_XP = "SELECT * FROM " + Config.DATABASE_TABLE_NAME + " WHERE uuid=?";
    public static final String GET_PLAYERS = "SELECT uuid FROM " + Config.DATABASE_TABLE_NAME;
    public static final String REMOVE_PLAYER = "DELETE FROM " + Config.DATABASE_TABLE_NAME + " WHERE uuid=?";
}
