package com.mirash.passkeeper.db;

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

    public List<Credentials> getAllCredentialsSync() {
        return dao.getAllSync();
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

    public LiveData<Credentials> getEntryByName(String name) {
        return dao.getByTitle(name);
    }

    public Credentials getEntryByNameSync(String name) {
        return dao.getByTitleSync(name);
    }
}