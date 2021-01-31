package com.liftoff.allaboard.models;

import org.springframework.context.annotation.Role;

import java.util.Collection;
import java.util.Objects;
import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractEntity {
    public AbstractEntity(){}
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public AbstractEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

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


    public abstract String getRole();

    public abstract String getPassword();

    public abstract boolean isAccountNonExpired();

    public abstract String getUsername();


    public abstract boolean isAccountNonLocked();

    public abstract boolean isCredentialsNonExpired();

    public abstract boolean isEnabled();

//    public abstract boolean isMatchingPassword(String password);
}
