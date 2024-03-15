package com.mirash.familiar.activity.edit.user

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.mirash.familiar.activity.edit.base.BaseEditActivity
import com.mirash.familiar.databinding.ActivityUserEditBinding
import com.mirash.familiar.db.User
import com.mirash.familiar.model.user.IUser
import com.mirash.familiar.model.user.UserModel
import com.mirash.familiar.user.UserControl

/**
 * @author Mirash
 */
class UserEditActivity : BaseEditActivity<IUser, User, UserEditViewModel, ActivityUserEditBinding>() {

    override fun initViewBinding(): ActivityUserEditBinding = ActivityUserEditBinding.inflate(layoutInflater)

    override fun initViewModel(): UserEditViewModel = ViewModelProvider(this)[UserEditViewModel::class.java]

    override fun toolbar(): Toolbar = binding.userToolbar

    override fun createData(): IUser {
        val user = UserModel(binding.userNameInput.text ?: "")
        user.email = binding.userEmailInput.text
        user.phone = binding.userPhoneInput.text
        return user
    }

    override fun isHideDeleteMenuItem(): Boolean {
        return super.isHideDeleteMenuItem() || model.id == UserControl.userId
    }

    override fun onChanged(value: User) {
        binding.userNameInput.setTextWithNoAnimation(value.name)
        binding.userEmailInput.setTextWithNoAnimation(value.email)
        binding.userPhoneInput.setTextWithNoAnimation(value.phone)
    }

    override fun initSaveButtonStateObserver() {
        initTextFillObserver(binding.userNameInput)
    }
}
