package com.android.nunes.sophiamobile.model;

/**
 * Created by Letícia on 28/06/2015.
 */
public class User {

    private String name;
    private String password;
    private String id;

    public User(String name) {
        this.name = name;
    }

    public User(String name, String password, String id) {
        this.name = name;
        this.password = password;
        this.id = id;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public String getId() { return id;   }

    public void setPassword(String password) {
        this.password = password;
    }
}
