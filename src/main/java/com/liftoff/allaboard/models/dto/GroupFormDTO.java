package com.liftoff.allaboard.models.dto;

import javax.validation.constraints.NotBlank;

public class GroupFormDTO {

    @NotBlank
    private String gameGroupName;

    private String gameGroupDescription;

    private String gameGroupRules;

    private String gameGroupLocation;

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

    public String getGameGroupLocation() {
        return gameGroupLocation;
    }

    public void setGameGroupLocation(String gameGroupLocation) {
        this.gameGroupLocation = gameGroupLocation;
    }
}
