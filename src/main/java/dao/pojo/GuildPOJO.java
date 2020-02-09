package dao.pojo;

import java.util.Set;

public class GuildPOJO {

    private String id;
    private String name;
    private String defChan;
    private String defRole;
    private String prefix;
    private boolean shield;
    private Set<MemberPOJO> members;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefChan() {
        return defChan;
    }

    public void setDefChan(String defChan) {
        this.defChan = defChan;
    }

    public String getDefRole() {
        return defRole;
    }

    public void setDefRole(String defRole) {
        this.defRole = defRole;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean getShield() {
        return shield;
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }

    public Set<MemberPOJO> getMembers() {
        return members;
    }

    public void setMembers(Set<MemberPOJO> members) {
        this.members = members;
    }

    public void addMember(MemberPOJO member) {
        if (!this.members.contains(member)) {
            this.members.add(member);
        }
    }

    public void removeMember(MemberPOJO member) {
        this.members.remove(member);
    }

}