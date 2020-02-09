package commands.moderator;

import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

public class Slowmode {

    private GuildMessageReceivedEvent event;
    private String[] args;

    public Slowmode(GuildMessageReceivedEvent event, String[] args) {
        this.event = event;
        this.args = args;
    }

    public void slowmode() {

        try {

            if (Integer.parseInt(args[1]) == 0) {

                if (event.getChannel().getSlowmode() == 0) {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Slowmode is already **disabled** !").queueAfter(500,
                            TimeUnit.MILLISECONDS);
                }

                else {

                    event.getChannel().getManager().setSlowmode(Integer.parseInt(args[1])).queue();
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Slowmode is now **disabled** !").queueAfter(500,
                            TimeUnit.MILLISECONDS);
                }
            }

            else if (Integer.parseInt(args[1]) == event.getChannel().getSlowmode()) {

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("This value has already been defined.").queueAfter(500,
                        TimeUnit.MILLISECONDS);
            }

            else if (Integer.parseInt(args[1]) > 0 && Integer.parseInt(args[1]) <= 30) {

                event.getChannel().getManager().setSlowmode(Integer.parseInt(args[1])).queue();
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Slowmode is now **enabled** !" + " (" + args[1] + "s).").queueAfter(500,
                        TimeUnit.MILLISECONDS);
            }

            else {

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Please enter a number between **0** and **30**.").queueAfter(500,
                        TimeUnit.MILLISECONDS);
            }

        } catch (InsufficientPermissionException e) {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Missing permission: **" + Permission.MESSAGE_MANAGE + "** or **"
                    + Permission.MANAGE_CHANNEL + "**.").queueAfter(500, TimeUnit.MILLISECONDS);

        } catch (IllegalStateException e) {

        } catch (NumberFormatException e) {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Please enter a number.").queueAfter(500, TimeUnit.MILLISECONDS);
        }
    }

}