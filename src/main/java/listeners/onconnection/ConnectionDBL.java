package listeners.onconnection;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.discordbots.api.client.DiscordBotListAPI;

import proxy.Constants;

public class ConnectionDBL {

    private static volatile ConnectionDBL instance;
    private static Properties props;
    private static DiscordBotListAPI dbl;

    private ConnectionDBL() {
        try {
            props = new Properties();
            props.load(new FileReader(Constants.PATH_DBL_PROPERTIES));
            dbl = new DiscordBotListAPI.Builder().token(props.getProperty("tokenDBL")).botId(Constants.BOT_ID).build();
        } catch (IOException e) {
        }
    }

    public static DiscordBotListAPI getInstance() {
        if (instance == null) {
            synchronized (ConnectionDBL.class) {
                if (instance == null) {
                    instance = new ConnectionDBL();
                }
            }
        }
        return dbl;
    }

}