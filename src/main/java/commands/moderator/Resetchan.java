package commands.moderator;

import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

public class Resetchan {

    private GuildMessageReceivedEvent event;

    public Resetchan(GuildMessageReceivedEvent event) {
        this.event = event;
    }

    public void resetchan() {

        try {

            event.getChannel().createCopy().queue();
            event.getChannel().delete().queue();

        } catch (InsufficientPermissionException e) {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Missing permission: **" + Permission.MANAGE_CHANNEL + "**.").queueAfter(500,
                    TimeUnit.MILLISECONDS);

        } catch (ErrorResponseException e) {
        }
    }

}