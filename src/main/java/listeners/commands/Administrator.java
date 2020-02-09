package listeners.commands;

import java.util.concurrent.TimeUnit;

import commands.administrator.DefaultChannel;
import commands.administrator.DefaultRole;
import commands.administrator.Disable;
import commands.administrator.Permission;
import commands.administrator.Prefix;
import commands.administrator.Shield;
import dao.DAO;
import dao.DAOFactory;
import dao.pojo.MemberPOJO;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import proxy.Constants;

public class Administrator extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        if (!event.getGuild().getId().equals(Constants.DISCORDBOTLIST_ID)) {

            Constants.getPrefix(event);
            String[] args = event.getMessage().getContentRaw().split("\\s+");

            DAO<MemberPOJO> authorDao = DAOFactory.getMemberDAO();
            MemberPOJO author = authorDao.find(event.getGuild().getId(), event.getAuthor().getId());

            if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_PREFIX)) {

                if (author.getPermLevel() >= Constants.ADMINISTRATOR_PERM) {

                    if (args.length == 2) {

                        Prefix guild = new Prefix(event, args);
                        guild.prefix();
                    }

                    else {

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Please specify a prefix.").queueAfter(500,
                                TimeUnit.MILLISECONDS);
                    }
                }

                else {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Permission level too low.").queueAfter(500, TimeUnit.MILLISECONDS);
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_PERM)) {

                if (event.getAuthor().getId().equals(event.getGuild().getOwnerId())) {

                    if (args.length == 3) {

                        Permission member = new Permission(event, args);
                        member.permission();
                    }

                    else {

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Please mention a **member** and a **number** between 1 and 3.")
                                .queueAfter(500, TimeUnit.MILLISECONDS);
                    }
                }

                else {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Only the guild owner can define an administrator.").queueAfter(500,
                            TimeUnit.MILLISECONDS);
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_DEFCHAN)) {

                if (author.getPermLevel() >= Constants.ADMINISTRATOR_PERM) {

                    if (args.length == 2) {

                        DefaultChannel guild = new DefaultChannel(event, args);
                        guild.defaultchannel();
                    }

                    else {

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Please mention a text channel (only one).").queueAfter(500,
                                TimeUnit.MILLISECONDS);
                    }
                }

                else {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Permission level too low.").queueAfter(500, TimeUnit.MILLISECONDS);
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_DEFROLE)) {

                if (author.getPermLevel() >= Constants.ADMINISTRATOR_PERM) {

                    if (args.length == 2) {

                        DefaultRole guild = new DefaultRole(event, args);
                        guild.defaultrole();
                    }

                    else {

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Please mention a valid role (only one).").queueAfter(500,
                                TimeUnit.MILLISECONDS);
                    }
                }

                else {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Permission level too low.").queueAfter(500, TimeUnit.MILLISECONDS);
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_SHIELD)) {

                if (author.getPermLevel() >= Constants.ADMINISTRATOR_PERM) {

                    if (args.length == 2) {

                        Shield guild = new Shield(event, args);
                        guild.shield();
                    }

                    else {

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Please specify **on** or **off**.").queueAfter(500,
                                TimeUnit.MILLISECONDS);
                    }
                }

                else {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Permission level too low.").queueAfter(500, TimeUnit.MILLISECONDS);
                }
            }

            else if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_DISABLE)) {

                if (author.getPermLevel() >= Constants.ADMINISTRATOR_PERM) {

                    if (args.length == 2) {

                        Disable guild = new Disable(event, args);
                        guild.disable();
                    }

                    else {

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Please mention **" + Constants.CMD_DEFCHAN + "** or **"
                                + Constants.CMD_DEFROLE + "** command.").queueAfter(500, TimeUnit.MILLISECONDS);
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