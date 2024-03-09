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
        return UserModel(binding.userNameInput.text ?: "")
    }

    override fun isHideDeleteMenuItem(): Boolean {
        return super.isHideDeleteMenuItem() || model.id == UserControl.userId
    }

    override fun onChanged(value: User) {
        binding.userNameInput.setTextWithNoAnimation(value.name)
    }

    override fun initSaveButtonStateObserver() {
        initTextFillObserver(binding.userNameInput)
    }
}
