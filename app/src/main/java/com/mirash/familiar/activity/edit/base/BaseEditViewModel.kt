package com.mirash.familiar.activity.edit.base

import android.app.Application
import androidx.annotation.CallSuper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mirash.familiar.db.IRepository
import com.mirash.familiar.db.RepositoryProvider
import com.mirash.familiar.db.User
import com.mirash.familiar.model.user.IUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * @author Mirash
 */
abstract class BaseEditViewModel<I, E : I>(application: Application) : AndroidViewModel(application) {
    var liveData: LiveData<E>? = null
        private set
    val saveButtonEnableStateLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    var id: Long? = null
        private set

    protected abstract val repository: IRepository<E>

    protected abstract fun createEntity(): E

    fun setId(id: Long) {
        this.id = id
        liveData = repository.getById(id)
    }

    private fun insertSync(data: I) {
        liveData?.value?.let {
            fill(data, it)
            repository.update(it)
            return
        }
        val user = createEntity()
        fill(data, user)
        repository.insert(user)
    }

    open fun deleteSync(id: Long) {
        repository.deleteById(id)
    }

    protected abstract fun fill(from: I, to: E)

    fun removeObserver(observer: Observer<E>) {
        liveData?.removeObserver(observer)
    }

    fun save(data: I) {
        runBlocking { launch(Dispatchers.Default) { insertSync(data) } }
    }

    fun delete() {
        runBlocking {
            launch(Dispatchers.Default) {
                id?.let {
                    deleteSync(it)
                }
            }
        }
    }

    @CallSuper
    open fun setFillState(state: Boolean, index: Int) {
        saveButtonEnableStateLiveData.value = state
    }
}
