package com.mirash.familiar.model;

import com.mirash.familiar.tool.listener.Filterable;

/**
 * @author Mirash
 */
public interface ICredentials extends Filterable {
    String getTitle();

    String getLink();

    String getLogin();

    String getEmail();

    String getPhone();

    String getPassword();

    String getPin();

    String getDetails();

    int getId();

    int getPosition();
}
