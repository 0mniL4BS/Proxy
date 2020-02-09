package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

import dao.pojo.MemberPOJO;
import dao.pojo.POJOFactory;

public class MemberDAO extends DAO<MemberPOJO> {

    protected MemberDAO(HikariDataSource dataSource) {
        super(dataSource);
    }

    @Override
    public boolean create(MemberPOJO member) {

        String query = "INSERT INTO `members`(`guild_id`, `member_id`, `member_name`, `member_nickname`) VALUES(?, ?, ?, ?);";

        try (Connection conn = this.dataSource.getConnection();) {

            conn.setAutoCommit(false);

            try (PreparedStatement pst = conn.prepareStatement(query);) {

                pst.setString(1, member.getGuildId());
                pst.setString(2, member.getId());
                pst.setString(3, member.getName());
                pst.setString(4, member.getNickName());
                pst.executeUpdate();
                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                return false;
            }

            conn.setAutoCommit(true);

        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(MemberPOJO member) {

        String query = "DELETE FROM `members` WHERE `guild_id` = ? AND `member_id` = ?;";

        try (Connection conn = this.dataSource.getConnection();) {

            conn.setAutoCommit(false);

            try (PreparedStatement pst = conn.prepareStatement(query);) {

                pst.setString(1, member.getGuildId());
                pst.setString(2, member.getId());
                pst.executeUpdate();
                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                return false;
            }

            conn.setAutoCommit(true);

        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(MemberPOJO member) {

        String query = "UPDATE `members` SET `member_name` = ?, `member_nickname` = ?, `permission_level` = ? WHERE `guild_id` = ? AND `member_id` = ?;";

        try (Connection conn = this.dataSource.getConnection();) {

            conn.setAutoCommit(false);

            try (PreparedStatement pst = conn.prepareStatement(query);) {

                pst.setString(1, member.getName());
                pst.setString(2, member.getNickName());
                pst.setInt(3, member.getPermLevel());
                pst.setString(4, member.getGuildId());
                pst.setString(5, member.getId());
                pst.executeUpdate();
                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                return false;
            }

            conn.setAutoCommit(true);

        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public MemberPOJO find(String guildId, String memberId) {

        MemberPOJO member = null;
        String query = "SELECT `guild_id`, `member_id`, `member_name`, `member_nickname`, `permission_level` FROM `members` WHERE `guild_id` = ? AND `member_id` = ?;";

        try (Connection conn = this.dataSource.getConnection(); PreparedStatement pst = conn.prepareStatement(query);) {

            pst.setString(1, guildId);
            pst.setString(2, memberId);

            try (ResultSet rs = pst.executeQuery();) {

                rs.next();
                member = POJOFactory.getMember();
                member.setGuildId(rs.getString("guild_id"));
                member.setId(rs.getString("member_id"));
                member.setName(rs.getString("member_name"));
                member.setNickName(rs.getString("member_nickname"));
                member.setPermLevel(rs.getInt("permission_level"));
            }

        } catch (SQLException e) {
        }
        return member;
    }

}