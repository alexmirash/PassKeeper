package com.mirash.passkeeper.db.repository;

import androidx.lifecycle.LiveData;

import com.mirash.passkeeper.db.Credentials;
import com.mirash.passkeeper.db.CredentialsDao;

import java.util.List;

/**
 * @author Mirash
 */
public class CredentialsRepository {
    private final CredentialsDao dao;

    public CredentialsRepository(CredentialsDao dao) {
        this.dao = dao;
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

    public void delete(Credentials Credentials) {
        dao.delete(Credentials);
    }

    public void delete(List<Credentials> credentials) {
        dao.delete(credentials);
    }

    public LiveData<List<Credentials>> getAllCredentialsLiveData() {
        return dao.getAll();
    }

    public List<Credentials> getAllEntryDataSync() {
        return dao.getAllSync();
    }

    public LiveData<Credentials> getEntryById(int id) {
        return dao.getById(id);
    }

    public LiveData<Credentials> getEntryByName(String name) {
        return dao.getByTitle(name);
    }

    public Credentials getEntryByNameSync(String name) {
        return dao.getByTitleSync(name);
    }
}
