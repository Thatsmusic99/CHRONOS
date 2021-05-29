package io.github.thatsmusic99.chronos.sql;

import io.github.thatsmusic99.chronos.Chronos;
import io.github.thatsmusic99.chronos.api.ChronosPlayer;
import io.github.thatsmusic99.chronos.configuration.MainConfiguration;

import java.sql.*;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class SQLHandler {

    private static Connection connection;

    public static void init() {
        try {
            MainConfiguration config = Chronos.getInstance().getConfiguration();
            if (config.getSQLType().equalsIgnoreCase("mysql")) {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:" +
                        "mysql:" +
                        "");
            } else {

                connection = DriverManager.getConnection("jdbc:sqlite:" + config.getSQLiteDBName() + ".db");
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    private static void createTables() {

    }


    public static CompletableFuture<String> addPlayerStatistic(String uuid, String statistic, int increase, String... subtypes) {
        CompletableFuture.supplyAsync(() -> {

        }, Chronos.async).thenApplyAsync(result -> result, Chronos.sync);
    }


    public static void createTable(String name) {
        try {
            StringBuilder arg = new StringBuilder();
            arg.append(name).append("(`id` INT NOT NULL AUTO_INCREMENT, `uuid` VARCHAR(45), ");
            for (String stat : Arrays.asList("`first`", "`last`", "`second_last`", "`current_login`", "`today`", "`weekly`", "`monthly`", "`yearly`")) {
                arg.append(stat).append(" BIGINT(), ");
            }
            arg.append("PRIMARY KEY (`id`))");
            update(OperationType.CREATE, arg.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void addToTotal(String database, int addition, String section, String uuid) {
        try {
            if (!doesPlayerExist(database, uuid)) addPlayer(database, uuid);
            ResultSet set = query(OperationType.SELECT, section, database, "uuid", uuid);
            set.next();
            int total = set.getInt("total");
            int totalSec = set.getInt(section);
            update(OperationType.UPDATE, database, section, String.valueOf(totalSec + addition), String.valueOf(total + addition), "uuid", uuid);
        } catch (Exception e) {
            try {
                update(OperationType.ALTER, database, section);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static long getData(UUID uuid, String table, ChronosPlayer.TimeScope scope) {
        try {
            String column = scope.name().toLowerCase();
            ResultSet set = query(OperationType.SELECT, column, table, "uuid", uuid.toString());
            if (set.next()) {
                return set.getLong(column);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return -1;
    }

    private static boolean doesPlayerExist(String database, String uuid) {
        try {
            return query(OperationType.SELECT_COUNT, database, "uuid", uuid).next();
        } catch (SQLException e) {
            return false;
        }
    }

    private static void addPlayer(String database, String uuid) {
        try {
            update(OperationType.INSERT_INTO, database, "uuid", uuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void update(OperationType type, String... args) throws SQLException {
        prepareStatement(type, args).executeUpdate();
    }

    private static ResultSet query(OperationType type, String... args) throws SQLException {
        return prepareStatement(type, args).executeQuery();
    }

    private static PreparedStatement prepareStatement(OperationType type, String... args) throws SQLException {
        return connection.prepareStatement(String.format(type.syntax, args));
    }

    public static Connection getConnection() {
        return connection;
    }

    private enum OperationType {
        SELECT("SELECT %1$s FROM %2$s WHERE %3$s='%4$s'"),
        INSERT_INTO("INSERT INTO %1$s (`%2$s`) VALUES ('%3$s')"),
        UPDATE("UPDATE %1$s SET %2$s='%3$s' WHERE %5$s='%6$s'"),
        CREATE("CREATE TABLE IF NOT EXISTS %1$s"),
        ALTER("ALTER TABLE %1$s ADD COLUMN %2$s VARCHAR(45)"),
        SELECT_ORDER("SELECT %1$s, %2$s FROM %3$s ORDER BY `id`"),
        SELECT_COUNT("SELECT 1 FROM %1$s WHERE `%2$s`='%3$s'"),
        SELECT_DESC("");

        String syntax;

        OperationType(String syntax) {
            this.syntax = syntax;
        }
    }
}
