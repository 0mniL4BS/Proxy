package listeners.guild;

import dao.DAO;
import dao.DAOFactory;
import dao.pojo.GuildPOJO;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import proxy.Constants;

public class GuildDefRole extends ListenerAdapter {

    @Override
    public void onRoleDelete(RoleDeleteEvent event) {

        if (!event.getGuild().getId().equals(Constants.DISCORDBOTLIST_ID)) {

            DAO<GuildPOJO> guildDao = DAOFactory.getGuildDAO();
            GuildPOJO guild = guildDao.find(event.getGuild().getId(), null);

            if (event.getRole().getId().equals(guild.getDefRole())) {

                guild.setDefRole(null);
                guildDao.update(guild);
            }
        }
    }

}