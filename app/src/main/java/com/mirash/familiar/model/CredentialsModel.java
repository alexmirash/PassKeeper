package com.mirash.familiar.model;

import static com.mirash.familiar.tool.Utils.isStringAlike;

import androidx.annotation.NonNull;

/**
 * @author Mirash
 */
public class CredentialsModel implements ICredentials {
    protected String title;
    protected String link;
    protected String login;
    protected String email;
    protected String phone;
    protected String password;
    protected String pin;
    protected String details;
    protected int position;

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
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getPin() {
        return pin;
    }

    @Override
    public String getDetails() {
        return details;
    }

    @Override
    public int getPosition() {
        return position;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @NonNull
    @Override
    public String toString() {
        return "[" + position + "]{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", pin='" + pin + '\'' +
                ", details='" + details + '\'' +
                '}';
    }

    @Override
    public boolean isAlike(String query) {
        return isStringAlike(title, query)
                || isStringAlike(link, query)
                || isStringAlike(login, query)
                || isStringAlike(email, query)
                || isStringAlike(phone, query);
    }
}
