package io.paradaux.friendlybot.core.data.database;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import io.paradaux.friendlybot.core.data.config.FConfiguration;

public class EBeanConnection {

    private final DatabaseConfig dbConfig = new DatabaseConfig();
    private final Database database;

    public EBeanConnection(FConfiguration config) {

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.addProperty("ebean.db.ddl.generate", "true");
        dataSourceConfig.addProperty("ebean.db.ddl.run", "true");
        dataSourceConfig.setUsername(config.getJdbcUsername());
        dataSourceConfig.setPassword(config.getJdbcPassword());
        dataSourceConfig.setUrl(config.getJdbcUrl());

        dbConfig.setDataSourceConfig(dataSourceConfig);
        database = DatabaseFactory.create(dbConfig);

    }

}
