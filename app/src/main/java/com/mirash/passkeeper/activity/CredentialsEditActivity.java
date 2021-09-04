package com.mirash.passkeeper.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mirash.passkeeper.Const;
import com.mirash.passkeeper.R;
import com.mirash.passkeeper.db.Credentials;
import com.mirash.passkeeper.model.CredentialsModel;
import com.mirash.passkeeper.view.StyledTextInputLayout;

/**
 * @author Mirash
 */
public class CredentialsEditActivity extends AppCompatActivity implements Observer<Credentials> {
    private StyledTextInputLayout titleInputLayout;
    private StyledTextInputLayout linkInputLayout;
    private StyledTextInputLayout loginInputLayout;
    private StyledTextInputLayout passwordInputLayout;
    private StyledTextInputLayout pinInputLayout;
    private StyledTextInputLayout detailsInputLayout;
    private CredentialsEditViewModel model;
    private MenuItem saveMenuItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentials_edit);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        titleInputLayout = findViewById(R.id.item_title_input);
        linkInputLayout = findViewById(R.id.item_link_input);
        loginInputLayout = findViewById(R.id.item_login_input);
        passwordInputLayout = findViewById(R.id.item_password_input);
        pinInputLayout = findViewById(R.id.item_pin_input);
        detailsInputLayout = findViewById(R.id.item_details_input);

        model = new ViewModelProvider(this).get(CredentialsEditViewModel.class);
        Bundle args = getIntent().getExtras();
        if (args != null && args.containsKey(Const.KEY_ID)) {
            int id = getIntent().getIntExtra(Const.KEY_ID, 0);
            model.setCredentialsId(id);
            model.getCredentialsLiveData().observe(this, this);
        }
        initSaveButtonStateObserver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.removeCredentialsObserver(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(model.getCredentialsId() == null ? R.menu.credentials_save_menu : R.menu.credentials_edit_menu, menu);
        saveMenuItem = menu.findItem(R.id.credentials_edit_save);
        model.getSaveButtonEnableStateLiveData().observe(this, enabled -> {
            saveMenuItem.setEnabled(enabled);
            saveMenuItem.getIcon().setAlpha(enabled ? 255 : 78);
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.credentials_edit_save) {
            saveCredentials();
        } else if (id == R.id.credentials_edit_delete) {
            model.deleteCredentials();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChanged(Credentials credentials) {
        if (credentials == null) return;
        titleInputLayout.setTextWithNoAnimation(credentials.getTitle());
        linkInputLayout.setTextWithNoAnimation(credentials.getLink());
        loginInputLayout.setTextWithNoAnimation(credentials.getLogin());
        passwordInputLayout.setTextWithNoAnimation(credentials.getPassword());
        pinInputLayout.setTextWithNoAnimation(credentials.getPin());
        detailsInputLayout.setTextWithNoAnimation(credentials.getDetails());
    }

    private void saveCredentials() {
        CredentialsModel credentialsModel = new CredentialsModel();
        credentialsModel.setTitle(titleInputLayout.getText());
        credentialsModel.setLink(linkInputLayout.getText());
        credentialsModel.setLogin(loginInputLayout.getText());
        credentialsModel.setPassword(passwordInputLayout.getText());
        credentialsModel.setPin(pinInputLayout.getText());
        credentialsModel.setDetails(detailsInputLayout.getText());
        model.saveCredentials(credentialsModel);
        onBackPressed();
    }

    private void initSaveButtonStateObserver() {
        titleInputLayout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                model.setFillState(CredentialsEditViewModel.INDEX_TITLE, editable.length() > 0);
            }
        });
        linkInputLayout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                model.setFillState(CredentialsEditViewModel.INDEX_LINK, editable.length() > 0);
            }
        });
        loginInputLayout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                model.setFillState(CredentialsEditViewModel.INDEX_LOGIN, editable.length() > 0);
            }
        });
        passwordInputLayout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                model.setFillState(CredentialsEditViewModel.INDEX_PASSWORD, editable.length() > 0);
            }
        });
        pinInputLayout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                model.setFillState(CredentialsEditViewModel.INDEX_PIN, editable.length() > 0);
            }
        });
    }
}
