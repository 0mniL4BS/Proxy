package listeners.guild;

import dao.DAO;
import dao.DAOFactory;
import dao.GuildDAO;
import dao.pojo.GuildPOJO;
import dao.pojo.POJOFactory;
import listeners.onconnection.ConnectionDBL;
import listeners.onconnection.ConnectionDiscord;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import proxy.Constants;

public class GuildLeave extends ListenerAdapter {

    private GuildPOJO guild;
    private DAO<GuildPOJO> guildDao;

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {

        if (!event.getGuild().getId().equals(Constants.DISCORDBOTLIST_ID)) {
            removeMembers(event);
            removeGuild(event);
        }
        ConnectionDBL.getInstance().setStats(ConnectionDiscord.getInstance().getGuilds().size());
    }

    private void removeMembers(GuildLeaveEvent event) {

        guildDao = DAOFactory.getGuildDAO();
        guild = POJOFactory.getGuild();
        guild.setMembers(((GuildDAO) guildDao).findMembers(event.getGuild().getId()));

        ((GuildDAO) guildDao).deleteMembers(guild.getMembers());
    }

    private void removeGuild(GuildLeaveEvent event) {

        guildDao = DAOFactory.getGuildDAO();
        guild = guildDao.find(event.getGuild().getId(), null);

        guildDao.delete(guild);
    }

}