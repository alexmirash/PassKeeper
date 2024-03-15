package com.mirash.familiar.activity.edit.credentials

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mirash.familiar.activity.edit.base.BaseEditActivity
import com.mirash.familiar.databinding.ActivityCredentialsEditBinding
import com.mirash.familiar.db.Credentials
import com.mirash.familiar.db.RepositoryProvider
import com.mirash.familiar.db.User
import com.mirash.familiar.model.credentials.CredentialsModel
import com.mirash.familiar.model.credentials.ICredentials
import com.mirash.familiar.tool.KEY_POSITION
import com.mirash.familiar.user.UserControl

/**
 * @author Mirash
 */
class CredentialsEditActivity :
    BaseEditActivity<ICredentials, Credentials, CredentialsEditViewModel, ActivityCredentialsEditBinding>() {

    override fun applyIntentExtras(bundle: Bundle) {
        super.applyIntentExtras(bundle)
        val position = intent.getIntExtra(KEY_POSITION, 0)
        model.credentialsPosition = position
    }

    override fun onCreateNewDataMode() {
        val liveData = RepositoryProvider.userRepository.getById(UserControl.userId)
        liveData.observe(this, object : Observer<User> {
            override fun onChanged(value: User) {
                liveData.removeObserver(this)
                binding.itemEmailInput.let {
                    if (it.text.isNullOrEmpty()) it.text = value.email
                }
                binding.itemPhoneInput.let {
                    if (it.text.isNullOrEmpty()) it.text = value.phone
                }
            }
        })
    }

    override fun initViewBinding(): ActivityCredentialsEditBinding =
        ActivityCredentialsEditBinding.inflate(layoutInflater)

    override fun initViewModel(): CredentialsEditViewModel =
        ViewModelProvider(this)[CredentialsEditViewModel::class.java]

    override fun toolbar(): Toolbar = binding.credentialsToolbar

    override fun createData(): ICredentials {
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
        return credentialsModel
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

    override fun initSaveButtonStateObserver() {
        initTextFillObserver(binding.itemTitleInput, CredentialsEditViewModel.INDEX_TITLE)
        initTextFillObserver(binding.itemLinkInput, CredentialsEditViewModel.INDEX_LINK)
        initTextFillObserver(binding.itemLoginInput, CredentialsEditViewModel.INDEX_LOGIN)
        initTextFillObserver(binding.itemEmailInput, CredentialsEditViewModel.INDEX_EMAIL)
        initTextFillObserver(binding.itemPhoneInput, CredentialsEditViewModel.INDEX_PHONE)
        initTextFillObserver(binding.itemPasswordInput, CredentialsEditViewModel.INDEX_PASSWORD)
        initTextFillObserver(binding.itemPinInput, CredentialsEditViewModel.INDEX_PIN)
    }
}
