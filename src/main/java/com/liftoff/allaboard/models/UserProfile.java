package com.liftoff.allaboard.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserProfile<Groups> {

    @ManyToMany
    private List<Groups> groups = new ArrayList<>();








}

