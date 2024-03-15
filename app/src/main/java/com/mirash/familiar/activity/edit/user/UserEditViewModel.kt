package com.mirash.familiar.activity.edit.user

import android.app.Application
import com.mirash.familiar.activity.edit.base.BaseEditViewModel
import com.mirash.familiar.db.IRepository
import com.mirash.familiar.db.RepositoryProvider.userRepository
import com.mirash.familiar.db.User
import com.mirash.familiar.model.user.IUser

/**
 * @author Mirash
 */
class UserEditViewModel(application: Application) : BaseEditViewModel<IUser, User>(application) {
    override val repository: IRepository<User>
        get() = userRepository

    override fun createEntity(): User = User()

    override fun fill(from: IUser, to: User) {
        to.name = from.name
        to.email = from.email
        to.phone = from.phone
    }
}
