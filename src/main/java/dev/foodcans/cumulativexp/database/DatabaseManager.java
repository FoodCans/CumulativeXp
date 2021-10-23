package dev.foodcans.cumulativexp.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.foodcans.cumulativexp.manager.PlayerData;
import dev.foodcans.cumulativexp.util.Config;
import dev.foodcans.cumulativexp.util.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
        hikariConfig.addDataSourceProperty("allowPublicKeyRetrieval", "true");
        hikariConfig.addDataSourceProperty("useSSL", "false");
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

    public PlayerData getPlayerData(String uuid)
    {
        try (Connection connection = getConnection())
        {
            return getPlayerData(uuid, connection);
        } catch (SQLException e)
        {
            LogUtil.error(e.getMessage());
        }
        return null;
    }

    private PlayerData getPlayerData(String uuid, Connection connection) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(Queries.GET);
        statement.setString(1, uuid);
        ResultSet result = statement.executeQuery();
        if (result.next())
        {
            int xp = result.getInt("xp");
            int level = result.getInt("level");
            return new PlayerData(UUID.fromString(uuid), xp, level);
        }
        return null;
    }

    public void updateXp(String uuid, int xp)
    {
        try (Connection connection = getConnection())
        {
            updateXp(uuid, xp, connection);
        } catch (SQLException e)
        {
            LogUtil.error(e.getMessage());
        }
    }

    private void updateXp(String uuid, int xp, Connection connection) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(Queries.UPDATE_XP);
        statement.setString(1, uuid);
        statement.setInt(2, xp);
        statement.setInt(3, xp);
        statement.executeUpdate();
    }

    public void updateLevel(String uuid, int level)
    {
        try (Connection connection = getConnection())
        {
            updateLevel(uuid, level, connection);
        } catch (SQLException e)
        {
            LogUtil.error(e.getMessage());
        }
    }

    private void updateLevel(String uuid, int level, Connection connection) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(Queries.UPDATE_LEVEL);
        statement.setString(1, uuid);
        statement.setInt(2, level);
        statement.setInt(3, level);
        statement.executeUpdate();
    }

    public Set<String> getUuids()
    {
        try (Connection connection = getConnection())
        {
            return getUuids(connection);
        } catch (SQLException e)
        {
            LogUtil.error(e.getMessage());
        }
        return null;
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
        statement.setString(1, uuid);
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
