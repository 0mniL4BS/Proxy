package commands.administrator;

import java.util.List;
import java.util.concurrent.TimeUnit;

import dao.DAO;
import dao.DAOFactory;
import dao.pojo.GuildPOJO;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class DefaultRole {

    private GuildMessageReceivedEvent event;
    private String[] args;

    public DefaultRole(GuildMessageReceivedEvent event, String[] args) {
        this.event = event;
        this.args = args;
    }

    public void defaultrole() {

        try {

            DAO<GuildPOJO> guildDao = DAOFactory.getGuildDAO();
            GuildPOJO guild = guildDao.find(event.getGuild().getId(), null);

            String roleId = args[1].substring(args[1].indexOf('&') + 1, args[1].indexOf('>'));

            if (isRoleList(event, roleId)) {

                if (roleId.equals(guild.getDefRole())) {

                    event.getChannel().sendTyping().queue();
                    event.getChannel()
                            .sendMessage(
                                    "Default role " + "<@&" + guild.getDefRole() + ">" + " has already been defined.")
                            .queueAfter(500, TimeUnit.MILLISECONDS);
                }

                else {

                    guild.setDefRole(roleId);
                    guildDao.update(guild);
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Default role is now " + "<@&" + guild.getDefRole() + ">.")
                            .queueAfter(500, TimeUnit.MILLISECONDS);
                }
            }

            else {

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Please mention a valid role.").queueAfter(500, TimeUnit.MILLISECONDS);
            }

        } catch (NullPointerException | StringIndexOutOfBoundsException e) {

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Please mention a valid role.").queueAfter(500, TimeUnit.MILLISECONDS);
        }

    }

    private boolean isRoleList(GuildMessageReceivedEvent event, String roleId) {

        List<Role> roles = event.getGuild().getRoles();

        for (int i = 0; i < roles.size(); i++) {

            if (roleId.equalsIgnoreCase(roles.get(i).getId())) {
                return true;
            }
        }
        return false;
    }

}