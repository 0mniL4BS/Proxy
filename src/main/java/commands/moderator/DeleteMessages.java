package commands.moderator;

import java.util.List;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

public class DeleteMessages {

    private GuildMessageReceivedEvent event;
    private String[] args;

    public DeleteMessages(GuildMessageReceivedEvent event, String[] args) {
        this.event = event;
        this.args = args;
    }

    public void delete() {

        try {

            event.getMessage().delete().complete();

            if (Integer.parseInt(args[1]) >= 2 && Integer.parseInt(args[1]) <= 100) {

                List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1]))
                        .complete();
                event.getChannel().deleteMessages(messages).queue();
                event.getChannel().sendMessage("**" + messages.size() + "** deleted messages.")
                        .completeAfter(500, TimeUnit.MILLISECONDS).delete().completeAfter(2, TimeUnit.SECONDS);
            }

            else {

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Please enter a number between **2** and **100**.").queueAfter(500,
                        TimeUnit.MILLISECONDS);
            }

        } catch (NumberFormatException e) {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Please enter a number.").queueAfter(500, TimeUnit.MILLISECONDS);

        } catch (IllegalArgumentException e) {

            if (e.getMessage().startsWith("Messages collection may not be empty")) {

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("No messages to delete.").queueAfter(500, TimeUnit.MILLISECONDS);
            }

            else if (e.getMessage().startsWith("Must provide at least 2 or at most 100 messages to be deleted.")) {

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Must provide at least 2 or at most 100 messages to be deleted.")
                        .queueAfter(500, TimeUnit.MILLISECONDS);
            }

            else if (e.getMessage().startsWith("Message Id provided was older than 2 weeks.")) {

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Impossible to delete messages older than 2 weeks.").queueAfter(500,
                        TimeUnit.MILLISECONDS);
            }

        } catch (InsufficientPermissionException e) {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Missing permission: **" + Permission.MESSAGE_MANAGE + "**.").queueAfter(500,
                    TimeUnit.MILLISECONDS);

        } catch (ErrorResponseException e) {
        }
    }

}