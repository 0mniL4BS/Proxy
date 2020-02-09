package commands.administrator;

import java.util.concurrent.TimeUnit;

import dao.DAO;
import dao.DAOFactory;
import dao.pojo.GuildPOJO;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Prefix {

    private GuildMessageReceivedEvent event;
    private String[] args;

    public Prefix(GuildMessageReceivedEvent event, String[] args) {
        this.event = event;
        this.args = args;
    }

    public void prefix() {

        if (args[1].length() <= 2) {

            DAO<GuildPOJO> guildDao = DAOFactory.getGuildDAO();
            GuildPOJO guild = guildDao.find(event.getGuild().getId(), null);

            if (guild.getPrefix().equalsIgnoreCase(args[1])) {

                event.getChannel().sendTyping().queue();
                event.getChannel()
                        .sendMessage("Prefix " + "**" + guild.getPrefix() + "**" + " has already been defined.")
                        .queueAfter(500, TimeUnit.MILLISECONDS);
            }

            else {

                guild.setPrefix(args[1]);
                guildDao.update(guild);
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Prefix is now: " + "**" + guild.getPrefix() + "**").queueAfter(500,
                        TimeUnit.MILLISECONDS);
            }
        }

        else {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Two characters maximum are allowed.").queueAfter(500,
                    TimeUnit.MILLISECONDS);
        }
    }

}