package listeners.commands;

import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import proxy.Constants;
import proxy.Embed;

public class Help extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        if (!event.getGuild().getId().equals(Constants.DISCORDBOTLIST_ID)) {

            Constants.getPrefix(event);
            String[] args = event.getMessage().getContentRaw().split("\\s+");

            if (args[0].equalsIgnoreCase(Constants.prefix + Constants.CMD_HELP)) {

                if (args[1].equalsIgnoreCase(Constants.CMD_PREFIX)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_PREFIX, "Sets the prefix of the server.\n\nExample: `" + Constants.prefix
                            + Constants.CMD_PREFIX + " $`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_PERM)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_PERM,
                            "Sets the permission level of the mentionned member.\n\nExample: `" + Constants.prefix
                                    + Constants.CMD_PERM + " @Unknown " + Constants.USER_PERM + "`\n\n`"
                                    + Constants.USER_PERM + "` for User.\n\n`" + Constants.MODERATOR_PERM
                                    + "` for Moderator.\n\n`" + Constants.ADMINISTRATOR_PERM + "` for Administrator.");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_DEFCHAN)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_DEFCHAN, "Sets the default notification channel.\n\nExample: `"
                            + Constants.prefix + Constants.CMD_DEFCHAN + " home`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_DEFROLE)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_DEFROLE,
                            "Sets the default role when a member join the server.\n\nExample: `" + Constants.prefix
                                    + Constants.CMD_DEFROLE + " admin`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_SHIELD)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_SHIELD,
                            "Auto kicks accounts created on the same day they join your server (you can enable or disable the shield).\n\nExample: `"
                                    + Constants.prefix + Constants.CMD_SHIELD + " on`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_DISABLE)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_DISABLE, "Disable default channel or default role.\n\nExample: `"
                            + Constants.prefix + Constants.CMD_DISABLE + " defchan`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_CLEAN)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_CLEAN, "Deletes past messages between 2 and 100.\n\nExample: `"
                            + Constants.prefix + Constants.CMD_CLEAN + " 20`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_SLOWMODE)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_SLOWMODE, "Enables or disables slowmode.\n\nExample: `" + Constants.prefix
                            + Constants.CMD_SLOWMODE + " 5`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_KICK)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_KICK, "Kicks the mentionned member.\n\nExample: `" + Constants.prefix
                            + Constants.CMD_KICK + " @Unknown`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_BAN)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_BAN, "Bans the mentionned member.\n\nExample: `" + Constants.prefix
                            + Constants.CMD_BAN + " @Unknown`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_SOFTBAN)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_SOFTBAN,
                            "Kicks the mentionned member and clear his past messages.\n\nExample: `" + Constants.prefix
                                    + Constants.CMD_SOFTBAN + " @Unknown`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_UNBAN)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_UNBAN, "Unbans a member from the banlist.\n\nExample: `" + Constants.prefix
                            + Constants.CMD_UNBAN + " 3`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_PURGE)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_PURGE,
                            "Kicks all members who were offline for at least the number of days you set.\n\nExample: `"
                                    + Constants.prefix + Constants.CMD_PURGE + " 5`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_RESETCHAN)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_RESETCHAN,
                            "Creates a copy and delete the current text channel.\n\nExample: `" + Constants.prefix
                                    + Constants.CMD_RESETCHAN + "`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_PING)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_PING,
                            "Displays the time in milliseconds that discord took to respond to a request.\n\nExample: `"
                                    + Constants.prefix + Constants.CMD_PING + "`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_UPTIME)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_UPTIME,
                            "Displays the percentage of time the bot has been working and available.\n\nExample: `"
                                    + Constants.prefix + Constants.CMD_UPTIME + "`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_GUILD_INFO)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_GUILD_INFO, "Displays the informations of the server.\n\nExample: `"
                            + Constants.prefix + Constants.CMD_GUILD_INFO + "`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_MEMBER_INFO)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_MEMBER_INFO,
                            "Displays the informations of the mentionned member.\n\nExample: `" + Constants.prefix
                                    + Constants.CMD_MEMBER_INFO + " @Unknown`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_TEXTCHAN_INFO)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_TEXTCHAN_INFO,
                            "Displays the informations of the mentionned text channel.\n\nExample: `" + Constants.prefix
                                    + Constants.CMD_TEXTCHAN_INFO + " #general`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_BANLIST)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_BANLIST,
                            "Displays the members who where banned from the server.\n\nExample: `" + Constants.prefix
                                    + Constants.CMD_BANLIST + "`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_MODOLIST)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_MODOLIST, "Displays the moderators from the server.\n\nExample: `"
                            + Constants.prefix + Constants.CMD_MODOLIST + "`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }

                else if (args[1].equalsIgnoreCase(Constants.CMD_ADMINLIST)) {

                    Embed embed = new Embed();
                    embed.help(Constants.CMD_ADMINLIST, "Displays the administrators from the server.\n\nExample: `"
                            + Constants.prefix + Constants.CMD_ADMINLIST + "`");
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
                }
            }
        }
    }

}