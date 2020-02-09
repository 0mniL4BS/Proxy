package commands.user;

import java.util.List;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild.Ban;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import proxy.Embed;

public class BanList {

    private GuildMessageReceivedEvent event;
    private String[] args;

    public BanList(GuildMessageReceivedEvent event, String[] args) {
        this.event = event;
        this.args = args;
    }

    public void showBanList() {

        try {

            List<Ban> bans = event.getGuild().retrieveBanList().complete();

            if (args.length == 1) {

                if (bans.isEmpty()) {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("No banned member(s).").queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else {

                    Embed embed = new Embed();
                    embed.banList(bans);
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }
            }

            else if (args.length == 2 && (Integer.parseInt(args[1]) > 0 && Integer.parseInt(args[1]) <= bans.size())) {

                if (bans.isEmpty()) {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("No banned member(s).").queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else {

                    int banNumber = Integer.parseInt(args[1]) - 1;

                    if (banNumber >= 0 && banNumber < bans.size()) {

                        Embed embed = new Embed();
                        embed.bannedMemberInfo(bans, banNumber);
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                    }

                    else {

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Please enter a value from the list.").queueAfter(300,
                                TimeUnit.MILLISECONDS);
                    }
                }
            }

            else {

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Invalid command.").queueAfter(300, TimeUnit.MILLISECONDS);
            }

        } catch (NumberFormatException e) {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Invalid command.").queueAfter(300, TimeUnit.MILLISECONDS);

        } catch (InsufficientPermissionException e) {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Missing permission: **" + Permission.BAN_MEMBERS + "**.").queueAfter(500,
                    TimeUnit.MILLISECONDS);
        }
    }

}