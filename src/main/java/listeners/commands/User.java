package listeners.commands;

import java.util.concurrent.TimeUnit;

import commands.user.AdministratorList;
import commands.user.BanList;
import commands.user.GuildInfo;
import commands.user.MemberInfo;
import commands.user.ModeratorList;
import commands.user.SendImage;
import commands.user.TextChannelInfo;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import proxy.Constants;
import proxy.Embed;

public class User extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        if (!event.getGuild().getId().equals(Constants.DISCORDBOTLIST_ID)) {

            Constants.getPrefix(event);
            String[] args = event.getMessage().getContentRaw().split("\\s+");

            if (event.getMessage().getMentionedUsers().contains(event.getJDA().getSelfUser()) && args.length == 1) {

                Member mbr = event.getGuild().getMemberById(Constants.BOT_ID);

                Embed embed = new Embed();
                embed.memberInfo(event, mbr, null);
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_INFO)) {

                if (args.length == 1) {

                    Embed embed = new Embed();
                    embed.info(event);
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_PING)) {

                if (args.length == 1) {

                    Embed embed = new Embed();
                    embed.ping(event);
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_UPTIME)) {

                if (args.length == 1) {

                    Embed embed = new Embed();
                    embed.uptime();
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_GUILD_INFO)) {

                if (args.length > 0 && args.length < 2) {

                    GuildInfo guild = new GuildInfo(event);
                    guild.guildInfo();
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_MEMBER_INFO)) {

                if (args.length == 1) {

                    MemberInfo member = new MemberInfo(event);
                    member.memberInfo();
                }

                else if (args.length == 2) {

                    try {

                        MemberInfo member = new MemberInfo(event, args);
                        member.memberInfo();

                    } catch (NumberFormatException | StringIndexOutOfBoundsException e) {

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Please mention a member.").queueAfter(300,
                                TimeUnit.MILLISECONDS);
                    }
                }

                else {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Please mention a member (only one).").queueAfter(300,
                            TimeUnit.MILLISECONDS);
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_TEXTCHAN_INFO)) {

                if (args.length == 2) {

                    try {

                        TextChannelInfo guild = new TextChannelInfo(event, args);
                        guild.textChannelInfo();

                    } catch (NumberFormatException | StringIndexOutOfBoundsException e) {

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Please mention a text channel.").queueAfter(300,
                                TimeUnit.MILLISECONDS);
                    }
                }

                else {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Please mention a text channel (only one).").queueAfter(300,
                            TimeUnit.MILLISECONDS);
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_BANLIST)) {

                BanList banlist = new BanList(event, args);
                banlist.showBanList();
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_MODOLIST)) {

                if (args.length == 1) {

                    ModeratorList modolist = new ModeratorList(event);
                    modolist.showModeratorList();
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_ADMINLIST)) {

                if (args.length == 1) {

                    AdministratorList adminlist = new AdministratorList(event);
                    adminlist.showAdministratorList();
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_RICARDO)) {

                if (args.length == 1) {

                    SendImage image = new SendImage(event, Constants.PATH_IMG_RICARDO);
                    image.sendImage();
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_ISSOU)) {

                if (args.length == 1) {

                    SendImage image = new SendImage(event, Constants.PATH_IMG_ISSOU);
                    image.sendImage();
                }
            }
        }
    }

}