package com.mirash.passkeeper.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author Mirash
 */
@Entity
public class Credentials {
    @PrimaryKey(autoGenerate = true) private int id;
    private String title;
    private String link;
    private String login;
    private String password;
    private String details;

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}