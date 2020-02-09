package commands.moderator;

import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

public class Purge {

    private GuildMessageReceivedEvent event;
    private String[] args;

    public Purge(GuildMessageReceivedEvent event, String[] args) {
        this.event = event;
        this.args = args;
    }

    public void purge() {

        try {

            int userValue = Integer.parseInt(args[1]);

            if (userValue > 0 && userValue <= 30) {

                AuditableRestAction<Integer> prune = event.getGuild().prune(userValue);
                int pruneValue = prune.complete();

                if (pruneValue == 0) {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("No member(s) offline for at least **" + userValue + "** days.")
                            .queueAfter(500, TimeUnit.MILLISECONDS);
                }

                else {

                    prune.queue();
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("**" + pruneValue + "** member(s) successfully kicked !")
                            .queueAfter(500, TimeUnit.MILLISECONDS);
                }
            }

            else {

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Please enter a number between **1** and **30**.").queueAfter(500,
                        TimeUnit.MILLISECONDS);
            }

        } catch (NumberFormatException e) {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Please enter a number.").queueAfter(500, TimeUnit.MILLISECONDS);

        } catch (InsufficientPermissionException e) {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Missing permission: **" + Permission.KICK_MEMBERS + "**.").queueAfter(500,
                    TimeUnit.MILLISECONDS);
        }
    }

}