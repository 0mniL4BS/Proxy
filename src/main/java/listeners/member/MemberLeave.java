package listeners.member;

import dao.DAO;
import dao.DAOFactory;
import dao.pojo.MemberPOJO;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import proxy.Constants;

public class MemberLeave extends ListenerAdapter {

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {

        if (!event.getGuild().getId().equals(Constants.DISCORDBOTLIST_ID) && !event.getUser().isBot()) {

            removeMemberDatas(event);
        }
    }

    private void removeMemberDatas(GuildMemberLeaveEvent event) {

        DAO<MemberPOJO> memberDao = DAOFactory.getMemberDAO();
        MemberPOJO member = memberDao.find(event.getGuild().getId(), event.getUser().getId());

        memberDao.delete(member);
    }

}