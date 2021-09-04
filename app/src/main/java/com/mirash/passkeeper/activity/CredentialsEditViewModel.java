package com.mirash.passkeeper.activity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.mirash.passkeeper.db.Credentials;
import com.mirash.passkeeper.db.repository.RepositoryProvider;
import com.mirash.passkeeper.model.CredentialsModel;
import com.mirash.passkeeper.model.ICredentials;

import java.util.concurrent.Executors;

/**
 * @author Mirash
 */
public class CredentialsEditViewModel extends AndroidViewModel {
    public static final int INDEX_TITLE = 0;
    public static final int INDEX_LINK = 1;
    public static final int INDEX_LOGIN = 2;
    public static final int INDEX_PASSWORD = 3;
    public static final int INDEX_PIN = 4;
    private LiveData<Credentials> credentialsLiveData;
    private Integer credentialsId;
    private final boolean[] fillStates = new boolean[]{false, false, false, false, false};
    private final MutableLiveData<Boolean> saveButtonEnableStateLiveData;

    public CredentialsEditViewModel(@NonNull Application application) {
        super(application);
        saveButtonEnableStateLiveData = new MutableLiveData<>(false);
    }

    public void setCredentialsId(int id) {
        credentialsId = id;
        credentialsLiveData = RepositoryProvider.getCredentialsRepository().getCredentialsById(credentialsId);
    }

    @Nullable
    public Integer getCredentialsId() {
        return credentialsId;
    }

    public LiveData<Credentials> getCredentialsLiveData() {
        return credentialsLiveData;
    }

    public MutableLiveData<Boolean> getSaveButtonEnableStateLiveData() {
        return saveButtonEnableStateLiveData;
    }

    public void removeCredentialsObserver(@NonNull Observer<Credentials> observer) {
        if (credentialsLiveData != null) credentialsLiveData.removeObserver(observer);
    }

    public void saveCredentials(ICredentials credentials) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> insertCredentialsSync(credentials));
    }

    public void insertCredentialsSync(ICredentials data) {
        if (credentialsLiveData != null) {
            Credentials credentials = credentialsLiveData.getValue();
            if (credentials != null) {
                fillCredentials(data, credentials);
                RepositoryProvider.getCredentialsRepository().update(credentials);
                return;
            }
        }
        Credentials credentials = new Credentials();
        fillCredentials(data, credentials);
        RepositoryProvider.getCredentialsRepository().insert(credentials);
    }

    public static void fillCredentials(@NonNull ICredentials from, @NonNull CredentialsModel to) {
        to.setTitle(from.getTitle());
        to.setLink(from.getLink());
        to.setLogin(from.getLogin());
        to.setPassword(from.getPassword());
        to.setPin(from.getPin());
        to.setDetails(from.getDetails());
    }

    public void deleteCredentials() {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            if (credentialsId != null) {
                RepositoryProvider.getCredentialsRepository().deleteCredentialsById(credentialsId);
            }
        });
    }

    public void setFillState(int index, boolean state) {
        fillStates[index] = state;
        boolean result = (fillStates[INDEX_TITLE] || fillStates[INDEX_LINK])
                && fillStates[INDEX_LOGIN] && (fillStates[INDEX_PASSWORD] || fillStates[INDEX_PIN]);
        saveButtonEnableStateLiveData.setValue(result);
    }
}
