package com.mirash.familiar.activity.edit.base

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.mirash.familiar.R
import com.mirash.familiar.tool.EditResultAction
import com.mirash.familiar.tool.KEY_EDIT_RESULT_ACTION
import com.mirash.familiar.tool.KEY_ID
import com.mirash.familiar.view.StyledTextInputLayout

/**
 * @author Mirash
 */
abstract class BaseEditActivity<I, E : I, M : BaseEditViewModel<I, E>, B : ViewBinding> : AppCompatActivity(),
    Observer<E> {
    private lateinit var saveMenuItem: MenuItem

    protected lateinit var binding: B
    protected lateinit var model: M
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initViewBinding()
        setContentView(binding.root)
        setSupportActionBar(toolbar())
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        model = initViewModel()
        intent.extras?.let {
            applyIntentExtras(it)
        }
        initSaveButtonStateObserver()
    }

    @CallSuper
    protected open fun applyIntentExtras(bundle: Bundle) {
        if (bundle.containsKey(KEY_ID)) {
            val id = bundle.getLong(KEY_ID, 0)
            model.setId(id)
            model.liveData?.observe(this, this)
        }
    }

    protected abstract fun initViewBinding(): B

    protected abstract fun initViewModel(): M

    protected abstract fun toolbar(): Toolbar

    protected abstract fun createData(): I
    protected abstract fun initSaveButtonStateObserver()


    override fun onDestroy() {
        super.onDestroy()
        model.removeObserver(this)
    }

    protected open fun isHideDeleteMenuItem(): Boolean = model.id == null
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)
        saveMenuItem = menu.findItem(R.id.edit_save)
        if (isHideDeleteMenuItem()) {
            menu.findItem(R.id.edit_delete).isVisible = false
        }
        model.saveButtonEnableStateLiveData.observe(this) { enabled: Boolean ->
            saveMenuItem.setEnabled(enabled)
            saveMenuItem.icon?.alpha = if (enabled) 255 else 78
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }

            R.id.edit_save -> {
                saveData()
            }

            R.id.edit_delete -> {
                deleteCredentials()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveData() {
        model.save(createData())
        val data = Intent()
        data.putExtra(
            KEY_EDIT_RESULT_ACTION,
            if (model.id == null) EditResultAction.CREATE else EditResultAction.UPDATE
        )
        setResult(RESULT_OK, data)
        finish()
    }

    private fun deleteCredentials() {
        model.removeObserver(this)
        model.delete()
        val data = Intent()
        data.putExtra(KEY_EDIT_RESULT_ACTION, EditResultAction.DELETE)
        setResult(RESULT_OK, data)
        finish()
    }

    protected fun initTextFillObserver(inputLayout: StyledTextInputLayout, index: Int = 0) {
        inputLayout.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                model.setFillState(editable.isNotEmpty(), index)
            }
        })
    }
}
