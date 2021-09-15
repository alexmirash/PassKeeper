package com.mirash.passkeeper.db;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * @author Mirash
 */
public class CredentialsRepository {
    private final CredentialsDao dao;
    private final CredentialsDatabase database;

    public CredentialsRepository(CredentialsDatabase database) {
        this.database = database;
        this.dao = database.getCredentialsDao();
    }

    public void insertAll(List<Credentials> credentialsList) {
        dao.insertAll(credentialsList);
    }

    public void updateAll(List<Credentials> credentialsList) {
        dao.updateAll(credentialsList);
    }

    public void insert(Credentials Credentials) {
        dao.insert(Credentials);
    }

    public void update(Credentials Credentials) {
        dao.update(Credentials);
    }

    public LiveData<List<Credentials>> getAllCredentialsLiveData() {
        return dao.getAll();
    }

    public LiveData<Credentials> getCredentialsById(int id) {
        return dao.getById(id);
    }

    public Credentials getCredentialsByIdSync(int id) {
        return dao.getByIdSync(id);
    }

    public List<Credentials> getCredentialsUnderPositionSync(int position) {
        return dao.getUnderPositionSync(position);
    }

    public void deleteCredentialsById(int id) {
        dao.deleteById(id);
    }

    public void nuke() {
        new Thread(database::clearAllTables).start();
    }
}
