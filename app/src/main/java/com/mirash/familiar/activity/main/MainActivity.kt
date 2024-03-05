package com.mirash.familiar.activity.main

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.mirash.familiar.FamiliarApp
import com.mirash.familiar.R
import com.mirash.familiar.activity.edit.CredentialsEditActivity
import com.mirash.familiar.activity.main.adapter.CredentialsAdapter
import com.mirash.familiar.activity.main.adapter.CredentialsItemCallback
import com.mirash.familiar.databinding.ActivityMainBinding
import com.mirash.familiar.db.Credentials
import com.mirash.familiar.model.CredentialsItem
import com.mirash.familiar.motion.ItemTouchStateCallback
import com.mirash.familiar.motion.OnStartDragListener
import com.mirash.familiar.motion.SimpleItemTouchHelperCallback
import com.mirash.familiar.tool.AppBarCollapseStateChangeListener
import com.mirash.familiar.tool.BACKGROUND_INACTIVITY_KILL_TIME
import com.mirash.familiar.tool.EditResultAction
import com.mirash.familiar.tool.KEY_EDIT_RESULT_ACTION
import com.mirash.familiar.tool.KEY_ID
import com.mirash.familiar.tool.KEY_POSITION
import com.mirash.familiar.tool.REQUEST_CODE_EDIT
import com.mirash.familiar.tool.decoration.DividerListItemDecoration
import com.mirash.familiar.tool.decoration.VerticalBottomSpaceItemDecoration
import com.mirash.familiar.tool.fromCredentials
import com.mirash.familiar.tool.listener.AppShowObserver
import com.mirash.familiar.tool.openLinkExternally
import com.mirash.familiar.tool.share
import java.util.Locale

/**
 * @author Mirash
 */
class MainActivity : AppCompatActivity(), Observer<List<Credentials>>, CredentialsItemCallback,
    OnStartDragListener, ItemTouchStateCallback, Runnable, AppShowObserver {

    private lateinit var binding: ActivityMainBinding
    private lateinit var model: MainActivityModel
    private var adapter: CredentialsAdapter? = null
    private var itemTouchHelper: ItemTouchHelper? = null
    private val handler = Handler(Looper.getMainLooper())
    private var isBackPressedOnce = false
    private var isAppBarExpanded = false
    private var appBarMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
//        supportActionBar?.let {
//            it.setLogo(R.drawable.action_bar_logo)
//            it.setDisplayUseLogoEnabled(true)
//            it.setDisplayShowHomeEnabled(true)
//        }
        binding.credentialsRecycler.layoutManager = LinearLayoutManager(this)
        binding.credentialsRecycler.addItemDecoration(DividerListItemDecoration(this))
        binding.credentialsRecycler.addItemDecoration(
            VerticalBottomSpaceItemDecoration(
                resources.getDimensionPixelSize(R.dimen.main_bottom_padding)
            )
        )
        model = ViewModelProvider(this)[MainActivityModel::class.java]
        model.credentialsModelLiveData.observe(this, this)
        binding.credentialsAddFab.setOnClickListener { showEditCredentialsScreen(null) }
        FamiliarApp.instance.addAppShowObserver(this)
        initAppBarBehaviour()
    }

    override fun onDestroy() {
        super.onDestroy()
        model.credentialsModelLiveData.removeObserver(this)
        FamiliarApp.instance.removeAppShowObserver(this)
    }

    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (isBackPressedOnce) {
            isBackPressedOnce = false
            onDoubleBackPressed()
            return
        }
        isBackPressedOnce = true
        onSingleBackPressed()
        handler.postDelayed({ isBackPressedOnce = false }, 2000)
    }

    private fun onSingleBackPressed() {
        Toast.makeText(this, getString(R.string.double_back_press_message), Toast.LENGTH_SHORT).show()
    }

    private fun onDoubleBackPressed() {
        finishAndRemoveTask()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_EDIT) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    @EditResultAction val action =
                        data.getIntExtra(KEY_EDIT_RESULT_ACTION, EditResultAction.UNDEFINED)
                    if (action == EditResultAction.CREATE) {
                        adapter?.let {
                            binding.credentialsRecycler.scrollToPosition(it.itemCount - 1)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        appBarMenu = menu
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchMenuItem = menu.findItem(R.id.search)
        val searchView = searchMenuItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                adapter?.setFilterQuery(query.lowercase(Locale.getDefault()))
                return false
            }
        })
        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                binding.credentialsAddFab.hide()
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                binding.credentialsAddFab.show()
                adapter?.setFilterQuery(null)
                return true
            }
        })
        return true
    }

    override fun onChanged(value: List<Credentials>) {
        val credentialsItems: MutableList<CredentialsItem> = ArrayList(value.size)
        for (credential in value) {
            credentialsItems.add(CredentialsItem(credential))
        }
        binding.credentialsRecycler.adapter?.let {
            (it as CredentialsAdapter).setItems(credentialsItems)
        } ?: run {
            val credentialsAdapter = CredentialsAdapter(
                credentialsItems, this
            )
            binding.credentialsRecycler.adapter = credentialsAdapter
            val callback = SimpleItemTouchHelperCallback(credentialsAdapter)
            callback.touchStateCallback = this
            val helper = ItemTouchHelper(callback)
            helper.attachToRecyclerView(binding.credentialsRecycler)
            itemTouchHelper = helper
            adapter = credentialsAdapter
        }
    }

    override fun onLinkClick(link: String) {
        openLinkExternally(this, link)
    }

    override fun onEditClick(item: CredentialsItem) {
        showEditCredentialsScreen(item)
    }

    override fun onOrderChanged(items: List<CredentialsItem>) {}

    override fun onShare(item: CredentialsItem) {
        share(this, fromCredentials(item))
    }

    private fun showEditCredentialsScreen(item: CredentialsItem?) {
        val intent = Intent(this, CredentialsEditActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        if (item != null) {
            intent.putExtra(KEY_ID, item.id)
            intent.putExtra(KEY_POSITION, item.position)
        } else {
            intent.putExtra(KEY_POSITION, adapter?.baseItemCount)
        }
        startActivityForResult(intent, REQUEST_CODE_EDIT)
    }

    //not used
    override fun onDragStart(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper?.startDrag(viewHolder)
    }

    override fun onItemSelected() {}

    override fun onItemClear() {
        adapter?.items?.let { model.handleOrderChanged(it) }
    }

    override fun run() {
        finishAndRemoveTask()
    }

    override fun onWentToBackground() {
        handler.postDelayed(this, BACKGROUND_INACTIVITY_KILL_TIME.toLong())
    }

    override fun onWentToForeground() {
        handler.removeCallbacks(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.user) {
//            val intent = Intent(this, UserActivity::class.java)
//            startActivity(intent)
            applyExpandedState()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initAppBarBehaviour() {
        binding.appBar.addOnOffsetChangedListener(object : AppBarCollapseStateChangeListener() {
            override fun onStateChanged(state: State) {
                if (state == State.COLLAPSED) {
                } else if (state == State.EXPANDED) {
                }
            }
        })
        ViewCompat.setNestedScrollingEnabled(binding.credentialsRecycler, false)
        val params = binding.appBar.layoutParams as CoordinatorLayout.LayoutParams
        if (params.behavior == null) params.behavior = AppBarLayout.Behavior()
        val behaviour = params.behavior as AppBarLayout.Behavior
        behaviour.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                return false
            }
        })
        applyExpandedState(false)
    }

    private fun applyExpandedState(expanded: Boolean = !isAppBarExpanded) {
        binding.appBar.setExpanded(expanded)
        isAppBarExpanded = expanded
    }

//    private fun hideOption(id: Int) {
//        menu?.findItem(id)?.isVisible = false
//    }
}