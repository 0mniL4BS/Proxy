package commands.administrator;

import java.util.concurrent.TimeUnit;

import dao.DAO;
import dao.DAOFactory;
import dao.pojo.GuildPOJO;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import proxy.Constants;

public class Disable {

    private GuildMessageReceivedEvent event;
    private String[] args;

    public Disable(GuildMessageReceivedEvent event, String[] args) {
        this.event = event;
        this.args = args;
    }

    public void disable() {

        DAO<GuildPOJO> guildDao = DAOFactory.getGuildDAO();
        GuildPOJO guild = guildDao.find(event.getGuild().getId(), null);

        if (args[1].equalsIgnoreCase(Constants.CMD_DEFCHAN)) {

            disableDefaultChannel(guildDao, guild);
        }

        else if (args[1].equalsIgnoreCase(Constants.CMD_DEFROLE)) {

            disableDefaultRole(guildDao, guild);
        }

        else {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(
                    "Please mention **" + Constants.CMD_DEFCHAN + "** or **" + Constants.CMD_DEFROLE + "** command.")
                    .queueAfter(500, TimeUnit.MILLISECONDS);
        }
    }

    private void disableDefaultChannel(DAO<GuildPOJO> guildDao, GuildPOJO guild) {

        String defChan = guild.getDefChan();

        if (defChan != null && !defChan.isEmpty()) {

            guild.setDefChan(null);
            guildDao.update(guild);
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(
                    "Default notification channel is now **disabled**, you will no longer receive notifications when a member join or leave the server.")
                    .queueAfter(500, TimeUnit.MILLISECONDS);
        }

        else {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Default notification channel is already disabled.").queueAfter(500,
                    TimeUnit.MILLISECONDS);
        }
    }

    private void disableDefaultRole(DAO<GuildPOJO> guildDao, GuildPOJO guild) {

        String defRole = guild.getDefRole();

        if (defRole != null && !defRole.isEmpty()) {

            guild.setDefRole(null);
            guildDao.update(guild);
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(
                    "Default role is now **disabled**, the bot will not longer add role to members who join the server.")
                    .queueAfter(500, TimeUnit.MILLISECONDS);
        }

        else {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Default role is already disabled.").queueAfter(500, TimeUnit.MILLISECONDS);
        }
    }

}