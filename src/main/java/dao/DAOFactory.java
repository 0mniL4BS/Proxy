package dao;

import com.zaxxer.hikari.HikariDataSource;

import dao.pojo.GuildPOJO;
import dao.pojo.MemberPOJO;
import dao.pojo.PermissionPOJO;

public class DAOFactory {

    private static HikariDataSource dataSource = DBService.getInstance();

    private DAOFactory() {
    }

    public static DAO<GuildPOJO> getGuildDAO() {
        return new GuildDAO(dataSource);
    }

    public static DAO<MemberPOJO> getMemberDAO() {
        return new MemberDAO(dataSource);
    }

    public static DAO<PermissionPOJO> getPermissionDAO() {
        return new PermissionDAO(dataSource);
    }

}