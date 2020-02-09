package listeners.member;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import dao.DAO;
import dao.DAOFactory;
import dao.pojo.GuildPOJO;
import dao.pojo.MemberPOJO;
import dao.pojo.POJOFactory;
import dao.pojo.PermissionPOJO;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import proxy.Constants;
import proxy.Embed;

public class MemberJoin extends ListenerAdapter {

    private GuildPOJO guild;
    private PermissionPOJO permission;

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {

        if (!event.getGuild().getId().equals(Constants.DISCORDBOTLIST_ID) && !event.getUser().isBot()) {

            DAO<GuildPOJO> guildDao = DAOFactory.getGuildDAO();
            guild = guildDao.find(event.getGuild().getId(), null);

            if (guild.getShield()) {

                boolean isFake = event.getUser().getTimeCreated().format(DateTimeFormatter.ofPattern("dd-MM"))
                        .equals(DateTimeFormatter.ofPattern("dd-MM").format(LocalDate.now()));

                if (isFake) {
                    event.getMember().kick().queue();
                }

                else {
                    addMemberDatas(event);
                    addDefRole(event);
                    sendWelcomeMessage(event);
                }
            }

            else if (!guild.getShield()) {
                addMemberDatas(event);
                addDefRole(event);
                sendWelcomeMessage(event);
            }
        }
    }

    private void addMemberDatas(GuildMemberJoinEvent event) {

        DAO<MemberPOJO> memberDao = DAOFactory.getMemberDAO();
        MemberPOJO member = POJOFactory.getMember();
        member.setGuildId(event.getGuild().getId());
        member.setId(event.getUser().getId());
        member.setName(event.getUser().getName());
        member.setNickName(null);

        memberDao.create(member);

        DAO<PermissionPOJO> permissionDao = DAOFactory.getPermissionDAO();
        permission = permissionDao.find(member.getGuildId(), member.getId());
    }

    private void addDefRole(GuildMemberJoinEvent event) {

        if (guild.getDefRole() != null && !guild.getDefRole().isEmpty()) {

            for (int i = 0; i < event.getGuild().getRoles().size(); i++) {

                if (event.getGuild().getRoles().get(i).getId().equals(guild.getDefRole())) {

                    try {

                        event.getGuild()
                                .addRoleToMember(event.getMember(), event.getGuild().getRoleById(guild.getDefRole()))
                                .queue();

                    } catch (HierarchyException e) {
                    } catch (ErrorResponseException e) {
                    }
                }
            }
        }
    }

    private void sendWelcomeMessage(GuildMemberJoinEvent event) {

        if (guild.getDefChan() != null) {

            Embed embed = new Embed();
            List<String> welcomeMessages = new ArrayList<>();
            setWelcomeMessagesList(welcomeMessages);

            embed.memberJoinInfo(event.getMember(), guild, permission);

            String randomWelcomeMessage = welcomeMessages.get(new Random().nextInt(welcomeMessages.size()))
                    .replace("[member]", event.getMember().getAsMention());
            try {

                event.getGuild().getTextChannelById(guild.getDefChan()).sendMessage(randomWelcomeMessage)
                        .queueAfter(300, TimeUnit.MILLISECONDS);

                event.getGuild().getTextChannelById(guild.getDefChan()).sendMessage(embed.getEmbed().build())
                        .queueAfter(500, TimeUnit.MILLISECONDS);

            } catch (InsufficientPermissionException e) {
            }
        }
    }

    private void setWelcomeMessagesList(List<String> welcomeMessages) {

        welcomeMessages.add("[member] has joined. Stay a while and listen !");
        welcomeMessages.add("[member] has spawned in the server !");
        welcomeMessages.add("[member] just landed !");
        welcomeMessages.add("[member] joined the party !");
        welcomeMessages.add("[member] came out of the sewers !");
        welcomeMessages.add("Look what the pipes washed in. [member]");
        welcomeMessages.add("The king [member] has come !");
    }

}