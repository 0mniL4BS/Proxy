package listeners.guild;

import dao.DAO;
import dao.DAOFactory;
import dao.pojo.GuildPOJO;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import proxy.Constants;

public class GuildDefChan extends ListenerAdapter {

    @Override
    public void onTextChannelDelete(TextChannelDeleteEvent event) {

        if (!event.getGuild().getId().equals(Constants.DISCORDBOTLIST_ID)) {

            DAO<GuildPOJO> guildDao = DAOFactory.getGuildDAO();
            GuildPOJO guild = guildDao.find(event.getGuild().getId(), null);

            if (event.getChannel().getId().equals(guild.getDefChan())) {

                guild.setDefChan(null);
                guildDao.update(guild);
            }
        }
    }

}