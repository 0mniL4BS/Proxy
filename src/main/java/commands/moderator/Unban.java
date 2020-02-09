package commands.moderator;

import java.util.List;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild.Ban;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

public class Unban {

    private GuildMessageReceivedEvent event;
    private String[] args;

    public Unban(GuildMessageReceivedEvent event, String[] args) {
        this.event = event;
        this.args = args;
    }

    public void unban(List<Ban> banList) {

        try {

            int unbanNumber = Integer.parseInt(args[1]) - 1;

            if (banList.isEmpty()) {

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("No banned member(s).").queueAfter(500, TimeUnit.MILLISECONDS);
            }

            else if (unbanNumber >= 0 && unbanNumber < banList.size()) {

                event.getGuild().unban(banList.get(unbanNumber).getUser().getId()).queue();
                event.getChannel().sendTyping().queue();
                event.getChannel()
                        .sendMessage(
                                "**" + banList.get(unbanNumber).getUser().getName() + "** is successfully unbanned !")
                        .queueAfter(500, TimeUnit.MILLISECONDS);
            }

        } catch (NumberFormatException e) {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Please enter a number.").queueAfter(500, TimeUnit.MILLISECONDS);

        } catch (InsufficientPermissionException e) {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Missing permission: **" + Permission.BAN_MEMBERS + "**.").queueAfter(500,
                    TimeUnit.MILLISECONDS);

        } catch (IllegalArgumentException e) {

        } catch (ErrorResponseException e) {
        }
    }

}