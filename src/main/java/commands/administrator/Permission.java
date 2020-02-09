package commands.administrator;

import java.util.concurrent.TimeUnit;

import dao.DAO;
import dao.DAOFactory;
import dao.pojo.MemberPOJO;
import dao.pojo.PermissionPOJO;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Permission {

    private GuildMessageReceivedEvent event;
    private String[] args;

    public Permission(GuildMessageReceivedEvent event, String[] args) {
        this.event = event;
        this.args = args;
    }

    public void permission() {

        String memberId = args[1].substring(args[1].indexOf('@') + 1, args[1].indexOf('>')).replace("!", "");

        try {

            if (Integer.parseInt(args[2]) >= 1 && Integer.parseInt(args[2]) <= 3) {

                if (event.getGuild().getMemberById(memberId).getUser().isBot()) {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Impossible to set a permission for a bot.").queueAfter(500,
                            TimeUnit.MILLISECONDS);
                }

                else {

                    DAO<MemberPOJO> memberDao = DAOFactory.getMemberDAO();
                    MemberPOJO member = memberDao.find(event.getGuild().getId(), memberId);

                    if (Integer.parseInt(args[2]) == member.getPermLevel()) {

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("This member already has this permission level.").queueAfter(500,
                                TimeUnit.MILLISECONDS);
                    }

                    else {

                        member.setPermLevel(Integer.parseInt(args[2]));
                        memberDao.update(member);

                        DAO<PermissionPOJO> permissionDao = DAOFactory.getPermissionDAO();
                        PermissionPOJO permission = permissionDao.find(member.getGuildId(), member.getId());

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage(
                                "<@" + member.getId() + ">" + " is now " + "**" + permission.getName() + "**" + ".")
                                .queueAfter(500, TimeUnit.MILLISECONDS);
                    }
                }
            }

            else {

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Please specify a number between **1** and **3**.").queueAfter(500,
                        TimeUnit.MILLISECONDS);
            }

        } catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Please mention a **member** and a **number** between 1 and 3.")
                    .queueAfter(500, TimeUnit.MILLISECONDS);
        }
    }

}