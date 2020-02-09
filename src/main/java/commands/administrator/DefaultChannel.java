package commands.administrator;

import java.util.List;
import java.util.concurrent.TimeUnit;

import dao.DAO;
import dao.DAOFactory;
import dao.pojo.GuildPOJO;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class DefaultChannel {

    private GuildMessageReceivedEvent event;
    private String[] args;

    public DefaultChannel(GuildMessageReceivedEvent event, String[] args) {
        this.event = event;
        this.args = args;
    }

    public void defaultchannel() {

        try {

            DAO<GuildPOJO> guildDao = DAOFactory.getGuildDAO();
            GuildPOJO guild = guildDao.find(event.getGuild().getId(), null);

            String textChannelId = args[1].substring(args[1].indexOf('#') + 1, args[1].indexOf('>'));

            if (isTextChannelList(event, textChannelId)) {

                if (textChannelId.equals(guild.getDefChan())) {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Default notification channel " + "<#" + guild.getDefChan() + ">"
                            + " has already been defined.").queueAfter(500, TimeUnit.MILLISECONDS);
                }

                else {

                    guild.setDefChan(textChannelId);
                    guildDao.update(guild);
                    event.getChannel().sendTyping().queue();
                    event.getChannel()
                            .sendMessage("Default notification channel is now " + "<#" + guild.getDefChan() + ">.")
                            .queueAfter(500, TimeUnit.MILLISECONDS);
                }
            }

            else {

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Please mention a valid text channel.").queueAfter(500,
                        TimeUnit.MILLISECONDS);
            }

        } catch (NullPointerException | StringIndexOutOfBoundsException e) {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Please mention a valid text channel.").queueAfter(500,
                    TimeUnit.MILLISECONDS);
        }
    }

    private boolean isTextChannelList(GuildMessageReceivedEvent event, String textChannelId) {

        List<TextChannel> textChannels = event.getGuild().getTextChannels();

        for (int i = 0; i < textChannels.size(); i++) {

            if (textChannelId.equalsIgnoreCase(textChannels.get(i).getId())) {
                return true;
            }
        }
        return false;
    }

}