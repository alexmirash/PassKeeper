package com.mirash.passkeeper.model;

/**
 * @author Mirash
 */
public class CredentialsModel implements ICredentials {
    protected String title;
    protected String link;
    protected String login;
    protected String password;
    protected String details;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getLink() {
        return link;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getDetails() {
        return details;
    }

    @Override
    public int getId() {
        return 0;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "CredentialsItem{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
