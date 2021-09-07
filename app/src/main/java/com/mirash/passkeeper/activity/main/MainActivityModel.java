package com.mirash.passkeeper.activity.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mirash.passkeeper.db.Credentials;
import com.mirash.passkeeper.db.RepositoryProvider;
import com.mirash.passkeeper.model.CredentialsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

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

    public void handleOrderChanged(List<CredentialsItem> items) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            handleOrderChangedSync(items);
        });
    }

    public void handleOrderChangedSync(List<CredentialsItem> items) {
        List<Credentials> credentials = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            int id = items.get(i).getId();
            Credentials credById = RepositoryProvider.getCredentialsRepository().getCredentialsByIdSync(id);
            credById.setPosition(i);
            credentials.add(credById);
        }
        RepositoryProvider.getCredentialsRepository().updateAll(credentials);
    }
}
