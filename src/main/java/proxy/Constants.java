package proxy;

import dao.DAO;
import dao.DAOFactory;
import dao.pojo.GuildPOJO;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Constants {

    public final static String INVITE = "https://discordapp.com/oauth2/authorize?client_id=592680274962415635&permissions=8&scope=bot";
    public final static String GITLAB = "https://github.com/Unknown-Ph4ntom/Proxy";
    public final static String SUPPORT_SERVER = "https://discord.gg/3XkknNE";

    public final static String PATH_RESOURCES_FOLDER = "src/main/dev/resources";
    public final static String PATH_DATASOURCE_PROPERTIES = PATH_RESOURCES_FOLDER + "/files/datasource.properties";
    public final static String PATH_DISCORD_PROPERTIES = PATH_RESOURCES_FOLDER + "/files/discord.properties";
    public final static String PATH_DBL_PROPERTIES = PATH_RESOURCES_FOLDER + "/files/dbl.properties";
    public final static String PATH_IMG_RICARDO = PATH_RESOURCES_FOLDER + "/img/ricardomilos/";
    public final static String PATH_IMG_ISSOU = PATH_RESOURCES_FOLDER + "/img/issou/";

    public final static String BOT_NAME = "Proxy";
    public final static String BOT_ID = "592680274962415635";
    public final static String CREATOR_ID = "500688503617749023";
    public final static String DISCORDBOTLIST_ID = "264445053596991498";

    public static String prefix = "";
    public final static String CMD_INFO = "info";
    public final static String CMD_HELP = "help";
    public final static String CMD_PREFIX = "prefix";
    public final static String CMD_PERM = "setperm";
    public final static String CMD_DEFCHAN = "defchan";
    public final static String CMD_DEFROLE = "defrole";
    public final static String CMD_DISABLE = "disable";
    public final static String CMD_SLOWMODE = "slowmode";
    public final static String CMD_KICK = "kick";
    public final static String CMD_BAN = "ban";
    public final static String CMD_SOFTBAN = "softban";
    public final static String CMD_UNBAN = "unban";
    public final static String CMD_PURGE = "purge";
    public final static String CMD_PING = "ping";
    public final static String CMD_UPTIME = "uptime";
    public final static String CMD_CLEAN = "clean";
    public final static String CMD_RESETCHAN = "resetchan";
    public final static String CMD_SHIELD = "shield";
    public final static String CMD_GUILD_INFO = "server";
    public final static String CMD_MEMBER_INFO = "member";
    public final static String CMD_TEXTCHAN_INFO = "textchan";
    public final static String CMD_BANLIST = "banlist";
    public final static String CMD_MODOLIST = "modolist";
    public final static String CMD_ADMINLIST = "adminlist";
    public final static String CMD_RICARDO = "ricardo";
    public final static String CMD_ISSOU = "issou";

    public final static int USER_PERM = 1;
    public final static int MODERATOR_PERM = 2;
    public final static int ADMINISTRATOR_PERM = 3;

    public static void getPrefix(GuildMessageReceivedEvent event) {
        DAO<GuildPOJO> guildDao = DAOFactory.getGuildDAO();
        GuildPOJO guild = guildDao.find(event.getGuild().getId(), null);
        prefix = guild.getPrefix();
    }

}