package com.mirash.passkeeper.db;

import android.app.Application;

import com.mirash.passkeeper.PassKeeperApp;

/**
 * @author Mirash
 */
public class RepositoryProvider {
    private static final RepositoryProvider instance = new RepositoryProvider();
    private CredentialsRepository credentialsRepository;

    public static RepositoryProvider getInstance() {
        return instance;
    }

    public CredentialsRepository getCredentialsRepository(Application app) {
        if (credentialsRepository == null) {
            credentialsRepository = new CredentialsRepository(CredentialsDatabase.getDatabase(app).getCredentialsDao());
        }
        return credentialsRepository;
    }

    public static CredentialsRepository getCredentialsRepository() {
        return instance.getCredentialsRepository(PassKeeperApp.getInstance());
    }
}
