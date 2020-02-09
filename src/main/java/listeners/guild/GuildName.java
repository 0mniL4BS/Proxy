package listeners.guild;

import dao.DAO;
import dao.DAOFactory;
import dao.pojo.GuildPOJO;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildName extends ListenerAdapter {

    @Override
    public void onGuildUpdateName(GuildUpdateNameEvent event) {

        DAO<GuildPOJO> guildDao = DAOFactory.getGuildDAO();
        GuildPOJO guild = guildDao.find(event.getGuild().getId(), null);
        guild.setName(event.getNewName());
        guildDao.update(guild);
    }

}