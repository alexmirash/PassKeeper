package com.mirash.familiar.activity.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mirash.familiar.Const;
import com.mirash.familiar.FamiliarApp;
import com.mirash.familiar.R;
import com.mirash.familiar.activity.edit.CredentialsAdapter;
import com.mirash.familiar.activity.edit.CredentialsEditActivity;
import com.mirash.familiar.activity.edit.CredentialsItemCallback;
import com.mirash.familiar.db.Credentials;
import com.mirash.familiar.model.CredentialsItem;
import com.mirash.familiar.motion.ItemTouchStateCallback;
import com.mirash.familiar.motion.OnStartDragListener;
import com.mirash.familiar.motion.SimpleItemTouchHelperCallback;
import com.mirash.familiar.tool.EditResultAction;
import com.mirash.familiar.tool.Utils;
import com.mirash.familiar.tool.decoration.DividerListItemDecoration;
import com.mirash.familiar.tool.decoration.VerticalBottomSpaceItemDecoration;
import com.mirash.familiar.tool.listener.AppShowObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mirash
 */
public class MainActivity extends AppCompatActivity implements Observer<List<Credentials>>,
        CredentialsItemCallback, OnStartDragListener, ItemTouchStateCallback, Runnable, AppShowObserver {

    private MainActivityModel model;
    private RecyclerView credentialsRecycler;
    private CredentialsAdapter adapter;
    private FloatingActionButton addButton;
    private ItemTouchHelper itemTouchHelper;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean isBackPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setLogo(R.drawable.action_bar_logo);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        credentialsRecycler = findViewById(R.id.credentials_list);
        credentialsRecycler.setLayoutManager(new LinearLayoutManager(this));
        credentialsRecycler.addItemDecoration(new DividerListItemDecoration(this));
        credentialsRecycler.addItemDecoration(new VerticalBottomSpaceItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.main_bottom_padding)));

        model = new ViewModelProvider(this).get(MainActivityModel.class);
        model.getCredentialsModelLiveData().observe(this, this);
        addButton = findViewById(R.id.credentials_add_fab);
        addButton.setOnClickListener(view -> showEditCredentialsScreen(null));
        FamiliarApp.getInstance().addAppShowObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.getCredentialsModelLiveData().removeObserver(this);
        FamiliarApp.getInstance().removeAppShowObserver(this);
    }

    @Override
    public void onBackPressed() {
        if (isBackPressedOnce) {
            isBackPressedOnce = false;
            onDoubleBackPressed();
            return;
        }
        isBackPressedOnce = true;
        onSingleBackPressed();
        new Handler(Looper.getMainLooper()).postDelayed(() -> isBackPressedOnce = false, 2000);
    }

    private void onSingleBackPressed() {
        Toast.makeText(this, getString(R.string.double_back_press_message), Toast.LENGTH_SHORT).show();
    }

    private void onDoubleBackPressed() {
        finishAndRemoveTask();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_CODE_EDIT) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    @EditResultAction int action = data.getIntExtra(Const.KEY_EDIT_RESULT_ACTION, EditResultAction.UNDEFINED);
                    if (action == EditResultAction.CREATE) {
                        credentialsRecycler.scrollToPosition(adapter.getItemCount() - 1);
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.setFilterQuery(query.toLowerCase());
                return false;
            }
        });
        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                addButton.hide();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                addButton.show();
                adapter.setFilterQuery(null);
                return true;
            }
        });
        return true;
    }

    @Override
    public void onChanged(List<Credentials> credentials) {
        adapter = (CredentialsAdapter) credentialsRecycler.getAdapter();
        List<CredentialsItem> credentialsItems = new ArrayList<>(credentials.size());
        for (Credentials value : credentials) {
            credentialsItems.add(new CredentialsItem(value));
        }
        if (adapter == null) {
            adapter = new CredentialsAdapter(credentialsItems, this);
            credentialsRecycler.setAdapter(adapter);
            SimpleItemTouchHelperCallback callback = new SimpleItemTouchHelperCallback(adapter);
            callback.setTouchStateCallback(this);
            itemTouchHelper = new ItemTouchHelper(callback);
            itemTouchHelper.attachToRecyclerView(credentialsRecycler);
        } else {
            adapter.setItems(credentialsItems);
        }
    }

    @Override
    public void onLinkClick(String link) {
        Utils.openLinkExternally(this, link);
    }

    @Override
    public void onEditClick(CredentialsItem item) {
        showEditCredentialsScreen(item);
    }

    @Override
    public void onOrderChanged(List<CredentialsItem> items) {
    }

    @Override
    public void onShare(CredentialsItem item) {
        Utils.share(this, Utils.fromCredentials(item));
    }

    private void showEditCredentialsScreen(@Nullable CredentialsItem item) {
        Intent intent = new Intent(this, CredentialsEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (item != null) {
            intent.putExtra(Const.KEY_ID, item.getId());
            intent.putExtra(Const.KEY_POSITION, item.getPosition());
        } else {
            intent.putExtra(Const.KEY_POSITION, adapter.getBaseItemCount());
        }
        startActivityForResult(intent, Const.REQUEST_CODE_EDIT);
    }

    //not used
    @Override
    public void onDragStart(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onItemSelected() {
    }

    @Override
    public void onItemClear() {
        model.handleOrderChanged(adapter.getItems());
    }

    @Override
    public void run() {
        finishAndRemoveTask();
    }

    @Override
    public void onWentToBackground() {
        handler.postDelayed(this, Const.BACKGROUND_INACTIVITY_KILL_TIME);
    }

    @Override
    public void onWentToForeground() {
        handler.removeCallbacks(this);
    }
}