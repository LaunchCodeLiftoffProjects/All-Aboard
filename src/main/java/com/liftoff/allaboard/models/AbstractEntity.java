package com.liftoff.allaboard.models;

import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue
    private int id;

//    @Id
//    @GeneratedValue
//    private int groupId;

    public AbstractEntity() {
    }

    public int getId() {
        return this.id;
    }

//    public int getGroupId() { return groupId; }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            AbstractEntity that = (AbstractEntity)o;
            return this.id == that.id;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id});
    }
}
