package dev.foodcans.cumulativexp.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.foodcans.cumulativexp.util.Config;
import dev.foodcans.cumulativexp.util.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DatabaseManager
{
    private final HikariDataSource dataSource;

    public DatabaseManager()
    {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" + Config.DATABASE_URL);
        hikariConfig.setUsername(Config.DATABASE_USERNAME);
        hikariConfig.setPassword(Config.DATABASE_PASSWORD);
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        dataSource = new HikariDataSource(hikariConfig);
    }

    public void setupTable() throws SQLException
    {
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(Queries.CREATE_TABLE);
            statement.executeUpdate();
        }
    }

    public int getTotalXp(String uuid)
    {
        int xp = 0;
        try (Connection connection = getConnection())
        {
            xp = getTotalXp(uuid, connection);
        } catch (SQLException e)
        {
            LogUtil.error(e.getMessage());
        }
        return xp;
    }

    private int getTotalXp(String uuid, Connection connection) throws SQLException
    {
        int xp = 0;
        PreparedStatement statement = connection.prepareStatement(Queries.GET_XP);
        statement.setString(1, uuid);
        ResultSet result = statement.executeQuery();
        if (result.next())
        {
            xp = result.getInt("xp");
        }
        return xp;
    }

    public void addXp(String uuid, int amount)
    {
        try (Connection connection = getConnection())
        {
            addXp(uuid, amount, connection);
        } catch (SQLException e)
        {
            LogUtil.error(e.getMessage());
        }
    }

    private void addXp(String uuid, int amount, Connection connection) throws SQLException
    {
        int xp = getTotalXp(uuid, connection);
        xp += amount;
        PreparedStatement statement = connection.prepareStatement(Queries.UPDATE_XP);
        statement.setString(1, uuid);
        statement.setInt(2, xp);
        statement.setInt(3, xp);
        statement.executeUpdate();
    }

    public Set<String> getUuids()
    {
        Set<String> uuids = new HashSet<>();
        try (Connection connection = getConnection())
        {
            uuids = getUuids(connection);
        } catch (SQLException e)
        {
            LogUtil.error(e.getMessage());
        }
        return uuids;
    }

    private Set<String> getUuids(Connection connection) throws SQLException
    {
        Set<String> uuids = new HashSet<>();
        PreparedStatement statement = connection.prepareStatement(Queries.GET_PLAYERS);
        ResultSet result = statement.executeQuery();
        while (result.next())
        {
            uuids.add(result.getString("uuid"));
        }
        return uuids;
    }

    public void removeUuid(String uuid)
    {
        try (Connection connection = getConnection())
        {
            removeUuid(uuid, connection);
        } catch (SQLException e)
        {
            LogUtil.error(e.getMessage());
        }
    }

    private void removeUuid(String uuid, Connection connection) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(Queries.REMOVE_PLAYER);
        statement.executeUpdate();
    }

    private Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }

    public void shutdown()
    {
        dataSource.close();
    }
}
