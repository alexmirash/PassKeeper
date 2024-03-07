package com.mirash.familiar.activity.edit

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mirash.familiar.R
import com.mirash.familiar.databinding.ActivityCredentialsEditBinding
import com.mirash.familiar.db.Credentials
import com.mirash.familiar.model.credentials.CredentialsModel
import com.mirash.familiar.tool.EditResultAction
import com.mirash.familiar.tool.KEY_EDIT_RESULT_ACTION
import com.mirash.familiar.tool.KEY_ID
import com.mirash.familiar.tool.KEY_POSITION
import com.mirash.familiar.view.StyledTextInputLayout

/**
 * @author Mirash
 */
class CredentialsEditActivity : AppCompatActivity(), Observer<Credentials> {
    private lateinit var saveMenuItem: MenuItem

    private lateinit var binding: ActivityCredentialsEditBinding
    private lateinit var model: CredentialsEditViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCredentialsEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.credentialsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        model = ViewModelProvider(this)[CredentialsEditViewModel::class.java]
        intent.extras?.let {
            if (it.containsKey(KEY_ID)) {
                val id = it.getLong(KEY_ID, 0)
                model.setCredentialsId(id)
                model.credentialsLiveData?.observe(this, this)
            }
            val position = intent.getIntExtra(KEY_POSITION, 0)
            model.credentialsPosition = position
        }
        initSaveButtonStateObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        model.removeCredentialsObserver(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(
            if (model.credentialsId == null) R.menu.credentials_save_menu else R.menu.credentials_edit_menu, menu
        )
        saveMenuItem = menu.findItem(R.id.credentials_edit_save)
        model.saveButtonEnableStateLiveData.observe(this) { enabled: Boolean ->
            saveMenuItem.setEnabled(enabled)
            saveMenuItem.icon?.alpha = if (enabled) 255 else 78
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }

            R.id.credentials_edit_save -> {
                saveCredentials()
            }

            R.id.credentials_edit_delete -> {
                deleteCredentials()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onChanged(value: Credentials) {
        binding.itemTitleInput.setTextWithNoAnimation(value.title)
        binding.itemLinkInput.setTextWithNoAnimation(value.link)
        binding.itemLoginInput.setTextWithNoAnimation(value.login)
        binding.itemEmailInput.setTextWithNoAnimation(value.email)
        binding.itemPhoneInput.setTextWithNoAnimation(value.phone)
        binding.itemPasswordInput.setTextWithNoAnimation(value.password)
        binding.itemPinInput.setTextWithNoAnimation(value.pin)
        binding.itemDetailsInput.setTextWithNoAnimation(value.details)
    }

    private fun saveCredentials() {
        val credentialsModel = CredentialsModel()
        credentialsModel.title = binding.itemTitleInput.text
        credentialsModel.link = binding.itemLinkInput.text
        credentialsModel.login = binding.itemLoginInput.text
        credentialsModel.email = binding.itemEmailInput.text
        credentialsModel.phone = binding.itemPhoneInput.text
        credentialsModel.password = binding.itemPasswordInput.text
        credentialsModel.pin = binding.itemPinInput.text
        credentialsModel.details = binding.itemDetailsInput.text
        credentialsModel.position = model.credentialsPosition
        model.saveCredentials(credentialsModel)
        val data = Intent()
        data.putExtra(
            KEY_EDIT_RESULT_ACTION,
            if (model.credentialsId == null) EditResultAction.CREATE else EditResultAction.UPDATE
        )
        data.putExtra(KEY_POSITION, model.credentialsPosition)
        setResult(RESULT_OK, data)
        finish()
    }

    private fun deleteCredentials() {
        model.removeCredentialsObserver(this)
        model.deleteCredentials()
        val data = Intent()
        data.putExtra(KEY_POSITION, model.credentialsPosition)
        data.putExtra(KEY_EDIT_RESULT_ACTION, EditResultAction.DELETE)
        setResult(RESULT_OK, data)
        finish()
    }

    private fun initSaveButtonStateObserver() {
        initTextFillObserver(binding.itemTitleInput, CredentialsEditViewModel.INDEX_TITLE)
        initTextFillObserver(binding.itemLinkInput, CredentialsEditViewModel.INDEX_LINK)
        initTextFillObserver(binding.itemLoginInput, CredentialsEditViewModel.INDEX_LOGIN)
        initTextFillObserver(binding.itemEmailInput, CredentialsEditViewModel.INDEX_EMAIL)
        initTextFillObserver(binding.itemPhoneInput, CredentialsEditViewModel.INDEX_PHONE)
        initTextFillObserver(binding.itemPasswordInput, CredentialsEditViewModel.INDEX_PASSWORD)
        initTextFillObserver(binding.itemPinInput, CredentialsEditViewModel.INDEX_PIN)
    }

    private fun initTextFillObserver(inputLayout: StyledTextInputLayout, index: Int) {
        inputLayout.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                model.setFillState(index, editable.isNotEmpty())
            }
        })
    }
}
