package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import proxy.Constants;

public class DBService {

    private static volatile DBService instance;
    private static HikariConfig hikariConfig;
    private static HikariDataSource dataSource;

    private DBService() {
        hikariConfig = new HikariConfig(Constants.PATH_DATASOURCE_PROPERTIES);
        dataSource = new HikariDataSource(hikariConfig);
        dataSource.setConnectionTimeout(30000);
        dataSource.setMaxLifetime(1800000);
        dataSource.setMinimumIdle(10);
        dataSource.setMaximumPoolSize(10);
    }

    public static HikariDataSource getInstance() {
        if (instance == null) {
            synchronized (DBService.class) {
                if (instance == null) {
                    instance = new DBService();
                }
            }
        }
        return dataSource;
    }

}