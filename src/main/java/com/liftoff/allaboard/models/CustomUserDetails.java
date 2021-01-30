package com.liftoff.allaboard.models;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public abstract class CustomUserDetails implements UserDetails {
    private  User user;

    public CustomUserDetails(final User _user) {
        this.user = _user;
    }

    public CustomUserDetails() {
    }

    public Collection<GrantedAuthority> getAuthorities(Integer access) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);

        if (access.compareTo(1) == 0) {
            authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        else{
            authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authList;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @Override
    public String getUsername() {
        if (this.user == null) {
            return null;
        }
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "CustomUserDetails [user=" + user + "]";
    }
}
