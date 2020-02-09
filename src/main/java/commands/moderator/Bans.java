package commands.moderator;

import java.util.List;
import java.util.concurrent.TimeUnit;

import dao.DAO;
import dao.DAOFactory;
import dao.pojo.MemberPOJO;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import proxy.Constants;

public class Bans {

    private GuildMessageReceivedEvent event;
    private String[] args;
    private MemberPOJO author;

    public Bans(GuildMessageReceivedEvent event, String[] args, MemberPOJO author) {
        this.event = event;
        this.args = args;
        this.author = author;
    }

    public void ban() {

        try {

            List<Member> mentionnedMembers = event.getMessage().getMentionedMembers();

            DAO<MemberPOJO> memberDao = DAOFactory.getMemberDAO();
            MemberPOJO member = memberDao.find(event.getGuild().getId(), mentionnedMembers.get(0).getId());

            if (mentionnedMembers.get(0).getId().equals(Constants.BOT_ID)) {

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Impossible to ban " + "<@" + Constants.BOT_ID + ">.").queueAfter(500,
                        TimeUnit.MILLISECONDS);
            }

            if (member.getId() != null) {

                if (member.getId().equals(event.getAuthor().getId())) {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Impossible to ban yourself.").queueAfter(500,
                            TimeUnit.MILLISECONDS);
                }

                if (member.getId().equals(event.getGuild().getOwnerId())) {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Impossible to ban the guild owner.").queueAfter(500,
                            TimeUnit.MILLISECONDS);
                }

                if (((author.getPermLevel() == Constants.MODERATOR_PERM)
                        && (member.getPermLevel() == Constants.MODERATOR_PERM))
                        || ((author.getPermLevel() == Constants.ADMINISTRATOR_PERM)
                                && member.getPermLevel() == Constants.ADMINISTRATOR_PERM)
                                && (!event.getAuthor().getId().equals(event.getGuild().getOwnerId()))) {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Impossible to ban a member with the same permission.")
                            .queueAfter(500, TimeUnit.MILLISECONDS);
                }

                if (author.getPermLevel() == Constants.MODERATOR_PERM
                        && (member.getPermLevel() == Constants.ADMINISTRATOR_PERM)) {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Impossible to ban an administrator.").queueAfter(500,
                            TimeUnit.MILLISECONDS);
                }
            }

            else {

                try {

                    event.getGuild().ban(mentionnedMembers.get(0).getId(), 1).queue();
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("**" + args[1] + "** is successfully banned !").queueAfter(500,
                            TimeUnit.MILLISECONDS);

                } catch (InsufficientPermissionException e) {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Missing permission: **" + Permission.BAN_MEMBERS + "**.")
                            .queueAfter(500, TimeUnit.MILLISECONDS);

                } catch (HierarchyException e) {

                    event.getChannel().sendTyping().queue();
                    event.getChannel()
                            .sendMessage("Impossible to ban a member with the same or a higher permission than yours.")
                            .queueAfter(500, TimeUnit.MILLISECONDS);

                } catch (IllegalArgumentException e) {

                } catch (ErrorResponseException e) {
                }
            }

        } catch (IndexOutOfBoundsException e) {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Please mention a member.").queueAfter(500, TimeUnit.MILLISECONDS);
        }
    }

}