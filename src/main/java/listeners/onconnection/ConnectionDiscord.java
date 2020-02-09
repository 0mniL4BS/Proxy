package listeners.onconnection;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.security.auth.login.LoginException;

import listeners.commands.Administrator;
import listeners.commands.Help;
import listeners.commands.Moderator;
import listeners.commands.User;
import listeners.guild.GuildDefChan;
import listeners.guild.GuildDefRole;
import listeners.guild.GuildJoin;
import listeners.guild.GuildLeave;
import listeners.guild.GuildName;
import listeners.guild.GuildOwner;
import listeners.member.MemberJoin;
import listeners.member.MemberLeave;
import listeners.member.MemberName;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import proxy.Constants;

public class ConnectionDiscord {

    private static volatile ConnectionDiscord instance;
    private static Properties props;
    private static ShardManager shard;

    private ConnectionDiscord() {
        try {
            props = new Properties();
            props.load(new FileReader(Constants.PATH_DISCORD_PROPERTIES));
            shard = new DefaultShardManagerBuilder(props.getProperty("tokenDiscord")).setAutoReconnect(true)
                    .setChunkingFilter(ChunkingFilter.exclude(Long.parseLong(Constants.DISCORDBOTLIST_ID))).build();
        } catch (LoginException e) {
        } catch (IllegalArgumentException e) {
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public static ShardManager getInstance() {
        if (instance == null) {
            synchronized (ConnectionDiscord.class) {
                if (instance == null) {
                    instance = new ConnectionDiscord();
                }
            }
        }
        return shard;
    }

    public static void addEventsListeners() {
        shard.addEventListener(new ConnectionBot());
        shard.addEventListener(new GuildJoin());
        shard.addEventListener(new GuildLeave());
        shard.addEventListener(new MemberJoin());
        shard.addEventListener(new MemberLeave());
        shard.addEventListener(new MemberName());
        shard.addEventListener(new GuildDefChan());
        shard.addEventListener(new GuildDefRole());
        shard.addEventListener(new GuildName());
        shard.addEventListener(new GuildOwner());
        shard.addEventListener(new Administrator());
        shard.addEventListener(new Moderator());
        shard.addEventListener(new User());
        shard.addEventListener(new Help());
    }

}