package com.liftoff.allaboard.models;

public class Game {


    public int id;
    public String name;

    public Game(int id, String name) {

        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
