package com.mirash.passkeeper.activity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mirash.passkeeper.db.Credentials;
import com.mirash.passkeeper.db.repository.RepositoryProvider;

import java.util.List;

/**
 * @author Mirash
 */
public class MainActivityModel extends AndroidViewModel {
    private final LiveData<List<Credentials>> credentialsModelLiveData;

    public MainActivityModel(@NonNull Application application) {
        super(application);
        credentialsModelLiveData = RepositoryProvider.getCredentialsRepository().getAllCredentialsLiveData();
    }

    public LiveData<List<Credentials>> getCredentialsModelLiveData() {
        return credentialsModelLiveData;
    }
}
