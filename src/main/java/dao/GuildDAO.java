package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.zaxxer.hikari.HikariDataSource;

import dao.pojo.GuildPOJO;
import dao.pojo.MemberPOJO;
import dao.pojo.POJOFactory;

public class GuildDAO extends DAO<GuildPOJO> {

    protected GuildDAO(HikariDataSource dataSource) {
        super(dataSource);
    }

    @Override
    public boolean create(GuildPOJO guild) {

        String query = "INSERT INTO `guilds`(`guild_id`, `guild_name`) VALUES(?, ?);";

        try (Connection conn = this.dataSource.getConnection();) {

            conn.setAutoCommit(false);

            try (PreparedStatement pst = conn.prepareStatement(query);) {

                pst.setString(1, guild.getId());
                pst.setString(2, guild.getName());
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

    public boolean createMembers(Set<MemberPOJO> members) {

        String query = "INSERT INTO `members`(`guild_id`, `member_id`, `member_name`, `member_nickname`, `permission_level`) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = this.dataSource.getConnection();) {

            conn.setAutoCommit(false);

            try (PreparedStatement pst = conn.prepareStatement(query);) {

                Object[] membersTmp = members.toArray();

                for (int i = 0; i < membersTmp.length; i++) {

                    pst.setString(1, ((MemberPOJO) membersTmp[i]).getGuildId());
                    pst.setString(2, ((MemberPOJO) membersTmp[i]).getId());
                    pst.setString(3, ((MemberPOJO) membersTmp[i]).getName());
                    pst.setString(4, ((MemberPOJO) membersTmp[i]).getNickName());
                    pst.setInt(5, ((MemberPOJO) membersTmp[i]).getPermLevel());
                    pst.addBatch();
                }

                pst.executeBatch();
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
    public boolean delete(GuildPOJO guild) {

        String query = "DELETE FROM `guilds` WHERE `guild_id` = ?;";

        try (Connection conn = this.dataSource.getConnection();) {

            conn.setAutoCommit(false);

            try (PreparedStatement pst = conn.prepareStatement(query);) {

                pst.setString(1, guild.getId());
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

    public boolean deleteMembers(Set<MemberPOJO> members) {

        String query = "DELETE FROM `members` WHERE `guild_id` = ? AND `member_id` = ?";

        try (Connection conn = this.dataSource.getConnection();) {

            conn.setAutoCommit(false);

            try (PreparedStatement pst = conn.prepareStatement(query);) {

                Object[] membersTmp = members.toArray();

                for (int i = 0; i < membersTmp.length; i++) {

                    pst.setString(1, (((MemberPOJO) membersTmp[i]).getGuildId()));
                    pst.setString(2, (((MemberPOJO) membersTmp[i]).getId()));
                    pst.addBatch();
                }

                pst.executeBatch();
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
    public boolean update(GuildPOJO guild) {

        String query = "UPDATE `guilds` SET `guild_name` = ?, `default_channel` = ?, `default_role` = ?, `prefix` = ?, `shield` = ? WHERE `guild_id` = ?;";

        try (Connection conn = this.dataSource.getConnection();) {

            conn.setAutoCommit(false);

            try (PreparedStatement pst = conn.prepareStatement(query);) {

                pst.setString(1, guild.getName());
                pst.setString(2, guild.getDefChan());
                pst.setString(3, guild.getDefRole());
                pst.setString(4, guild.getPrefix());
                pst.setBoolean(5, guild.getShield());
                pst.setString(6, guild.getId());
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

    public boolean updateMembers(Set<MemberPOJO> members) {

        String query = "UPDATE `members` SET `guild_id` = ?, `member_id` = ?, `member_name` = ?, `member_nickname` = ?, `permission_level` = ? WHERE `guild_id` = ?";

        try (Connection conn = this.dataSource.getConnection();) {

            conn.setAutoCommit(false);

            try (PreparedStatement pst = conn.prepareStatement(query);) {

                Object[] membersTmp = members.toArray();

                for (int i = 0; i < membersTmp.length; i++) {

                    pst.setString(1, ((MemberPOJO) membersTmp[i]).getGuildId());
                    pst.setString(2, ((MemberPOJO) membersTmp[i]).getId());
                    pst.setString(3, ((MemberPOJO) membersTmp[i]).getName());
                    pst.setString(4, ((MemberPOJO) membersTmp[i]).getNickName());
                    pst.setInt(5, ((MemberPOJO) membersTmp[i]).getPermLevel());
                    pst.setString(6, ((MemberPOJO) membersTmp[i]).getGuildId());
                    pst.addBatch();
                }

                pst.executeBatch();
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
    public GuildPOJO find(String guildId, String memberId) {

        GuildPOJO guild = null;
        String query = "SELECT `guild_id`, `guild_name`, `default_channel`, `default_role`, `prefix`, `shield` FROM `guilds` WHERE `guild_id` = ?;";

        try (Connection conn = this.dataSource.getConnection(); PreparedStatement pst = conn.prepareStatement(query);) {

            pst.setString(1, guildId);

            try (ResultSet rs = pst.executeQuery();) {

                rs.next();
                guild = POJOFactory.getGuild();
                guild.setId(rs.getString("guild_id"));
                guild.setName(rs.getString("guild_name"));
                guild.setDefChan(rs.getString("default_channel"));
                guild.setDefRole(rs.getString("default_role"));
                guild.setPrefix(rs.getString("prefix"));
                guild.setShield(rs.getBoolean("shield"));
            }

        } catch (SQLException e) {
        }
        return guild;
    }

    public Set<GuildPOJO> findGuilds() {

        Set<GuildPOJO> databaseGuilds = null;
        String query = "SELECT `guild_id`, `guild_name`, `default_channel`, `default_role`, `prefix`, `shield` FROM `guilds`;";

        try (Connection conn = this.dataSource.getConnection(); PreparedStatement pst = conn.prepareStatement(query);) {

            try (ResultSet rs = pst.executeQuery();) {

                databaseGuilds = new HashSet<>();

                while (rs.next()) {

                    GuildPOJO guild = POJOFactory.getGuild();
                    guild.setId(rs.getString("guild_id"));
                    guild.setName(rs.getString("guild_name"));
                    guild.setDefChan(rs.getString("default_channel"));
                    guild.setDefRole(rs.getString("default_role"));
                    guild.setPrefix(rs.getString("prefix"));
                    guild.setShield(rs.getBoolean("shield"));
                    databaseGuilds.add(guild);
                }
            }

        } catch (SQLException e) {
        }
        return databaseGuilds;
    }

    public Set<MemberPOJO> findMembers(String guildId) {

        Set<MemberPOJO> databaseMembers = null;
        String query = "SELECT `guild_id`, `member_id`, `member_name`, `member_nickname`, `permission_level` FROM `members` WHERE `guild_id` = ?;";

        try (Connection conn = this.dataSource.getConnection(); PreparedStatement pst = conn.prepareStatement(query);) {

            pst.setString(1, guildId);

            try (ResultSet rs = pst.executeQuery();) {

                databaseMembers = new HashSet<>();

                while (rs.next()) {

                    MemberPOJO member = POJOFactory.getMember();
                    member.setGuildId(rs.getString("guild_id"));
                    member.setId(rs.getString("member_id"));
                    member.setName(rs.getString("member_name"));
                    member.setNickName(rs.getString("member_nickname"));
                    member.setPermLevel(rs.getInt("permission_level"));
                    databaseMembers.add(member);
                }
            }

        } catch (SQLException e) {
        }
        return databaseMembers;
    }

}