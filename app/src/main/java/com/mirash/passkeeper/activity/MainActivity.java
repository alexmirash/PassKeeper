package com.mirash.passkeeper.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

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
import com.mirash.passkeeper.Const;
import com.mirash.passkeeper.R;
import com.mirash.passkeeper.db.Credentials;
import com.mirash.passkeeper.drag.ItemTouchStateCallback;
import com.mirash.passkeeper.drag.OnStartDragListener;
import com.mirash.passkeeper.drag.SimpleItemTouchHelperCallback;
import com.mirash.passkeeper.model.CredentialsItem;
import com.mirash.passkeeper.tool.EditResultAction;
import com.mirash.passkeeper.tool.Utils;
import com.mirash.passkeeper.tool.decoration.DividerListItemDecoration;
import com.mirash.passkeeper.tool.decoration.VerticalBottomSpaceItemDecoration;
import com.mirash.passkeeper.ui.main.CredentialsAdapter;
import com.mirash.passkeeper.ui.main.CredentialsItemCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mirash
 */
public class MainActivity extends AppCompatActivity implements Observer<List<Credentials>>,
        CredentialsItemCallback, OnStartDragListener, ItemTouchStateCallback {

    private MainActivityModel model;
    private RecyclerView credentialsRecycler;
    private CredentialsAdapter adapter;
    private FloatingActionButton addButton;
    private ItemTouchHelper itemTouchHelper;

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
        addButton.setOnClickListener(view -> showEditCredentialsScreen());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.getCredentialsModelLiveData().removeObserver(this);
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
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("LOL", "onQueryTextSubmit: " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("LOL", "onQueryTextChange: " + newText);
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LOL", "onSearchClick");
            }
        });
        menu.findItem(R.id.search).setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Log.d("LOL", "onMenuItemActionExpand");
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Log.d("LOL", "onMenuItemActionCollapse");
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            Log.d("LOL" ,"onOptionsItemSelected");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChanged(List<Credentials> credentials) {
        Log.d("LOL", "onChanged:\n" + TextUtils.join("\n", credentials));
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
    public void onEditClick(int id, int position) {
        showEditCredentialsScreen(id, position);
    }

    @Override
    public void onOrderChanged(List<CredentialsItem> items) {
        Log.d("LOL", "onOrderChanged");
    }

    @Override
    public void onShare(CredentialsItem item) {
        Utils.share(this, Utils.fromCredentials(item));
    }

    private void showEditCredentialsScreen() {
        showEditCredentialsScreen(null, adapter.getItemCount());
    }

    private void showEditCredentialsScreen(Integer id, int position) {
        Intent intent = new Intent(this, CredentialsEditActivity.class);
        if (id != null) intent.putExtra(Const.KEY_ID, id);
        intent.putExtra(Const.KEY_POSITION, position);
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
}