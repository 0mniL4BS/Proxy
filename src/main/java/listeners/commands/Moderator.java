package listeners.commands;

import java.util.List;
import java.util.concurrent.TimeUnit;

import commands.moderator.Bans;
import commands.moderator.DeleteMessages;
import commands.moderator.Kick;
import commands.moderator.Purge;
import commands.moderator.Resetchan;
import commands.moderator.Slowmode;
import commands.moderator.Softban;
import commands.moderator.Unban;
import dao.DAO;
import dao.DAOFactory;
import dao.pojo.MemberPOJO;
import net.dv8tion.jda.api.entities.Guild.Ban;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import proxy.Constants;

public class Moderator extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        if (!event.getGuild().getId().equals(Constants.DISCORDBOTLIST_ID)) {

            Constants.getPrefix(event);
            String[] args = event.getMessage().getContentRaw().split("\\s+");

            DAO<MemberPOJO> authorDao = DAOFactory.getMemberDAO();
            MemberPOJO author = authorDao.find(event.getGuild().getId(), event.getAuthor().getId());

            if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_CLEAN)) {

                if (author.getPermLevel() >= Constants.MODERATOR_PERM) {

                    if (args.length == 2) {

                        DeleteMessages messages = new DeleteMessages(event, args);
                        messages.delete();
                    }

                    else {

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Please specify the number of message(s) to delete.")
                                .queueAfter(500, TimeUnit.MILLISECONDS);
                    }
                }

                else {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Permission level too low.").queueAfter(500, TimeUnit.MILLISECONDS);
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_SLOWMODE)) {

                if (author.getPermLevel() >= Constants.MODERATOR_PERM) {

                    if (args.length == 2) {

                        Slowmode textChannel = new Slowmode(event, args);
                        textChannel.slowmode();
                    }

                    else {

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Please specify the duration of the slowmode.").queueAfter(500,
                                TimeUnit.MILLISECONDS);
                    }
                }

                else {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Permission level too low.").queueAfter(500, TimeUnit.MILLISECONDS);
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_KICK)) {

                if (author.getPermLevel() >= Constants.MODERATOR_PERM) {

                    if (args.length == 2) {

                        Kick member = new Kick(event, args, author);
                        member.kick();
                    }

                    else {

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Please mention the member to kick (only one).").queueAfter(500,
                                TimeUnit.MILLISECONDS);
                    }
                }

                else {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Permission level too low.").queueAfter(500, TimeUnit.MILLISECONDS);
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_BAN)) {

                if (author.getPermLevel() >= Constants.MODERATOR_PERM) {

                    if (args.length == 2) {

                        Bans member = new Bans(event, args, author);
                        member.ban();
                    }

                    else {

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Please mention the member to ban (only one).").queueAfter(500,
                                TimeUnit.MILLISECONDS);
                    }
                }

                else {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Permission level too low.").queueAfter(500, TimeUnit.MILLISECONDS);
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_SOFTBAN)) {

                if (author.getPermLevel() >= Constants.MODERATOR_PERM) {

                    if (args.length == 2) {

                        Softban member = new Softban(event, args, author);
                        member.softban();
                    }

                    else {

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Please mention the member to kick (only one).").queueAfter(500,
                                TimeUnit.MILLISECONDS);
                    }
                }

                else {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Permission level too low.").queueAfter(500, TimeUnit.MILLISECONDS);
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_UNBAN)) {

                if (author.getPermLevel() >= Constants.MODERATOR_PERM) {

                    List<Ban> banList = event.getGuild().retrieveBanList().complete();

                    if (args.length == 1) {

                        if (banList.isEmpty()) {

                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage("No banned member(s).").queueAfter(500,
                                    TimeUnit.MILLISECONDS);
                        }

                        else {

                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage("Please enter a number from the banlist (only one).")
                                    .queueAfter(500, TimeUnit.MILLISECONDS);
                        }
                    }

                    else if (args.length == 2) {

                        Unban member = new Unban(event, args);
                        member.unban(banList);
                    }

                    else {

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Please enter a number from the banlist (only one).")
                                .queueAfter(500, TimeUnit.MILLISECONDS);
                    }
                }

                else {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Permission level too low.").queueAfter(500, TimeUnit.MILLISECONDS);
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_PURGE)) {

                if (author.getPermLevel() >= Constants.MODERATOR_PERM) {

                    if (args.length == 2) {

                        Purge members = new Purge(event, args);
                        members.purge();
                    }

                    else {
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Please specify the number of days to purge.").queueAfter(500,
                                TimeUnit.MILLISECONDS);
                    }
                }

                else {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Permission level too low.").queueAfter(500, TimeUnit.MILLISECONDS);
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_RESETCHAN)) {

                if (author.getPermLevel() >= Constants.MODERATOR_PERM) {

                    if (args.length == 1) {

                        Resetchan textChannel = new Resetchan(event);
                        textChannel.resetchan();
                    }
                }

                else {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Permission level too low.").queueAfter(500, TimeUnit.MILLISECONDS);
                }
            }
        }
    }

}