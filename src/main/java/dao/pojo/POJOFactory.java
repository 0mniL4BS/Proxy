package dao.pojo;

public class POJOFactory {

    private POJOFactory() {
    }

    public static GuildPOJO getGuild() {
        return new GuildPOJO();
    }

    public static MemberPOJO getMember() {
        return new MemberPOJO();
    }

    public static PermissionPOJO getPermission() {
        return new PermissionPOJO();
    }

}