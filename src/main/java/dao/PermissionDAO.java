package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

import dao.pojo.POJOFactory;
import dao.pojo.PermissionPOJO;

public class PermissionDAO extends DAO<PermissionPOJO> {

    protected PermissionDAO(HikariDataSource dataSource) {
        super(dataSource);
    }

    @Override
    public boolean create(PermissionPOJO obj) {
        return false;
    }

    @Override
    public boolean delete(PermissionPOJO obj) {
        return false;
    }

    @Override
    public boolean update(PermissionPOJO obj) {
        return false;
    }

    @Override
    public PermissionPOJO find(String guildId, String memberId) {

        PermissionPOJO permission = null;
        String query = "SELECT `level`, `name` FROM `permissions` AS p INNER JOIN `members` AS m ON p.level = m.permission_level WHERE `guild_id` = ? AND `member_id` = ?;";

        try (Connection conn = this.dataSource.getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, guildId);
            pst.setString(2, memberId);

            try (ResultSet rs = pst.executeQuery()) {

                rs.next();
                permission = POJOFactory.getPermission();
                permission.setLevel(rs.getInt("level"));
                permission.setName(rs.getString("name"));
            }

        } catch (SQLException e) {
        }
        return permission;
    }

}