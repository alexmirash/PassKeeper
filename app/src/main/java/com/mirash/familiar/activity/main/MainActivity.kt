package com.mirash.familiar.activity.main

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.mirash.familiar.FamiliarApp
import com.mirash.familiar.R
import com.mirash.familiar.activity.edit.credentials.CredentialsEditActivity
import com.mirash.familiar.activity.edit.user.UserEditActivity
import com.mirash.familiar.activity.main.credentials.CredentialsAdapter
import com.mirash.familiar.activity.main.credentials.CredentialsItemCallback
import com.mirash.familiar.activity.main.user.UserAdapter
import com.mirash.familiar.activity.main.user.UserItemCallback
import com.mirash.familiar.databinding.ActivityMainBinding
import com.mirash.familiar.db.Credentials
import com.mirash.familiar.db.User
import com.mirash.familiar.model.credentials.CredentialsItem
import com.mirash.familiar.model.credentials.ICredentials
import com.mirash.familiar.model.user.IUser
import com.mirash.familiar.model.user.UserItem
import com.mirash.familiar.motion.ItemTouchStateCallback
import com.mirash.familiar.motion.OnStartDragListener
import com.mirash.familiar.motion.SimpleItemTouchHelperCallback
import com.mirash.familiar.tool.APP_BAR_USERS_MAX_VISIBLE_COUNT
import com.mirash.familiar.tool.BACKGROUND_INACTIVITY_KILL_TIME
import com.mirash.familiar.tool.EditResultAction
import com.mirash.familiar.tool.KEY_EDIT_RESULT_ACTION
import com.mirash.familiar.tool.KEY_ID
import com.mirash.familiar.tool.KEY_POSITION
import com.mirash.familiar.tool.decoration.DividerListItemDecoration
import com.mirash.familiar.tool.decoration.VerticalBottomSpaceItemDecoration
import com.mirash.familiar.tool.listener.AppShowObserver
import com.mirash.familiar.tool.listener.IScrollProvider
import com.mirash.familiar.tool.openLinkExternally
import com.mirash.familiar.user.TAG_USER
import com.mirash.familiar.user.UserControl
import java.util.Locale
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.roundToInt


/**
 * @author Mirash
 */
class MainActivity : AppCompatActivity(), MainModelCallback, CredentialsItemCallback, OnStartDragListener,
    ItemTouchStateCallback, Runnable, AppShowObserver {

    private lateinit var binding: ActivityMainBinding
    private lateinit var model: MainActivityModel
    private var credentialsAdapter: CredentialsAdapter? = null
    private var userAdapter: UserAdapter? = null
    private var itemTouchHelper: ItemTouchHelper? = null
    private val handler = Handler(Looper.getMainLooper())
    private var isBackPressedOnce = false
    private var isAppBarExpanded = false
    private var appBarMenu: Menu? = null
    private lateinit var userMenuItem: MenuItem
    private var userAddMenuItem: MenuItem? = null
    private var searchMenuItem: MenuItem? = null
    private val userActivityLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        userAdapter?.let {
            handleEditActivityResult(result, it)
        }
    }
    private val credentialsActivityLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        credentialsAdapter?.let {
            handleEditActivityResult(result, it)
        }
    }

    private val userObserver: Observer<User> = Observer { value ->
        Log.d(TAG_USER, "onUserChanged: ${value.id} ${value.name}")
        userAdapter?.setCheckedItem(value.id)
        binding.userTitle.text = value.name
    }

    private val credentialsObserver: Observer<List<Credentials>> = object : Observer<List<Credentials>> {
        override fun onChanged(value: List<Credentials>) {
            Log.d(TAG_USER, "onCredentialsChanged: ${value.size}")
            val credentialsItems: MutableList<CredentialsItem> = ArrayList(value.size)
            for (credential in value) {
                credentialsItems.add(CredentialsItem(credential))
            }
            credentialsAdapter?.let {
                it.setItems(credentialsItems)
                if (it.isScrollToBottom) {
                    binding.credentialsRecycler.scrollToPosition(it.itemCount - 1)
                }
            } ?: run {
                val adapter = CredentialsAdapter(
                    credentialsItems, this@MainActivity
                )
                binding.credentialsRecycler.adapter = adapter
                val callback = SimpleItemTouchHelperCallback(adapter)
                callback.touchStateCallback = this@MainActivity
                val helper = ItemTouchHelper(callback)
                helper.attachToRecyclerView(binding.credentialsRecycler)
                itemTouchHelper = helper
                credentialsAdapter = adapter
            }
        }
    }

    private fun initCredentialsRecycler() {
        binding.credentialsRecycler.layoutManager = LinearLayoutManager(this)
        binding.credentialsRecycler.addItemDecoration(DividerListItemDecoration(this))
        binding.credentialsRecycler.addItemDecoration(
            VerticalBottomSpaceItemDecoration(
                resources.getDimensionPixelSize(R.dimen.main_bottom_padding)
            )
        )
    }

    private fun updateAppBarHeight(count: Int) {
        val height: Int =
            (min(APP_BAR_USERS_MAX_VISIBLE_COUNT, count) * resources.getDimension(R.dimen.user_item_height) +
                    resources.getDimension(R.dimen.half_padding) +
                    binding.toolbar.height).roundToInt()
        binding.appBar.layoutParams.let {
            if (it.height != height) {
                it.height = height
                binding.appBar.requestLayout()
                applyExpandedState(expanded = isAppBarExpanded, animate = false)
            }
        }
    }

    private fun initUserRecycler() {
        binding.userRecycler.layoutManager = LinearLayoutManager(this)
        binding.userRecycler.addItemDecoration(
            VerticalBottomSpaceItemDecoration(
                resources.getDimensionPixelSize(R.dimen.half_padding)
            )
        )
        model.usersLiveData.observe(this) { users ->
            Log.d(TAG_USER, "onUsersChange: ${users.size}")
            val items = ArrayList<UserItem>(users.size)
            for (user in users) {
                items.add(UserItem(user))
            }
            updateAppBarHeight(items.size)
            userAdapter?.let {
                it.items = items
                if (it.isScrollToBottom) {
                    binding.userRecycler.scrollToPosition(it.itemCount - 1)
                }
            } ?: run {
                val adapter = UserAdapter(
                    items, object : UserItemCallback {
                        override fun onItemClick(item: IUser) {
                            UserControl.setUser(item.id)
                            applyExpandedState(false)
                        }

                        override fun onEditClick(item: IUser) {
                            showEditUserScreen(item)
                        }
                    }
                )
                binding.userRecycler.adapter = adapter
                userAdapter = adapter
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        model = ViewModelProvider(this, MainActivityModelFactory(application, this))[MainActivityModel::class.java]
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initCredentialsRecycler()
        initUserRecycler()
        binding.credentialsAddFab.setOnClickListener { showEditCredentialsScreen(null) }
        FamiliarApp.instance.addAppShowObserver(this)
        initAppBarBehaviour()
        binding.appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            binding.userView.alpha = 1 - abs(verticalOffset / appBarLayout.totalScrollRange.toFloat())
        }
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isBackPressedOnce) {
                    isBackPressedOnce = false
                    finishAndRemoveTask()
                    return
                }
                isBackPressedOnce = true
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.double_back_press_message),
                    Toast.LENGTH_SHORT
                ).show()
                handler.postDelayed({ isBackPressedOnce = false }, 2000)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        FamiliarApp.instance.removeAppShowObserver(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        appBarMenu = menu
        userMenuItem = menu.findItem(R.id.user)
        userAddMenuItem = menu.findItem(R.id.user_add)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchMenuItem = menu.findItem(R.id.search)
        this.searchMenuItem = searchMenuItem
        val searchView = searchMenuItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                credentialsAdapter?.setFilterQuery(query.lowercase(Locale.getDefault()))
                return false
            }
        })
        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                binding.credentialsAddFab.hide()
                userMenuItem.isVisible = false
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                binding.credentialsAddFab.show()
                handler.post {
                    userMenuItem.isVisible = true
                }
                credentialsAdapter?.setFilterQuery(null)
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user -> {
                applyExpandedState()
            }

            R.id.user_add -> {
                showEditUserScreen()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onLinkClick(link: String) {
        openLinkExternally(this, link)
    }

    override fun onEditClick(item: ICredentials) {
        showEditCredentialsScreen(item)
    }

    override fun onOrderChanged(items: List<ICredentials>) {}

    private fun showEditCredentialsScreen(item: ICredentials?) {
        val intent = Intent(this, CredentialsEditActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        if (item != null) {
            intent.putExtra(KEY_ID, item.id)
            intent.putExtra(KEY_POSITION, item.position)
        } else {
            intent.putExtra(KEY_POSITION, credentialsAdapter?.baseItemCount)
        }
        credentialsActivityLauncher.launch(intent)
    }

    private fun showEditUserScreen(item: IUser? = null) {
        val intent = Intent(this, UserEditActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        if (item != null) {
            intent.putExtra(KEY_ID, item.id)
        }
        userActivityLauncher.launch(intent)
    }

    //not used
    override fun onDragStart(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper?.startDrag(viewHolder)
    }

    override fun onItemSelected() {}

    override fun onItemClear() {
        credentialsAdapter?.items?.let { model.handleOrderChanged(it) }
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

    private fun initAppBarBehaviour() {
//        binding.appBar.addOnOffsetChangedListener(object : AppBarCollapseStateChangeListener() {
//            override fun onStateChanged(state: State) {
//                if (state == State.COLLAPSED) {
//                } else if (state == State.EXPANDED) {
//                }
//            }
//        })
        ViewCompat.setNestedScrollingEnabled(binding.credentialsRecycler, false)
        val params = binding.appBar.layoutParams as CoordinatorLayout.LayoutParams
        if (params.behavior == null) params.behavior = AppBarLayout.Behavior()
        val behaviour = params.behavior as AppBarLayout.Behavior
        behaviour.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                return false
            }
        })
    }

    private fun applyExpandedState(expanded: Boolean = !isAppBarExpanded, animate: Boolean = true) {
        binding.appBar.setExpanded(expanded, animate)
        isAppBarExpanded = expanded
        searchMenuItem?.isVisible = !expanded
        userAddMenuItem?.isVisible = expanded
    }

    private fun handleEditActivityResult(result: ActivityResult, scrollProvider: IScrollProvider) {
        if (result.resultCode == RESULT_OK) {
            result.data?.let { data ->
                @EditResultAction val action =
                    data.getIntExtra(KEY_EDIT_RESULT_ACTION, EditResultAction.UNDEFINED)
                if (action == EditResultAction.CREATE) {
                    scrollProvider.isScrollToBottom = true
                }
            }
        }
    }

    override fun setUserObservers(user: LiveData<User>, credentials: LiveData<List<Credentials>>) {
        user.observe(this, userObserver)
        credentials.observe(this, credentialsObserver)
    }

    override fun clearUserObservers(user: LiveData<User>, credentials: LiveData<List<Credentials>>) {
        user.removeObserver(userObserver)
        credentials.removeObserver(credentialsObserver)
    }
}