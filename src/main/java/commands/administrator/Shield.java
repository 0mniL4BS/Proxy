package commands.administrator;

import java.util.concurrent.TimeUnit;

import dao.DAO;
import dao.DAOFactory;
import dao.pojo.GuildPOJO;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Shield {

    private GuildMessageReceivedEvent event;
    private String[] args;

    public Shield(GuildMessageReceivedEvent event, String[] args) {
        this.event = event;
        this.args = args;
    }

    public void shield() {

        DAO<GuildPOJO> guildDao = DAOFactory.getGuildDAO();
        GuildPOJO guild = guildDao.find(event.getGuild().getId(), null);

        if (args[1].equalsIgnoreCase("on")) {

            if (guild.getShield()) {

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Shield is already **enabled**.").queueAfter(500, TimeUnit.MILLISECONDS);
            }

            else if (!guild.getShield()) {

                guild.setShield(true);
                guildDao.update(guild);
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Shield is now **enabled**.").queueAfter(500, TimeUnit.MILLISECONDS);
            }
        }

        else if (args[1].equalsIgnoreCase("off")) {

            if (!guild.getShield()) {

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Shield is already **disabled**.").queueAfter(500,
                        TimeUnit.MILLISECONDS);
            }

            else if (guild.getShield()) {

                guild.setShield(false);
                guildDao.update(guild);
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Shield is now **disabled**.").queueAfter(500, TimeUnit.MILLISECONDS);
            }
        }

        else {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Please specify **on** or **off**.").queueAfter(500, TimeUnit.MILLISECONDS);
        }
    }

}