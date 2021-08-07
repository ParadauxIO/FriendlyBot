package io.paradaux.friendlybot.core.database;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;

public class EBeanConnection {

    private final DatabaseConfig config = new DatabaseConfig();
    private final Database database;

    public EBeanConnection() {
        // 157.90.21.214

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUsername("sa");
        dataSourceConfig.setPassword("");
        dataSourceConfig.setUrl("jdbc:h2:mem:myapp;");

        this.config.setDataSourceConfig(dataSourceConfig);
        this.database = DatabaseFactory.create(config);
    }

}
