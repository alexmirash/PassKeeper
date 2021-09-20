package com.mirash.familiar.activity.edit;

import android.content.Intent;
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

import com.mirash.familiar.Const;
import com.mirash.familiar.R;
import com.mirash.familiar.db.Credentials;
import com.mirash.familiar.model.CredentialsModel;
import com.mirash.familiar.tool.EditResultAction;
import com.mirash.familiar.view.StyledTextInputLayout;

/**
 * @author Mirash
 */
public class CredentialsEditActivity extends AppCompatActivity implements Observer<Credentials> {
    private StyledTextInputLayout titleInputLayout;
    private StyledTextInputLayout linkInputLayout;
    private StyledTextInputLayout loginInputLayout;
    private StyledTextInputLayout emailInputLayout;
    private StyledTextInputLayout phoneInputLayout;
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
        emailInputLayout = findViewById(R.id.item_email_input);
        phoneInputLayout = findViewById(R.id.item_phone_input);
        passwordInputLayout = findViewById(R.id.item_password_input);
        pinInputLayout = findViewById(R.id.item_pin_input);
        detailsInputLayout = findViewById(R.id.item_details_input);

        model = new ViewModelProvider(this).get(CredentialsEditViewModel.class);
        Bundle args = getIntent().getExtras();
        if (args != null) {
            if (args.containsKey(Const.KEY_ID)) {
                int id = getIntent().getIntExtra(Const.KEY_ID, 0);
                model.setCredentialsId(id);
                model.getCredentialsLiveData().observe(this, this);
            }
            int position = getIntent().getIntExtra(Const.KEY_POSITION, 0);
            model.setCredentialsPosition(position);
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
        if (model.getCredentialsId() == null) {
            getMenuInflater().inflate(R.menu.credentials_save_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.credentials_edit_menu, menu);
        }
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
            deleteCredentials();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChanged(Credentials credentials) {
        if (credentials == null) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }
        titleInputLayout.setTextWithNoAnimation(credentials.getTitle());
        linkInputLayout.setTextWithNoAnimation(credentials.getLink());
        loginInputLayout.setTextWithNoAnimation(credentials.getLogin());
        emailInputLayout.setTextWithNoAnimation(credentials.getEmail());
        phoneInputLayout.setTextWithNoAnimation(credentials.getPhone());
        passwordInputLayout.setTextWithNoAnimation(credentials.getPassword());
        pinInputLayout.setTextWithNoAnimation(credentials.getPin());
        detailsInputLayout.setTextWithNoAnimation(credentials.getDetails());
    }

    private void saveCredentials() {
        CredentialsModel credentialsModel = new CredentialsModel();
        credentialsModel.setTitle(titleInputLayout.getText());
        credentialsModel.setLink(linkInputLayout.getText());
        credentialsModel.setLogin(loginInputLayout.getText());
        credentialsModel.setEmail(emailInputLayout.getText());
        credentialsModel.setPhone(phoneInputLayout.getText());
        credentialsModel.setPassword(passwordInputLayout.getText());
        credentialsModel.setPin(pinInputLayout.getText());
        credentialsModel.setDetails(detailsInputLayout.getText());
        credentialsModel.setPosition(model.getCredentialsPosition());
        model.saveCredentials(credentialsModel);
        Intent data = new Intent();
        if (model.getCredentialsId() == null) {
            data.putExtra(Const.KEY_EDIT_RESULT_ACTION, EditResultAction.CREATE);
        } else {
            data.putExtra(Const.KEY_EDIT_RESULT_ACTION, EditResultAction.UPDATE);
        }
        data.putExtra(Const.KEY_POSITION, model.getCredentialsPosition());
        setResult(RESULT_OK, data);
        finish();
    }

    private void deleteCredentials() {
        model.deleteCredentials();
        Intent data = new Intent();
        data.putExtra(Const.KEY_POSITION, model.getCredentialsPosition());
        data.putExtra(Const.KEY_EDIT_RESULT_ACTION, EditResultAction.DELETE);
        setResult(RESULT_OK, data);
        finish();
    }

    private void initSaveButtonStateObserver() {
        initTextFillObserver(titleInputLayout, CredentialsEditViewModel.INDEX_TITLE);
        initTextFillObserver(linkInputLayout, CredentialsEditViewModel.INDEX_LINK);
        initTextFillObserver(loginInputLayout, CredentialsEditViewModel.INDEX_LOGIN);
        initTextFillObserver(emailInputLayout, CredentialsEditViewModel.INDEX_EMAIL);
        initTextFillObserver(phoneInputLayout, CredentialsEditViewModel.INDEX_PHONE);
        initTextFillObserver(passwordInputLayout, CredentialsEditViewModel.INDEX_PASSWORD);
        initTextFillObserver(pinInputLayout, CredentialsEditViewModel.INDEX_PIN);
    }

    private void initTextFillObserver(StyledTextInputLayout inputLayout, int index) {
        inputLayout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                model.setFillState(index, editable.length() > 0);
            }
        });
    }
}
