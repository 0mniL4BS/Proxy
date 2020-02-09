package listeners.guild;

import dao.DAO;
import dao.DAOFactory;
import dao.pojo.MemberPOJO;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateOwnerEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import proxy.Constants;

public class GuildOwner extends ListenerAdapter {

    private MemberPOJO member;
    private DAO<MemberPOJO> memberDao;

    @Override
    public void onGuildUpdateOwner(GuildUpdateOwnerEvent event) {

        if (!event.getGuild().getId().equals(Constants.DISCORDBOTLIST_ID)) {

            memberDao = DAOFactory.getMemberDAO();
            updatePermOldOwner(event);
            updatePermNewOwner(event);

        }
    }

    private void updatePermOldOwner(GuildUpdateOwnerEvent event) {

        member = memberDao.find(event.getGuild().getId(), event.getOldOwnerId());
        member.setPermLevel(Constants.USER_PERM);

        memberDao.update(member);
    }

    private void updatePermNewOwner(GuildUpdateOwnerEvent event) {

        member = memberDao.find(event.getGuild().getId(), event.getNewOwnerId());
        member.setPermLevel(Constants.ADMINISTRATOR_PERM);

        memberDao.update(member);
    }

}