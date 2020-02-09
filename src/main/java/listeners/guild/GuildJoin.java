package listeners.guild;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dao.DAO;
import dao.DAOFactory;
import dao.GuildDAO;
import dao.pojo.GuildPOJO;
import dao.pojo.MemberPOJO;
import dao.pojo.POJOFactory;
import listeners.onconnection.ConnectionDBL;
import listeners.onconnection.ConnectionDiscord;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import proxy.Constants;

public class GuildJoin extends ListenerAdapter {

    private DAO<GuildPOJO> guildDao;

    @Override
    public void onGuildJoin(GuildJoinEvent event) {

        if (!event.getGuild().getId().equals(Constants.DISCORDBOTLIST_ID)) {
            addGuildDatas(event);
            addMembersDatas(event);
        }
        ConnectionDBL.getInstance().setStats(ConnectionDiscord.getInstance().getGuilds().size());
    }

    private void addGuildDatas(GuildJoinEvent event) {

        guildDao = DAOFactory.getGuildDAO();
        GuildPOJO guild = POJOFactory.getGuild();
        guild.setId(event.getGuild().getId());
        guild.setName(event.getGuild().getName());

        guildDao.create(guild);
    }

    private void addMembersDatas(GuildJoinEvent event) {

        Set<MemberPOJO> mbrs = new HashSet<>();
        List<Member> members = event.getGuild().getMembers();

        for (int i = 0; i < members.size(); i++) {

            if (!members.get(i).getUser().isBot()) {

                MemberPOJO member = POJOFactory.getMember();
                member.setGuildId(event.getGuild().getId());
                member.setId(members.get(i).getId());
                member.setName(members.get(i).getUser().getName());
                member.setNickName(members.get(i).getNickname());

                if (members.get(i).getId().equals(members.get(i).getGuild().getOwnerId())) {

                    member.setPermLevel(Constants.ADMINISTRATOR_PERM);

                } else {

                    member.setPermLevel(Constants.USER_PERM);
                }

                mbrs.add(member);
            }
        }
        ((GuildDAO) guildDao).createMembers(mbrs);
    }

}