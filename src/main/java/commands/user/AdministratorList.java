package commands.user;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import dao.DAO;
import dao.DAOFactory;
import dao.GuildDAO;
import dao.pojo.GuildPOJO;
import dao.pojo.MemberPOJO;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import proxy.Constants;
import proxy.Embed;

public class AdministratorList {

    private GuildMessageReceivedEvent event;

    public AdministratorList(GuildMessageReceivedEvent event) {
        this.event = event;
    }

    public void showAdministratorList() {

        DAO<GuildPOJO> guildDao = DAOFactory.getGuildDAO();
        Set<MemberPOJO> members = ((GuildDAO) guildDao).findMembers(event.getGuild().getId());

        Object[] membersTmp = members.toArray();

        for (int i = 0; i < membersTmp.length; i++) {

            if (((MemberPOJO) membersTmp[i]).getPermLevel() != Constants.ADMINISTRATOR_PERM) {

                members.remove(membersTmp[i]);
            }
        }

        if (members.isEmpty()) {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("No administrator(s).").queueAfter(300, TimeUnit.MILLISECONDS);
        }

        else {

            Embed embed = new Embed();
            embed.adminList(members);
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
        }
    }

}