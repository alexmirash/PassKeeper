package com.mirash.familiar.model;

import androidx.annotation.NonNull;

import com.mirash.familiar.db.Credentials;

/**
 * @author Mirash
 */
public class CredentialsItem implements ICredentials {
    private final Credentials credentials;
    private boolean isPasswordVisible;

    public CredentialsItem(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public String getTitle() {
        return credentials.getTitle();
    }

    @Override
    public String getLink() {
        return credentials.getLink();
    }

    @Override
    public String getLogin() {
        return credentials.getLogin();
    }

    @Override
    public String getPassword() {
        return credentials.getPassword();
    }

    @Override
    public String getPin() {
        return credentials.getPin();
    }

    @Override
    public String getDetails() {
        return credentials.getDetails();
    }

    @Override
    public int getId() {
        return credentials.getId();
    }

    @Override
    public int getPosition() {
        return credentials.getPosition();
    }

    public void setPasswordVisible(boolean passwordVisible) {
        isPasswordVisible = passwordVisible;
    }

    public boolean isPasswordVisible() {
        return isPasswordVisible;
    }

    @NonNull
    @Override
    public String toString() {
        return credentials.toString();
    }

    @Override
    public boolean isAlike(String query) {
        return credentials.isAlike(query);
    }
}
