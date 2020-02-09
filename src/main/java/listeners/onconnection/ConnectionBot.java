package listeners.onconnection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dao.DAO;
import dao.DAOFactory;
import dao.GuildDAO;
import dao.pojo.GuildPOJO;
import dao.pojo.MemberPOJO;
import dao.pojo.POJOFactory;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ReconnectedEvent;
import net.dv8tion.jda.api.events.ResumedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import proxy.Constants;

public class ConnectionBot extends ListenerAdapter {

    protected ConnectionBot() {
    }

    @Override
    public void onReady(ReadyEvent event) {
        updateGuildsLeftDC(event);
        updateGuildsAddDC(event);
        updateMembers(event);
    }

    @Override
    public void onResume(ResumedEvent event) {
        updateGuildsLeftDC(event);
        updateGuildsAddDC(event);
        updateMembers(event);
    }

    @Override
    public void onReconnect(ReconnectedEvent event) {
        updateGuildsLeftDC(event);
        updateGuildsAddDC(event);
        updateMembers(event);
    }

    private void updateGuildsLeftDC(Event event) {

        DAO<GuildPOJO> guildDao = DAOFactory.getGuildDAO();

        Set<GuildPOJO> discordGuilds = new HashSet<>();
        Set<GuildPOJO> databaseGuilds = ((GuildDAO) guildDao).findGuilds();

        List<Guild> guilds = event.getJDA().getGuilds();

        for (int i = 0; i < guilds.size(); i++) {

            GuildPOJO guild = POJOFactory.getGuild();
            guild.setId(guilds.get(i).getId());
            guild.setName(guilds.get(i).getName());
            discordGuilds.add(guild);
        }

        guildsRemoveAll(databaseGuilds, discordGuilds);

        if (!databaseGuilds.isEmpty()) {

            GuildPOJO guild = POJOFactory.getGuild();
            Object[] guildsTmp = databaseGuilds.toArray();

            for (int i = 0; i < guildsTmp.length; i++) {

                guild.setId(((GuildPOJO) guildsTmp[i]).getId());
                guild.setMembers(((GuildDAO) guildDao).findMembers(((GuildPOJO) guildsTmp[i]).getId()));

                ((GuildDAO) guildDao).deleteMembers(guild.getMembers());
                guildDao.delete(guild);
            }
        }
    }

    private void updateGuildsAddDC(Event event) {

        DAO<GuildPOJO> guildDao = DAOFactory.getGuildDAO();

        Set<GuildPOJO> discordGuilds = new HashSet<>();
        Set<GuildPOJO> databaseGuilds = ((GuildDAO) guildDao).findGuilds();

        List<Guild> guilds = event.getJDA().getGuilds();

        for (int i = 0; i < guilds.size(); i++) {

            GuildPOJO guild = POJOFactory.getGuild();
            guild.setId(guilds.get(i).getId());
            guild.setName(guilds.get(i).getName());
            discordGuilds.add(guild);
        }

        guildsRemoveAll(discordGuilds, databaseGuilds);

        if (!discordGuilds.isEmpty()) {

            GuildPOJO guild = POJOFactory.getGuild();
            Object[] guildsTmp = discordGuilds.toArray();

            for (int i = 0; i < guildsTmp.length; i++) {

                guild.setId(((GuildPOJO) guildsTmp[i]).getId());
                guild.setName(((GuildPOJO) guildsTmp[i]).getName());
                guildDao.create(guild);

                Set<MemberPOJO> discordMembers = getDiscordMembers(
                        event.getJDA().getGuildById(guild.getId()).getMembers());

                ((GuildDAO) guildDao).createMembers(discordMembers);
            }
        }
    }

    private void updateMembers(Event event) {

        List<Guild> guilds = event.getJDA().getGuilds();

        for (int i = 0; i < guilds.size(); i++) {

            if (!guilds.get(i).getId().equals(Constants.DISCORDBOTLIST_ID)) {

                updateMembersLeftDC(event, guilds, i);
                updateMembersAddDC(event, guilds, i);
            }
        }
    }

    private void updateMembersLeftDC(Event event, List<Guild> guilds, int i) {

        DAO<GuildPOJO> guildDao = DAOFactory.getGuildDAO();

        Set<MemberPOJO> discordMembers = new HashSet<>();
        Set<MemberPOJO> databaseMembers = ((GuildDAO) guildDao).findMembers(guilds.get(i).getId());

        List<Member> members = event.getJDA().getGuilds().get(i).getMembers();

        for (int j = 0; j < members.size(); j++) {

            if (!members.get(j).getUser().isBot()) {

                MemberPOJO member = POJOFactory.getMember();
                member.setGuildId(guilds.get(i).getId());
                member.setId(members.get(j).getId());

                discordMembers.add(member);
            }
        }

        membersRemoveAll(databaseMembers, discordMembers);

        if (!databaseMembers.isEmpty()) {

            ((GuildDAO) guildDao).deleteMembers(databaseMembers);
        }
    }

    private void updateMembersAddDC(Event event, List<Guild> guilds, int i) {

        DAO<GuildPOJO> guildDao = DAOFactory.getGuildDAO();

        Set<MemberPOJO> discordMembers = getDiscordMembers(event.getJDA().getGuilds().get(i).getMembers());
        Set<MemberPOJO> databaseMembers = ((GuildDAO) guildDao).findMembers(guilds.get(i).getId());

        membersRemoveAll(discordMembers, databaseMembers);

        if (!discordMembers.isEmpty()) {

            ((GuildDAO) guildDao).createMembers(discordMembers);
        }
    }

    private Set<MemberPOJO> getDiscordMembers(List<Member> members) {

        Set<MemberPOJO> discordMembers = new HashSet<>();

        for (int i = 0; i < members.size(); i++) {

            if (!members.get(i).getUser().isBot()) {

                MemberPOJO member = POJOFactory.getMember();
                member.setGuildId(members.get(i).getGuild().getId());
                member.setId(members.get(i).getId());
                member.setName(members.get(i).getUser().getName());
                member.setNickName(members.get(i).getNickname());

                if (members.get(i).getGuild().getMemberById(member.getId()).isOwner()) {

                    member.setPermLevel(Constants.ADMINISTRATOR_PERM);
                }

                else {

                    member.setPermLevel(Constants.USER_PERM);
                }

                discordMembers.add(member);
            }
        }
        return discordMembers;
    }

    private void membersRemoveAll(Set<MemberPOJO> membersList1, Set<MemberPOJO> membersList2) {

        Object[] membersTmp1 = membersList1.toArray();
        Object[] membersTmp2 = membersList2.toArray();

        for (int i = 0; i < membersTmp1.length; i++) {

            String idTmp = (((MemberPOJO) membersTmp1[i]).getId());

            for (int j = 0; j < membersTmp2.length; j++) {

                String idToCompare = (((MemberPOJO) membersTmp2[j]).getId());

                if (idTmp.equals(idToCompare)) {

                    membersList1.remove((MemberPOJO) membersTmp1[i]);
                }
            }
        }
    }

    private void guildsRemoveAll(Set<GuildPOJO> guildsList1, Set<GuildPOJO> guildsList2) {

        Object[] guildsTmp1 = guildsList1.toArray();
        Object[] guildsTmp2 = guildsList2.toArray();

        for (int i = 0; i < guildsTmp1.length; i++) {

            String idTmp = (((GuildPOJO) guildsTmp1[i]).getId());

            for (int j = 0; j < guildsTmp2.length; j++) {

                String idToCompare = (((GuildPOJO) guildsTmp2[j]).getId());

                if (idTmp.equals(idToCompare)) {

                    guildsList1.remove((GuildPOJO) guildsTmp1[i]);
                }
            }
        }
    }

}