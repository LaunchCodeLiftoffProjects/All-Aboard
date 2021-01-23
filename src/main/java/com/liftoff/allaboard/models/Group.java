package com.liftoff.allaboard.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Group extends AbstractEntity{

    @NotBlank(message = "This field cannot be left blank!")
    @Size(max = 150)
    private String groupName;

//    @Id
//    @GeneratedValue
//    private int groupId;

    @Size(max = 350)
    private String groupDescription;

    @Size(max = 350)
    private String groupRules;

    @Size(max = 150)
    private String groupLocation;

    private int groupMemberCount;

    @AssertTrue(message = "You are the Admin of this group")
    private boolean role;

    public Group(@NotBlank(message = "This field cannot be left blank!") @Size(max = 150) String groupName,
                 @Size(max = 350) String groupDescription, @Size(max = 350) String groupRules,
                 @Size(max = 150) String groupLocation, int groupMemberCount, @AssertTrue(message = "You are the Admin of this group") boolean role) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.groupRules = groupRules;
        this.groupLocation = groupLocation;
        this.groupMemberCount = groupMemberCount;
        this.role = role;
    }

    public Group(){
    }

    public String getGroupName() { return groupName; }

    public void setGroupName(String groupName) { this.groupName = groupName; }

//    public int getGroupId() {
//        return groupId;
//    }
//
//    public void setGroupId(int groupId) {
//        this.groupId = groupId;
//    }

    public String getGroupDescription() { return groupDescription; }

    public void setGroupDescription(String groupDescription) { this.groupDescription = groupDescription; }

    public String getGroupRules() { return groupRules; }

    public void setGroupRules(String groupRules) { this.groupRules = groupRules; }

    public String getGroupLocation() { return groupLocation; }

    public void setGroupLocation(String groupLocation) { this.groupLocation = groupLocation; }

    public int getGroupMemberCount() { return groupMemberCount; }

    public boolean isRole() { return role; }

    public void setRole(boolean role) { this.role = role; }
}
