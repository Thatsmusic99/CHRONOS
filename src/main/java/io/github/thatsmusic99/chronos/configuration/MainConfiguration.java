package io.github.thatsmusic99.chronos.configuration;

import io.github.thatsmusic99.chronos.Chronos;
import io.github.thatsmusic99.configurationmaster.CMFile;

public class MainConfiguration extends CMFile {

    public MainConfiguration(Chronos chronos) {
        super(chronos, "config");
        setTitle("CHRONOS\nBy Thatsmusic99");
    }

    @Override
    public void loadDefaults() {
        addSection("SQL Settings");
        addDefault("sql-type", "SQLite", "SQL Type - how the data is stored.\n" +
                "           By default, CHRONOS stores using SQLite (SQLite), which stores data in a .db file.\n" +
                "           However, you can store data using MySQL (MySQL), which stores data in an external database.\n" +
                "CHRONOS does not use JSON or YML storage to ensure it updates as fast as it can.");

        addComment("mysql", "MySQL connection properties\n" +
                "If the sql-type is not MySQL, you can ignore this configuration.");
        addDefault("mysql.host", "localhost", "The host name of your database.");
        addDefault("mysql.port", 3306, "The port of your database.");
        addDefault("mysql.database", "db", "The name of the database itself.");
        addDefault("mysql.user", "username", "The username which is required to log in.");
        addDefault("mysql.password", "password", "The password for the above user.");

        addComment("sqlite", "SQLite settings");
        addDefault("sqlite.file-name", "data", "File name for the SQLite DB file.\n" +
                "This will automatically have the .db extension.");


    }
}
