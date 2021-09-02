package com.mirash.passkeeper.model;

/**
 * @author Mirash
 */
public class CredentialsModel {
    protected String title;
    protected String link;
    protected String login;
    protected String password;
    protected String details;

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
        return "CredentialsModel{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
