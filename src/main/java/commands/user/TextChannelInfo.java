package commands.user;

import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import proxy.Embed;

public class TextChannelInfo {

    private GuildMessageReceivedEvent event;
    private TextChannel textChannel;

    public TextChannelInfo(GuildMessageReceivedEvent event, String[] args) {
        this.event = event;
        textChannel = event.getGuild()
                .getTextChannelById(args[1].substring(args[1].indexOf('#') + 1, args[1].indexOf('>')));
    }

    public void textChannelInfo() {

        Embed embed = new Embed();
        embed.channelInfo(textChannel);
        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessage(embed.getEmbed().build()).queueAfter(300, TimeUnit.MILLISECONDS);
    }

}