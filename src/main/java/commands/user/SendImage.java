package commands.user;

import java.io.File;
import java.util.Random;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class SendImage {

    private GuildMessageReceivedEvent event;
    private String path;

    public SendImage(GuildMessageReceivedEvent event, String path) {
        this.event = event;
        this.path = path;
    }

    public void sendImage() {

        File directory = new File(path);
        File[] paths = directory.listFiles();
        event.getChannel().sendFile(paths[new Random().nextInt((paths.length))]).queue();
    }

}