package io.paradaux.friendlybot.data.database;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import io.paradaux.friendlybot.data.config.FConfiguration;
import io.paradaux.friendlybot.data.config.FConfigurationLoader;

public class EBeanConnection {

    private final DatabaseConfig dbConfig = new DatabaseConfig();
    private final Database database;

    public EBeanConnection(FConfiguration config) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUsername(config.getJdbcUsername());
        dataSourceConfig.setPassword(config.getJdbcPassword());
        dataSourceConfig.setUrl(config.getJdbcUrl());

        dbConfig.setDataSourceConfig(dataSourceConfig);
        database = DatabaseFactory.create(dbConfig);
    }

}
