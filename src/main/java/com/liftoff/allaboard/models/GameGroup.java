package com.liftoff.allaboard.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GameGroup extends AbstractEntity{

    @ManyToMany(mappedBy = "gameGroup")
    private final List<User> user = new ArrayList<>();

    @NotBlank(message = "This field cannot be left blank!")
    @Size(max = 150)
    private String gameGroupName;

    @Size(max = 350)
    private String gameGroupDescription;

    @Size(max = 350)
    private String gameGroupRules;

    @Size(max = 150)
    private String gameGroupAddress;

    @NotNull(message = "This field cannot be left blank!")
    private int gameGroupZipCode;

    private int gameGroupMemberCount;

//    @AssertTrue(message = "You are the Admin of this group")
//    private boolean role;

    public GameGroup(String gameGroupName, String gameGroupDescription, String gameGroupRules, String gameGroupAddress,
                     int gameGroupZipCode, int gameGroupMemberCount) {
        this.gameGroupName = gameGroupName;
        this.gameGroupDescription = gameGroupDescription;
        this.gameGroupRules = gameGroupRules;
        this.gameGroupAddress = gameGroupAddress;
        this.gameGroupZipCode = gameGroupZipCode;
        this.gameGroupMemberCount = gameGroupMemberCount;
//        this.role = role;
    }

    public GameGroup(){
    }

    public String getGameGroupName() {
        return gameGroupName;
    }

    public void setGameGroupName(String gameGroupName) {
        this.gameGroupName = gameGroupName;
    }

    public String getGameGroupDescription() {
        return gameGroupDescription;
    }

    public void setGameGroupDescription(String gameGroupDescription) {
        this.gameGroupDescription = gameGroupDescription;
    }

    public String getGameGroupRules() {
        return gameGroupRules;
    }

    public void setGameGroupRules(String gameGroupRules) {
        this.gameGroupRules = gameGroupRules;
    }

    public String getGameGroupAddress() {
        return gameGroupAddress;
    }

    public int getGameGroupZipCode() {
        return gameGroupZipCode;
    }

    public void setGameGroupAddress(String gameGroupAddress) { this.gameGroupAddress = gameGroupAddress; }

    public void setGameGroupZipCode(int gameGroupZipCode) { this.gameGroupZipCode = gameGroupZipCode; }

    public int getGameGroupMemberCount() {
        return gameGroupMemberCount;
    }

    public void setGameGroupMemberCount(int gameGroupMemberCount) {
        this.gameGroupMemberCount = gameGroupMemberCount;
    }

    public List<User> getUser() {
        return user;
    }

    //    public boolean isRole() {
//        return role;
//    }
//
//    public void setRole(boolean role) {
//        this.role = role;
//    }
}
