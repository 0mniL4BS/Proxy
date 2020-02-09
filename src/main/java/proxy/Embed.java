package proxy;

import java.awt.Color;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import dao.pojo.GuildPOJO;
import dao.pojo.MemberPOJO;
import dao.pojo.PermissionPOJO;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Guild.Ban;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Embed {

    private EmbedBuilder embed;

    public void info(GuildMessageReceivedEvent event) {

        Constants.getPrefix(event);

        embed = new EmbedBuilder();

        embed.setColor(Color.YELLOW);

        embed.setTitle(":scroll: __Commands List__");

        embed.setDescription("The bot's prefix is __**" + Constants.prefix + "**__\n"
                + "_For more informations about a specific command, use_ __**" + Constants.prefix + Constants.CMD_HELP
                + " [command]**__");

        embed.addField(":closed_lock_with_key: **Administration**",
                "`" + Constants.CMD_PREFIX + "` " + "`" + Constants.CMD_PERM + "` " + "`" + Constants.CMD_DEFCHAN + "` "
                        + "`" + Constants.CMD_DEFROLE + "` " + "`" + Constants.CMD_SHIELD + "` " + "`"
                        + Constants.CMD_DISABLE + "`",
                false);

        embed.addField(":dagger: **Moderation**",
                "`" + Constants.CMD_CLEAN + "` " + "`" + Constants.CMD_SLOWMODE + "` " + "`" + Constants.CMD_KICK + "` "
                        + "`" + Constants.CMD_BAN + "` " + "`" + Constants.CMD_SOFTBAN + "` " + "`"
                        + Constants.CMD_UNBAN + "` " + "`" + Constants.CMD_PURGE + "` " + "`" + Constants.CMD_RESETCHAN
                        + "`",
                false);

        embed.addField(":tools: **Utility**",
                "`" + Constants.CMD_PING + "` " + "`" + Constants.CMD_UPTIME + "` " + "`" + Constants.CMD_GUILD_INFO
                        + "` " + "`" + Constants.CMD_MEMBER_INFO + "` " + "`" + Constants.CMD_TEXTCHAN_INFO + "`",
                false);

        embed.addField(":notepad_spiral: **Lists (bot)**", "`" + Constants.CMD_BANLIST + "` " + "`"
                + Constants.CMD_MODOLIST + "` " + "`" + Constants.CMD_ADMINLIST + "`", false);

        embed.addField(":grin: **Memes**", "`" + Constants.CMD_RICARDO + "` " + "`" + Constants.CMD_ISSOU + "`", false);
    }

    public void help(String title, String message) {

        embed = new EmbedBuilder();

        embed.setColor(Color.ORANGE);

        embed.setTitle(":gear: Command: " + title);

        embed.addField("", message, true);
    }

    public void ping(GuildMessageReceivedEvent event) {

        long ping = event.getJDA().getRestPing().complete();

        embed = new EmbedBuilder();

        embed.setColor(Color.BLUE);

        embed.setTitle(":ping_pong: Pong");

        embed.addField("", ":satellite: `" + ping + "`ms.", false);

        if (ping < 300) {

            embed.addField("", ":rocket: The bot isn't lagging.", false);
        }

        else {

            embed.addField("", ":snail: The bot is lagging.", false);
        }
    }

    public void uptime() {

        final long duration = ManagementFactory.getRuntimeMXBean().getUptime();

        final long years = duration / 31104000000L;

        final long months = duration / 2592000000L % 12;

        final long days = duration / 86400000L % 30;

        final long hours = duration / 3600000L % 24;

        final long minutes = duration / 60000L % 60;

        final long seconds = duration / 1000L % 60;

        String uptime = (years == 0 ? "" : "**" + years + "** Years, ")
                + (months == 0 ? "" : "**" + months + "** Months, ") + (days == 0 ? "" : "**" + days + "** Days, ")
                + (hours == 0 ? "" : "**" + hours + "** Hours, ")
                + (minutes == 0 ? "" : "**" + minutes + "** Minutes, ")
                + (seconds == 0 ? "" : "**" + seconds + "** Seconds, ");

        uptime = replaceLast(uptime, ", ", "");

        uptime = replaceLast(uptime, ",", " and");

        embed = new EmbedBuilder();

        embed.setTitle(":stopwatch: Uptime");

        embed.setColor(Color.BLUE);

        embed.addField("", uptime + ".", true);
    }

    public void serverInfo(Guild gld, GuildPOJO guild) {

        long members = gld.getMemberCache().applyStream(stream -> stream.map(Member::getUser).count());

        long bots = gld.getMemberCache()
                .applyStream(stream -> stream.map(Member::getUser).filter(user -> user.isBot()).count());

        long humans = gld.getMemberCache()
                .applyStream(stream -> stream.map(Member::getUser).filter(user -> !user.isBot()).count());

        long categories = gld.getCategories().size();

        long textChannels = gld.getTextChannels().size();

        long voiceChannels = gld.getVoiceChannels().size();

        long channels = categories + textChannels + voiceChannels;

        embed = new EmbedBuilder();

        embed.setColor(Color.GREEN);

        embed.setThumbnail(gld.getIconUrl());

        embed.setAuthor(gld.getName(), null, gld.getIconUrl());

        embed.addField("Server Creation", "**" + upperCaseFirstChar(gld.getTimeCreated().getDayOfWeek().toString())
                + " " + gld.getTimeCreated().format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)) + " at "
                + gld.getTimeCreated().format(DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH)) + "**", false);

        embed.addField("Guild Owner", gld.getOwner().getAsMention(), true);

        embed.addField("Owner ID", "`" + gld.getOwnerId() + "`", true);

        embed.addField("Guild ID", "`" + gld.getId() + "`", true);

        embed.addField("Members Count: __" + members + "__", ":small_orange_diamond: **__" + humans
                + "__ humans**\n:small_orange_diamond: **__" + bots + "__ Bots**", true);

        embed.addField("Channels Count: __" + channels + "__",
                ":small_orange_diamond: **__" + categories + "__ categories**\n:small_orange_diamond: **__"
                        + textChannels + "__ text channels**\n:small_orange_diamond: **__" + voiceChannels
                        + "__ voice channels**",
                true);

        embed.addField("Guild Region", "**" + upperCaseFirstChar(gld.getRegionRaw()) + "**", true);

        embed.addField("Roles Count", ":small_orange_diamond: **__" + gld.getRoles().size() + "__ roles**", true);

        embed.addField("Emotes Count", ":small_orange_diamond: **__" + gld.getEmotes().size() + "__ emotes**", true);

        embed.addField("Boosts Count", ":small_orange_diamond: **__" + gld.getBoostCount() + "__ boosts**", true);

        embed.addField("Default Notification Level",
                "**" + upperCaseFirstChar(gld.getDefaultNotificationLevel().toString()) + "**", false);

        if (gld.getExplicitContentLevel().getKey() == 0) {

            embed.addField("Explicit Content Level", "**" + gld.getExplicitContentLevel().getDescription()
                    + "**\n*Ain't no party like my grandma's tea party.*", false);
        }

        else if (gld.getExplicitContentLevel().getKey() == 1) {

            embed.addField("Explicit Content Level", "**" + gld.getExplicitContentLevel().getDescription()
                    + "**\n*Recommended option for servers that use roles trusted membership.*", false);
        }

        else if (gld.getExplicitContentLevel().getKey() == 2) {

            embed.addField("Explicit Content Level", "**" + gld.getExplicitContentLevel().getDescription()
                    + "**\n*Recommended option for when you want that squeaky clean shine.*", false);
        }

        embed.addField("Verification Level", "**" + upperCaseFirstChar(gld.getVerificationLevel().name()) + " (Level "
                + gld.getVerificationLevel().getKey() + ")**", true);

        embed.addField("MFA Level", "**" + upperCaseFirstChar(gld.getRequiredMFALevel().name()) + " (Level "
                + gld.getRequiredMFALevel().getKey() + ")**", true);

        embed.addField("Shield",
                "**" + String.valueOf(guild.getShield()).replace("true", "Active").replace("false", "Inactive") + "**",
                true);

        if (gld.getAfkChannel() != null) {

            embed.addField("AFK Channel", "```ini\n[Name]:      " + gld.getAfkChannel().getName() + "\n[ID]:        "
                    + gld.getAfkChannel().getId() + "\n[Timeout]:   " + gld.getAfkTimeout().getSeconds() + "s```",
                    false);
        }

        if (gld.getSystemChannel() != null) {

            embed.addField("System Channel", "```ini\n[Name]: " + gld.getSystemChannel().getName() + "\n[ID]: "
                    + gld.getSystemChannel().getId() + "```", false);
        }

        if (guild.getDefChan() == null) {

            embed.addField("Default Channel", "**No Default Channel**", false);
        }

        else {

            embed.addField("Default Channel", "<#" + guild.getDefChan() + ">", false);
        }

        if (guild.getDefRole() == null) {

            embed.addField("Default Role", "**No Default Role**", false);
        }

        else {

            embed.addField("Default Role", "<@&" + guild.getDefRole() + ">", false);
        }

        embed.setFooter(
                upperCaseFirstChar(
                        DateTimeFormatter.ofPattern("EEE, dd/MM/yyyy", Locale.ENGLISH).format(LocalDateTime.now()))
                        + " at " + DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH).format(LocalDateTime.now()),
                gld.getMemberById(Constants.BOT_ID).getUser().getAvatarUrl());
    }

    public void memberInfo(Event event, Member mbr, PermissionPOJO perm) {

        embed = new EmbedBuilder();

        embed.setColor(Color.GREEN);

        embed.setThumbnail(mbr.getUser().getEffectiveAvatarUrl());

        embed.setAuthor(mbr.getUser().getName(), null, mbr.getUser().getAvatarUrl());

        embed.addField("Nickname", "**" + nickname(mbr) + "**", true);

        embed.addField("Discriminator", "**" + mbr.getUser().getDiscriminator() + "**", true);

        embed.addField("Mention", mbr.getAsMention(), true);

        embed.addField("Status", "**" + upperCaseFirstChar(mbr.getOnlineStatus().name()) + "**", true);

        embed.addField("Activity", "**" + activities(mbr) + "**", true);

        if (!mbr.getUser().isBot()) {

            embed.addField("Permission (On Bot)", "**" + perm.getName() + "**", true);
        }

        else {

            if (mbr.hasPermission(Permission.ADMINISTRATOR)) {

                embed.addField("Administrator", "**Yes**", true);
            }

            else {

                embed.addField("Administrator", "**No**", true);
            }
        }

        embed.addField("Server Join",
                "**" + mbr.getTimeJoined().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "**", true);

        embed.addField("Discord Join",
                "**" + mbr.getUser().getTimeCreated().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "**", true);

        embed.addField("ID", "**" + mbr.getId() + "**", true);

        if (mbr.getUser().isBot()) {

            embed.addField("Permissions", "```ini\n[Manage Server]:       "
                    + returnYesOrNo(mbr.hasPermission(Permission.MANAGE_SERVER)) + "\n[Manage Roles]:        "
                    + returnYesOrNo(mbr.hasPermission(Permission.MANAGE_ROLES)) + "\n[Manage Channels]:     "
                    + returnYesOrNo(mbr.hasPermission(Permission.MANAGE_CHANNEL)) + "\n[Kick Members]:        "
                    + returnYesOrNo(mbr.hasPermission(Permission.KICK_MEMBERS)) + "\n[Ban Members]:         "
                    + returnYesOrNo(mbr.hasPermission(Permission.BAN_MEMBERS)) + "\n[Mute Members]:        "
                    + returnYesOrNo(mbr.hasPermission(Permission.VOICE_MUTE_OTHERS)) + "\n[Deafen Members]:      "
                    + returnYesOrNo(mbr.hasPermission(Permission.VOICE_DEAF_OTHERS)) + "```", false);

            if (mbr.getId().equals(Constants.BOT_ID)) {

                User creator = event.getJDA().getUserById(Constants.CREATOR_ID);

                embed.addField("Guild Count", "**" + event.getJDA().getGuilds().size() + "**", true);

                embed.addField("Prefix", "**" + Constants.prefix + "**", true);

                embed.addField("Get Started", "**" + Constants.prefix + "info**", true);

                embed.setFooter("Creator: " + creator.getName() + "#" + creator.getDiscriminator(),
                        creator.getEffectiveAvatarUrl());
            }
        }

        if (!mbr.getRoles().isEmpty()) {

            embed.addField("Role(s)", memberRoles(mbr), false);
        }

        if (mbr.getId().equals(Constants.BOT_ID)) {

            embed.addField("Links", "**[Invite](" + Constants.INVITE + ")** | **[GitHub](" + Constants.GITLAB
                    + ")** | **[Support Server](" + Constants.SUPPORT_SERVER + ")**", false);
        }
    }

    public void memberJoinInfo(Member mbr, GuildPOJO guild, PermissionPOJO perm) {

        embed = new EmbedBuilder();

        embed.setColor(Color.GREEN);

        embed.setThumbnail(mbr.getUser().getEffectiveAvatarUrl());

        embed.setAuthor(mbr.getUser().getName(), null, mbr.getUser().getAvatarUrl());

        embed.addField("Nickname", "**" + nickname(mbr) + "**", true);

        embed.addField("Discriminator", "**" + mbr.getUser().getDiscriminator() + "**", true);

        embed.addField("Mention", mbr.getAsMention(), true);

        embed.addField("Status", "**" + upperCaseFirstChar(mbr.getOnlineStatus().name()) + "**", true);

        embed.addField("Activity", "**" + activities(mbr) + "**", true);

        embed.addField("Permission (On Bot)", "**" + perm.getName() + "**", true);

        embed.addField("Server Join Date",
                "**" + mbr.getTimeJoined().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "**", true);

        embed.addField("Discord Join Date",
                "**" + mbr.getUser().getTimeCreated().format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH))
                        + "**",
                true);

        embed.addField("ID", "**" + mbr.getId() + "**", true);

        if (guild.getDefRole() != null) {

            embed.addField("Role(s)", "<@&" + guild.getDefRole() + ">", false);
        }

    }

    public void channelInfo(TextChannel textChannel) {

        embed = new EmbedBuilder();

        embed.setColor(Color.GREEN);

        embed.setThumbnail(textChannel.getGuild().getIconUrl());

        embed.addField("Name", "**" + textChannel.getName() + "**", true);

        embed.addField("Position", "**" + (textChannel.getPosition() + 1) + "**", true);

        embed.addField("Mention", textChannel.getAsMention(), true);

        embed.addField("Creation",
                "**" + textChannel.getTimeCreated().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "**", true);

        embed.addField("Slowmode", "**" + returnActiveOrInactive(textChannel.getSlowmode()) + "**", true);

        embed.addField("ID", "**" + textChannel.getId() + "**", true);

        embed.addField("Category", "```ini\n[Name]:               " + textChannel.getParent().getName()
                + "\n[ID]:                 " + textChannel.getParent().getId() + "\n[Position]:           "
                + (textChannel.getParent().getPosition() + 1) + "\n[Creation]:           " + textChannel.getParent()
                        .getTimeCreated().format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH))
                + "```", true);
    }

    public void modoListEmbed(Set<MemberPOJO> moderatorList) {

        Object[] moderators = moderatorList.toArray();

        embed = new EmbedBuilder();

        embed.setColor(Color.RED);

        embed.setTitle(":dagger: __Moderator(s)__");

        for (int i = 0; i < moderators.length; i++) {

            embed.addField("", "<@" + ((MemberPOJO) moderators[i]).getId() + ">", false);
        }
    }

    public void adminList(Set<MemberPOJO> adminList) {

        Object[] administrators = adminList.toArray();

        embed = new EmbedBuilder();

        embed.setColor(Color.RED);

        embed.setTitle(":closed_lock_with_key: __Administrator(s)__");

        for (int i = 0; i < adminList.size(); i++) {

            embed.addField("", "<@" + ((MemberPOJO) administrators[i]).getId() + ">", false);
        }
    }

    public void banList(List<Ban> memberList) {

        embed = new EmbedBuilder();

        embed.setColor(Color.RED);

        embed.setTitle(":skull_crossbones: __Ban List__");

        for (int i = 0; i < memberList.size(); i++) {

            embed.addField("", "**" + String.valueOf(i + 1) + ". " + memberList.get(i).getUser().getName() + "**",
                    false);
        }
        embed.addField("", "**" + Constants.prefix + "banlist [a number]**", true);
    }

    public void bannedMemberInfo(List<Ban> memberList, int arg) {

        embed = new EmbedBuilder();

        embed.setColor(Color.RED);

        embed.setThumbnail(memberList.get(arg).getUser().getEffectiveAvatarUrl());

        embed.addField("Name", "**" + memberList.get(arg).getUser().getName() + "**", true);

        embed.addField("Discriminator", "**" + memberList.get(arg).getUser().getDiscriminator() + "**", true);

        embed.addField("Discord Join Date", "**" + memberList.get(arg).getUser().getTimeCreated()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)) + "**", true);

        embed.addField("Bot Account", "**"
                + String.valueOf(memberList.get(arg).getUser().isBot()).replace("true", "Yes").replace("false", "No")
                + "**", true);

        embed.addField("Fake Account", "**"
                + String.valueOf(memberList.get(arg).getUser().isFake()).replace("true", "Yes").replace("false", "No")
                + "**", true);

        embed.addField("ID", "**" + memberList.get(arg).getUser().getId() + "**", true);

        embed.addField("Reason",
                "_" + String.valueOf(memberList.get(arg).getReason()).replace("null", "No reason provided.") + "_",
                false);
    }

    private String nickname(Member member) {

        if (member.getNickname() == null) {

            return member.getUser().getName();
        }

        else {

            return member.getNickname();
        }
    }

    private String activities(Member member) {

        String activities = "";

        for (int i = 0; i < member.getActivities().size(); i++) {

            activities = activities + member.getActivities().get(i).getName() + " ";
        }

        if (member.getActivities().isEmpty()) {

            activities = "No Activity";
        }

        return activities;
    }

    private String memberRoles(Member member) {

        String roles = "";

        for (int i = 0; i < member.getRoles().size(); i++) {

            roles = roles + "<@&" + member.getRoles().get(i).getId() + "> ";
        }
        return roles;
    }

    public String returnActiveOrInactive(int number) {

        String value = "";

        if (number == 0) {

            value = "Inactive";
        }

        else {

            value = "Active (" + String.valueOf(number) + "s)";
        }
        return value;
    }

    public String returnYesOrNo(boolean permission) {

        if (permission) {

            return "Yes";
        }

        else {

            return "No";
        }
    }

    private String upperCaseFirstChar(String message) {

        return message.substring(0, 1).toUpperCase() + message.substring(1).toLowerCase().replaceAll("_", " ");
    }

    private String replaceLast(final String text, final String regex, final String replacement) {

        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

    public EmbedBuilder getEmbed() {

        return embed;
    }

}