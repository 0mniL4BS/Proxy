package commands.user;

import java.util.concurrent.TimeUnit;

import dao.DAO;
import dao.DAOFactory;
import dao.pojo.PermissionPOJO;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import proxy.Embed;

public class MemberInfo {

    private GuildMessageReceivedEvent event;
    private Member member;

    public MemberInfo(GuildMessageReceivedEvent event) {
        this.event = event;
        member = event.getMember();
    }

    public MemberInfo(GuildMessageReceivedEvent event, String[] args) {
        this.event = event;
        member = event.getGuild()
                .getMemberById(args[1].substring(args[1].indexOf('@') + 1, args[1].indexOf('>')).replace("!", ""));
    }

    public void memberInfo() {

        DAO<PermissionPOJO> permissionDao = DAOFactory.getPermissionDAO();
        PermissionPOJO permission = permissionDao.find(event.getGuild().getId(), member.getId());

        Embed embed = new Embed();
        embed.memberInfo(event, member, permission);
        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
    }

}