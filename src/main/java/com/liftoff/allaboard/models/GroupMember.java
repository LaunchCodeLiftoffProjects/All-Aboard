package com.liftoff.allaboard.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GroupMember extends AbstractEntity{

//    @Size(min = 1, max = 25)
//    private int groupSize;
//
//    @ManyToMany(mappedBy = "groupmember")
//    private final List<GameGroup> gameGroups = new ArrayList<>();
//
//    public GroupMember(int groupSize) { this.groupSize = groupSize; }
//
//    public GroupMember() {}
//
//    public int getGroupSize() {
//        return groupSize;
//    }
//
//    public void setGroupSize(int groupSize) {
//        this.groupSize = groupSize;
//    }
//
//    public List<GameGroup> getGameGroups() {
//        return gameGroups;
//    }
//

}
