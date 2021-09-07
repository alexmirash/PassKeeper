package com.mirash.passkeeper.model;

import com.mirash.passkeeper.tool.listener.Filterable;

/**
 * @author Mirash
 */
public interface ICredentials extends Filterable {
    String getTitle();

    String getLink();

    String getLogin();

    String getPassword();

    String getPin();

    String getDetails();

    int getId();

    int getPosition();
}
