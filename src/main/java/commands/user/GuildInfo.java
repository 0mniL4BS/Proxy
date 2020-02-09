package commands.user;

import java.util.concurrent.TimeUnit;

import dao.DAO;
import dao.DAOFactory;
import dao.pojo.GuildPOJO;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import proxy.Embed;

public class GuildInfo {

    private GuildMessageReceivedEvent event;
    private Guild gld;
    private GuildPOJO guild;
    private DAO<GuildPOJO> guildDao;

    public GuildInfo(GuildMessageReceivedEvent event) {
        this.event = event;
        gld = event.getGuild();
        guildDao = DAOFactory.getGuildDAO();
        guild = guildDao.find(event.getGuild().getId(), null);
    }

    public void guildInfo() {

        Embed embed = new Embed();
        embed.serverInfo(gld, guild);
        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
    }

}