package listeners.member;

import java.util.List;

import dao.DAO;
import dao.DAOFactory;
import dao.pojo.MemberPOJO;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import proxy.Constants;

public class MemberName extends ListenerAdapter {

    @Override
    public void onUserUpdateName(UserUpdateNameEvent event) {

        if (!event.getUser().isBot()) {

            List<Guild> guilds = event.getJDA().getGuilds();

            for (int i = 0; i < guilds.size(); i++) {

                if (!guilds.get(i).getId().equals(Constants.DISCORDBOTLIST_ID)
                        && guilds.get(i).getMemberById(event.getUser().getId()) != null) {

                    DAO<MemberPOJO> memberDao = DAOFactory.getMemberDAO();
                    MemberPOJO member = memberDao.find(guilds.get(i).getId(), event.getUser().getId());
                    member.setName(event.getNewName());

                    memberDao.update(member);
                }
            }
        }
    }

    @Override
    public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent event) {

        if (!event.getGuild().getId().equals(Constants.DISCORDBOTLIST_ID) && !event.getUser().isBot()) {

            DAO<MemberPOJO> memberDao = DAOFactory.getMemberDAO();
            MemberPOJO member = memberDao.find(event.getGuild().getId(), event.getUser().getId());
            member.setNickName(event.getNewNickname());

            memberDao.update(member);
        }
    }

}