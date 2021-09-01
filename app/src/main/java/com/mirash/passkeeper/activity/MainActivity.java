package com.mirash.passkeeper.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mirash.passkeeper.R;
import com.mirash.passkeeper.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
}