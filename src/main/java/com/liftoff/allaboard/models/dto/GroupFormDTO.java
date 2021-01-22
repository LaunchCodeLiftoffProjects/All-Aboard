package com.liftoff.allaboard.models.dto;

import com.liftoff.allaboard.models.Group;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GroupFormDTO {

    @NotBlank
    private Group groupName;

    private Group groupDescription;

    private Group groupRules;

    private Group groupLocation;

    public Group getGroupName() {
        return groupName;
    }

    public void setGroupName(Group groupName) {
        this.groupName = groupName;
    }

    public Group getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(Group groupDescription) {
        this.groupDescription = groupDescription;
    }

    public Group getGroupRules() {
        return groupRules;
    }

    public void setGroupRules(Group groupRules) {
        this.groupRules = groupRules;
    }

    public Group getGroupLocation() {
        return groupLocation;
    }

    public void setGroupLocation(Group groupLocation) {
        this.groupLocation = groupLocation;
    }
}
