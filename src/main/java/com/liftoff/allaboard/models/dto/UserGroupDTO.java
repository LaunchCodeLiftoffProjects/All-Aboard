package com.liftoff.allaboard.models.dto;

import com.liftoff.allaboard.models.GameGroup;
import com.liftoff.allaboard.models.User;
import javax.validation.constraints.NotNull;

public class UserGroupDTO {

    @NotNull
    private GameGroup gameGroup;

    @NotNull
    private User user;

    public UserGroupDTO() {    }

    public GameGroup getGameGroup() {
        return gameGroup;
    }

    public void setGameGroup(GameGroup gameGroup) {
        this.gameGroup = gameGroup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
