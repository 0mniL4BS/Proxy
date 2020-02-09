package proxy;

import listeners.onconnection.ConnectionDBL;
import listeners.onconnection.ConnectionDiscord;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Startup {

    public static void main(String[] args) {
        launchBot();
    }

    private static void launchBot() {
        ConnectionDiscord.getInstance().setStatus(OnlineStatus.ONLINE);
        ConnectionDiscord.getInstance().setActivity(Activity.playing("@" + Constants.BOT_NAME));
        ConnectionDiscord.addEventsListeners();
        ConnectionDBL.getInstance().setStats(ConnectionDiscord.getInstance().getGuilds().size());
    }

}