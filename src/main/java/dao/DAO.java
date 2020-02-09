package dao;

import com.zaxxer.hikari.HikariDataSource;

public abstract class DAO<T> {

    protected HikariDataSource dataSource = null;

    protected DAO(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public abstract boolean create(T obj);

    public abstract boolean delete(T obj);

    public abstract boolean update(T obj);

    public abstract T find(String id1, String id2);

}