package com.mirash.passkeeper.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mirash.passkeeper.R;
import com.mirash.passkeeper.db.Credentials;
import com.mirash.passkeeper.tool.Utils;
import com.mirash.passkeeper.tool.decoration.DividerListItemDecoration;
import com.mirash.passkeeper.ui.main.CredentialsAdapter;
import com.mirash.passkeeper.ui.main.CredentialsItemCallback;

import java.util.List;

/**
 * @author Mirash
 */
public class MainActivity extends AppCompatActivity implements Observer<List<Credentials>>, CredentialsItemCallback {

    private MainActivityModel model;
    private RecyclerView credentialsRecycler;
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setLogo(R.drawable.action_bar_logo);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        credentialsRecycler = findViewById(R.id.credentials_list);
        credentialsRecycler.setLayoutManager(new LinearLayoutManager(this));
        credentialsRecycler.addItemDecoration(new DividerListItemDecoration(this));
        model = new ViewModelProvider(this).get(MainActivityModel.class);
        model.getCredentialsModelLiveData().observe(this, this);
        addButton = findViewById(R.id.credentials_add_fab);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.getCredentialsModelLiveData().removeObserver(this);
    }

    @Override
    public void onChanged(List<Credentials> credentials) {
        Log.d("LOL", "onChanged:" + credentials.size());
        CredentialsAdapter adapter = (CredentialsAdapter) credentialsRecycler.getAdapter();
        if (adapter == null) {
            adapter = new CredentialsAdapter(credentials, this);
            credentialsRecycler.setAdapter(adapter);
        } else {
            adapter.setItems(credentials);
        }
    }

    @Override
    public void onLinkClick(String link) {
        Utils.openLinkExternally(this, link);
    }
}